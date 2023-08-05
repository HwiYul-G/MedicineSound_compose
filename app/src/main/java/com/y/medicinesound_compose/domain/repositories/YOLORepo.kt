package com.y.medicinesound_compose.domain.repositories

import android.graphics.Bitmap
import com.y.medicinesound_compose.data.local.DetectResult

interface YOLORepo {

    suspend fun detectObject(
        bitmap: Bitmap,
        imgScaleX: Float,
        imgScaleY: Float,
        ivScaleX: Float,
        ivScaleY: Float,
        startX: Float,
        startY: Float
    ): List<DetectResult>
}