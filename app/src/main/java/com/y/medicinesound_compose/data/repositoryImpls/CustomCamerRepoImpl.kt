package com.y.medicinesound_compose.data.repositoryImpls

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.y.medicinesound_compose.domain.repositories.CustomCameraRepo
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class CustomCameraRepoImpl @Inject constructor(
    private val cameraProvider: ProcessCameraProvider,
    private val selector: CameraSelector,
    private val preview: Preview,
    private val imageCapture: ImageCapture,
    private val resolutionSelector: ResolutionSelector,
    private val imageAnalysis: ImageAnalysis,
) : CustomCameraRepo {

    private val savedImageUri : MutableStateFlow<Uri?> = MutableStateFlow(null)

    override suspend fun captureAndSaveImage(context: Context) {
        // for file name
        val name = SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss-SSS",
            Locale.KOREA
        ).format(System.currentTimeMillis())

        // for storing
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > 28){
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MedicineSound")
            }
        }

        // for capture output
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // TODO : uri를 Usecase로 보내서 VM으로도 보내야해.
                    outputFileResults.savedUri?.let {
                        savedImageUri.value = it
                    }

                    Toast.makeText(
                        context,
                        "${outputFileResults.savedUri}",
                        Toast.LENGTH_SHORT
                    ).show()
                    // content://media/external/images/media/60

                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        context,
                        "some error occurred : ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

    }

    override suspend fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
    ) {
        preview.setSurfaceProvider(previewView.surfaceProvider)
        try{
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                selector,
                preview,
                imageAnalysis,
                imageCapture,
            )
        }catch(e : Exception){
            e.printStackTrace()
        }
    }

    override fun getSavedImageUri() = savedImageUri
}