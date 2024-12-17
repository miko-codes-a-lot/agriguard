package com.example.agriguard.modules.main.user.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.agriguard.modules.main.user.service.UserService
import com.example.agriguard.modules.main.user.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun UploadIdUI(
    onImageSelected: (Uri) -> Unit = {},
    currentUserId: String,
    userService: UserService = hiltViewModel<UserViewModel>().userService
){
    val coroutineScope = rememberCoroutineScope()
    var selectedImgUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    LaunchedEffect(currentUserId) {
        val userDto = userService.fetchOne(currentUserId)
        if (!userDto.validId.isNullOrEmpty()) {
            val byteArray = android.util.Base64.decode(userDto.validId, android.util.Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            selectedImgUri = saveBitmapToUri(context, bitmap)
        } else {
            Log.e("UserImageUI", "Image Base64 is null or empty for user: $currentUserId")
        }
    }

    val stroke = Stroke(
        width = 4f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImgUri = uri
                onImageSelected(uri)
                coroutineScope.launch {
                    val byteArray = getBytesFromUri(context, uri)
                    if (byteArray != null) {
                        val result = userService.saveValidId(currentUserId, byteArray)
                        if (result.isSuccess) {
                            delay(500)
                            val userDto = userService.fetchOne(currentUserId)
                            if (userDto.validId.isNullOrEmpty()) {
                            } else {
                                val byteArray = android.util.Base64.decode(userDto.validId, android.util.Base64.DEFAULT)
                                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                                selectedImgUri = saveBitmapToUri(context, bitmap)
                            }
                        } else {
                            Log.e("UserImageUI", "Failed to save profile picture")
                        }
                    }
                }
            }
        }
    )

    Box(
        Modifier
            .height(600.dp)
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
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
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