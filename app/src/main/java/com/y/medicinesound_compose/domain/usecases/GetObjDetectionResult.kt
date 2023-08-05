package com.y.medicinesound_compose.domain.usecases

import android.graphics.Bitmap
import com.y.medicinesound_compose.data.local.DetectResult
import com.y.medicinesound_compose.data.local.PrePostProcessor
import com.y.medicinesound_compose.domain.repositories.YOLORepo
import javax.inject.Inject

class GetObjDetectionResult @Inject constructor(
    private val yoloRepo: YOLORepo,
) {
    suspend fun invoke(
        inputbitmap: Bitmap,
        imageViewWidth: Float,
        imageViewHeight: Float
    ): List<DetectResult> {
        val bitmap = inputbitmap.copy(Bitmap.Config.ARGB_8888, true)

        val imgScaleX = bitmap.width / PrePostProcessor.mInputWidth.toFloat()
        val imgScaleY = bitmap.height / PrePostProcessor.mInputHeight.toFloat()

        val ivScaleX =
            if (bitmap.width > bitmap.height) imageViewWidth / bitmap.width else imageViewHeight / bitmap.height
        val ivScaleY =
            if (bitmap.height > bitmap.width) imageViewHeight / bitmap.height else imageViewWidth / bitmap.width

        val startX = (imageViewWidth - bitmap.width * ivScaleX) / 2
        val startY = (imageViewHeight - bitmap.height * ivScaleY) / 2

        return yoloRepo.detectObject(bitmap, imgScaleX, imgScaleY, ivScaleX, ivScaleY, startX, startY)
    }

}