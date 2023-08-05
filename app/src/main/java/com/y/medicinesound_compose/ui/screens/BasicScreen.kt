package com.y.medicinesound_compose.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.y.medicinesound_compose.R
import com.y.medicinesound_compose.utils.NavDestination
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.onGloballyPositioned


@OptIn(ExperimentalPermissionsApi::class, ExperimentalCoilApi::class)
@Composable
fun BasicScreen(
    navController: NavController,
    imageUri: String? = null,
    basicViewModel: BasicScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    // TODO : imageUri를 basicViewModel 내부에 사용자에게 보여주기 위한 상태로 담아야해!
    val basicUiState by basicViewModel.uiState.collectAsState()

    // 앱 키자마자 모든 권한을 요청
    val permissions = listOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.INTERNET
    )


    val permissionState = rememberMultiplePermissionsState(permissions = permissions)

    if (!permissionState.allPermissionsGranted) {
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    if (imageUri != null) {
        basicViewModel.updateUiStateImageUri(imageUri)
    }

    // UI
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val imageWidth = remember { mutableStateOf(0.0f) }

        Image(
            painter = if (imageUri == null) painterResource(R.drawable.medicine_temp_img)
            else rememberImagePainter(data = imageUri),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .onGloballyPositioned { coordinates ->
                    imageWidth.value = coordinates.size.width.toFloat()
                },
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        Column(
            modifier = Modifier.padding(16.dp, 8.dp)
        ) {
            BasicButton(
                btnTextResId = R.string.btn_camera,
                btnIconResId = R.drawable.camera_black,
                onClicked = { navController.navigate(NavDestination.CAMERA_SCREEN) }
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            BasicButton(
                btnTextResId = R.string.btn_cognize,
                btnIconResId = R.drawable.check_black,
                onClicked = {
                    if (imageUri != null) {
                        Log.d("BasicScreen", "medicineImg is not empty $imageUri")
                        val bitmap = getBitmapFromUri(context, imageUri)
                        val medicineName = basicViewModel.getObjDetectionResult(
                            bitmap,
                            imageWidth.value,
                            320.0f
                        )
                        Log.d("BasicScreen", "medicineName : ${basicUiState.medicineName}")
                    } else {
                        Log.d("BasicScreen", "medicineImg is empty ${basicUiState.medicineImg}")
                        // TODO : 사진 촬영을 먼저하라는 안내가 필요함.
                    }
                }
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            BasicButton(
                btnTextResId = R.string.btn_extract_info,
                btnIconResId = R.drawable.extract_black,
                onClicked = {
                    if (basicUiState.medicineName.isNotEmpty()) {
                        basicViewModel.getEYakEffect(basicUiState.medicineName)
                    }
                    // TODO : else일 때 사진을 찍고 인식하기 버튼을 사용하라는 안내가 필요
                }
            )
        }

        Column(
            modifier = Modifier
        ) {
            TitleAndContentText(
                title = "제품명",
                content = if (basicUiState.medicineName != "") basicUiState.medicineName else "인식된 약의 이름"
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            if (basicUiState.medicineEffect.isNotEmpty()) {
                TitleAndContentText(
                    title = "효능 및 효과",
                    content = basicUiState.medicineEffect
                )
            } else {
                TitleAndContentText(
                    title = "효능 및 효과",
                    content = "인식된 효능 효과"
                )
            }
        }
    }
}

@Composable
fun BasicButton(
    @StringRes btnTextResId: Int,
    @DrawableRes btnIconResId: Int,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Button(
            onClick = onClicked,
            shape = RoundedCornerShape(5.dp),
            contentPadding = PaddingValues(24.dp, 12.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.orange1)),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = btnIconResId), contentDescription = null)
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Text(stringResource(btnTextResId))
            }
        }
    }
}

@Composable
fun TitleAndContentText(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = colorResource(R.color.green1))
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )
        Text(
            text = content,
            style = TextStyle(
                fontSize = 14.sp,
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(colorResource(id = R.color.white))
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
        )
    }
}

fun getBitmapFromUri(context: Context, imageUri: String): Bitmap {
    Log.d("getBitmapFromUri", "imageUri: $imageUri")
    val uri = Uri.parse(imageUri)
    Log.d("getBitmapFromUri", "uri: $uri")
    val src = ImageDecoder.createSource(context.contentResolver, uri)
    Log.d("getBitmapFromUri", "src: $src")
    return ImageDecoder.decodeBitmap(src)
}


@Preview(showBackground = true)
@Composable
fun BasicPreview() {
    BasicScreen(
        navController = NavController(LocalContext.current),
    )
}