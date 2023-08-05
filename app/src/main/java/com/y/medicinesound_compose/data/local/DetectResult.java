package com.y.medicinesound_compose.data.local;

import android.graphics.Rect;

public class DetectResult {
    public int classIndex;
    public Float score;
    public Rect rect;

    public DetectResult(int cls, Float output, Rect rect) {
        this.classIndex = cls;
        this.score = output;
        this.rect = rect;
    }
}
