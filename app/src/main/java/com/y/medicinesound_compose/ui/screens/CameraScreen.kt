package com.y.medicinesound_compose.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.y.medicinesound_compose.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter


@Composable
fun CameraScreen(
    navController: NavController,
    cameraViewModel: CameraViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val imageUri = cameraViewModel.imageUri.collectAsState(initial = null).value

    var previewView: PreviewView
    var isPreview by remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        if (PackageManager.PERMISSION_GRANTED == context.checkSelfPermission(Manifest.permission.CAMERA)) {
            if (isPreview) {

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
                            .height(screenHeight * 0.8f)
                            .width(screenWidth)
                    )
                }

                Box(
                    modifier = Modifier.height(screenHeight * 0.2f),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(
                        onClick = {
                            cameraViewModel.captureAndSave(context)
                            isPreview = !isPreview
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera_black),
                            contentDescription = "카메라 촬영 버튼"
                        )
                    }
                }
            } else {
                TakenPictureContent(
                    imageUri,
                    screenHeight,
                    screenWidth,
                    onRetakeClicked = {
                        isPreview = !isPreview
                        //TODO :다시 Preview로 돌아가게 하고 기존에 찍은 URL 정보는 완전히 삭제  + isBoolean이 상태값이 되어야 하나봄..*/
                    },
                    onConfirmClicked = {
                        // TODO : BasicScreen으로 navigate하면서 Url 정보를 전달해 주어야함 */
                    }
                )
            }
        }

    }


}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TakenPictureContent(
    imageUri: Uri?,
    screenHeight: Dp,
    screenWidth: Dp,
    onRetakeClicked: () -> Unit,
    onConfirmClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(screenHeight * 0.85f)
            .width(screenWidth)
    ) {
        imageUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = "촬영한 사진",
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    Box(
        modifier = Modifier.height(screenHeight * 0.2f),
        contentAlignment = Alignment.Center,
    ) {
        TakenPictureButton(
            onRetakeClicked = {
                onRetakeClicked()
            },
            onConfirmClicked = {
                onConfirmClicked()
            }
        )
    }
}

@Composable
fun TakenPictureButton(
    onRetakeClicked: () -> Unit,
    onConfirmClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
            .padding(16.dp),
    ) {
        Button(onClick = onRetakeClicked) {
            Text(text = "다시 찍기")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onConfirmClicked) {
            Text(text = "확인")
        }
    }
}
