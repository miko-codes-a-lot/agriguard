package com.example.agriguard.modules.main.user.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.io.File

@Composable
fun UploadIdUI(
){
    val coroutineScope = rememberCoroutineScope()
    var selectedImgUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

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
            if (selectedImgUri != null) {
                AsyncImage(
                    modifier = Modifier
                        .size(355.dp)
                        .clip(RectangleShape),
                    model = selectedImgUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "Upload ID",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,

                )
            }
        }
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

fun getBytesFromUri(context: Context, uri: Uri): ByteArray? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use { it.readBytes() }
    } catch (e: Exception) {
        null
    }
}

fun saveBitmapToUri(context: Context, bitmap: Bitmap?, maxWidth: Int = 500, maxHeight: Int = 500): Uri? {
    val resizedBitmap = bitmap?.let { resizeBitmap(it, maxWidth, maxHeight) }

    val file = File(context.cacheDir, "profile_image.png")
    file.outputStream().use { out ->
        resizedBitmap?.compress(Bitmap.CompressFormat.PNG, 80, out)
        out.flush()
    }
    return Uri.fromFile(file)
}

fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
    val width: Int
    val height: Int
    if (bitmap.width > bitmap.height) {
        width = maxWidth
        height = (maxWidth / aspectRatio).toInt()
    } else {
        height = maxHeight
        width = (maxHeight * aspectRatio).toInt()
    }
    return Bitmap.createScaledBitmap(bitmap, width, height, true)
}