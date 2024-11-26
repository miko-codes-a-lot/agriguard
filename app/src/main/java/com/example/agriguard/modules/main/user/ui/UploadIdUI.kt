package com.example.agriguard.modules.main.user.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showSystemUi = true)
@Composable
fun UploadIdUIPreview() {
    UploadIdUI()
}

@Composable
fun UploadIdUI(){

    val stroke = Stroke(
        width = 4f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    Box(
        Modifier
            .height(140.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp, end = 5.dp)
                .size(355.dp)
                .fillMaxWidth()
                .clip(RectangleShape)
                .background(Color.White)
                .drawBehind {
                    drawRoundRect(
                        color = Color(0xFF136204),
                        style = stroke,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
//                photoPickerLauncher.launch(
//                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//                )
                },
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center),
                colors = IconButtonDefaults.iconButtonColors(Color.White),
            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.upload_id),
//                    contentDescription = "Select Profile",
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    tint = Color(0xFF136204)
//                )
                Text(
                    text = "Upload ID",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,

                )
            }
        }
    }
}