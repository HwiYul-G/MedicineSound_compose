package com.y.medicinesound_compose.data.local;


import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class PrePostProcessor {
    public static float[] NO_MEAN_RGB = new float[]{0.0f, 0.0f, 0.0f};
    public static float[] NO_STD_RGB = new float[]{1.0f, 1.0f, 1.0f};

    // model input img size
    public static int mInputWidth = 640;
    public static int mInputHeight = 640;

    // model output is of size 25200 * (num_of_class + 5)
    private static final int  mOutputRow = 25200; // as decided by the YOLOv5 model for input image of size 640*640
    private static final int mOutputColumn = 12; // left, top, right, bottom, score and 80 class probability
    private static final float mThreshold = 0.30f; // score above which a detection is generated
    private static final int mNmsLimit = 15;

    public static String[] mClasses;

    public static ArrayList<DetectResult> nonMaxSuppression(ArrayList<DetectResult> boxes, int limit, float threshold) {
        // high to low로 confidience scroes 인자를 정렬
        Collections.sort(
                boxes,
                Comparator.comparing(o -> o.score)
        );

        ArrayList<DetectResult> selected = new ArrayList<>();
        boolean[] active = new boolean[boxes.size()];
        Arrays.fill(active, true);
        int numActive = active.length;

        /*
            알고리즘
            가장 높은 점수를 가진 box에서 시작한다. 주어진 threshold 이상으로 겹치는 남아있는 box들을 제거한다.
            만약 이전의 어떤 박스와도 겹치지 않고 왼쪽에 있는 박스가 있으면, 이 절차를 반복한다.
            더이상 남아있는 박스가 없거나 제한에 도달할때까지 반복한다.
        */
        boolean done = false;
        for (int i = 0; i < boxes.size() && !done; i++) {
            if (active[i]) {
                DetectResult boxA = boxes.get(i);
                selected.add(boxA);
                if (selected.size() >= limit) break;

                for (int j = i + 1; j < boxes.size(); j++) {
                    if (active[j]) {
                        DetectResult boxB = boxes.get(j);
                        if (IOU(boxA.rect, boxB.rect) > threshold) {
                            active[j] = false;
                            numActive -= 1;
                            if (numActive <= 0) {
                                done = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return selected;
    }

    // 두 개의 바운딩 박스 사이에 IoU 계산
    public static float IOU(Rect a, Rect b) {
        float areaA = (a.right - a.left) * (a.bottom - a.top);
        if (areaA <= 0.0) return 0.0f;

        float areaB = (b.right - b.left) * (b.bottom - b.top);
        if (areaB <= 0.0) return 0.0f;

        float intersectionMinX = Math.max(a.left, b.left);
        float intersectionMinY = Math.max(a.top, b.top);
        float intersectionMaxX = Math.min(a.right, b.right);
        float intersectionMaxY = Math.min(a.bottom, b.bottom);
        float intersectionArea = Math.max(intersectionMaxY - intersectionMinY, 0) *
                Math.max(intersectionMaxX - intersectionMinX, 0);
        return intersectionArea / (areaA + areaB - intersectionArea);
    }

    public static ArrayList<DetectResult> outputsToNMSPredictions(float[] outputs, float imgScaleX, float imgScaleY, float ivScaleX, float ivScaleY, float startX, float startY) {
        ArrayList<DetectResult> results = new ArrayList<>();
        for (int i = 0; i < mOutputRow; i++) {
            if (outputs[i * mOutputColumn + 4] > mThreshold) {
                float x = outputs[i * mOutputColumn];
                float y = outputs[i * mOutputColumn + 1];
                float w = outputs[i * mOutputColumn + 2];
                float h = outputs[i * mOutputColumn + 3];

                float left = imgScaleX * (x - w / 2);
                float top = imgScaleY * (y - h / 2);
                float right = imgScaleX * (x + w / 2);
                float bottom = imgScaleY * (y + h / 2);

                float max = outputs[i * mOutputColumn + 5];
                int cls = 0;
                for (int j = 0; j < mOutputColumn - 5; j++) {
                    if (outputs[i * mOutputColumn + 5 + j] > max) {
                        max = outputs[i * mOutputColumn + 5 + j];
                        cls = j;
                    }
                }

                Rect rect = new Rect((int) (startX + ivScaleX * left), (int) (startY + top * ivScaleY), (int) (startX + ivScaleX * right), (int) (startY + ivScaleY * bottom));
                DetectResult result = new DetectResult(cls, outputs[i * mOutputColumn + 4], rect);
                results.add(result);
            }
        }
        return nonMaxSuppression(results, mNmsLimit, mThreshold);
    }

}
