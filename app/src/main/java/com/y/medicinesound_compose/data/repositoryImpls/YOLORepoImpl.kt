package com.y.medicinesound_compose.data.repositoryImpls

import android.graphics.Bitmap
import com.y.medicinesound_compose.data.local.DetectResult
import com.y.medicinesound_compose.data.local.PrePostProcessor
import com.y.medicinesound_compose.domain.repositories.YOLORepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.torchvision.TensorImageUtils
import javax.inject.Inject

class YOLORepoImpl @Inject constructor(
    private val mModule: Module
) : YOLORepo {


    override suspend fun detectObject(
        bitmap: Bitmap,
        imgScaleX: Float,
        imgScaleY: Float,
        ivScaleX: Float,
        ivScaleY: Float,
        startX: Float,
        startY: Float
    ): List<DetectResult> {
        val resizedBitmap: Bitmap = Bitmap.createScaledBitmap(
            bitmap,
            PrePostProcessor.mInputWidth,
            PrePostProcessor.mInputHeight,
            true
        )
        val inputTensor = TensorImageUtils.bitmapToFloat32Tensor(
            resizedBitmap,
            PrePostProcessor.NO_MEAN_RGB,
            PrePostProcessor.NO_STD_RGB,
        )
        val outputTuple = mModule.forward(IValue.from(inputTensor)).toTuple()
        val outputTensor = outputTuple[0].toTensor()
        val outputs = outputTensor.dataAsFloatArray
        return PrePostProcessor.outputsToNMSPredictions(
            outputs,
            imgScaleX,
            imgScaleY,
            ivScaleX,
            ivScaleY,
            startX,
            startY
        )

    }

}