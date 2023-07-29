package com.y.medicinesound_compose.ui.screens

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.y.medicinesound_compose.R


@Composable
fun BasicScreen() {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(
                R.drawable.medicine_temp_img
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,

            )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        Column(
            modifier = Modifier.padding(16.dp, 8.dp)
        ) {
            BasicButton(
                btnTextResId = R.string.btn_camera,
                btnIconResId = R.drawable.camera_black,
                onClicked = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            BasicButton(
                btnTextResId = R.string.btn_cognize,
                btnIconResId = R.drawable.check_black,
                onClicked = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            BasicButton(
                btnTextResId = R.string.btn_extract_info,
                btnIconResId = R.drawable.extract_black,
                onClicked = { /*TODO*/ }
            )
        }

        Column(
            modifier = Modifier
        ) {
            TitleAndContentText(title = "제품명", content = "제품 이름")
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            TitleAndContentText(title = "효능 및 효과", content = "이 약은 해열 및 감기에 의한 동통(통증)과 두통, 치통, 근육통, 허리통증, 생리통, 관절통의 완화에 사용합니다.")
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


@Preview(showBackground = true)
@Composable
fun BasicPreview() {
    BasicScreen()
}