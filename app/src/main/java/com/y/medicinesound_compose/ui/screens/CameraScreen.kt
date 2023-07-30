package com.y.medicinesound_compose.ui.screens

import android.Manifest
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.y.medicinesound_compose.R
import java.security.Permission

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    var previewView: PreviewView

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // TODO : BasicScrren.kt에 있는 PermissionState를 여기서도 알 수 있으면 좋을텐데.
        if (Manifest.permission.CAMERA == "android.permission.CAMERA" &&
            Manifest.permission.WRITE_EXTERNAL_STORAGE == "android.permission.WRITE_EXTERNAL_STORAGE" &&
            Manifest.permission.READ_EXTERNAL_STORAGE == "android.permission.READ_EXTERNAL_STORAGE"
        ) {
            Box(
                modifier = Modifier
                    .height(screenHeight * 0.85f)
                    .width(screenWidth)
            ) {
                AndroidView(
                    factory = {
                        previewView = PreviewView(it)
                        cameraViewModel.showCameraPreview(previewView, lifecycleOwner)
                        previewView
                    },
                    modifier = Modifier
                        .height(screenHeight * 0.85f)
                        .width(screenWidth)
                )
            }
        }
    }

    Box(
        modifier = Modifier.height(screenHeight * 0.15f),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = { cameraViewModel.captureAndSave(context) }) {
            Icon(
                painter = painterResource(id = R.drawable.camera_black),
                contentDescription = "카메라 촬영 버튼"
            )
        }
    }

}
