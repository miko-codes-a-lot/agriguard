package com.example.agriguard.modules.main.farmer

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.core.content.ContextCompat
import androidx.camera.core.Preview
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.agriguard.R

@Composable
fun UploadImageUI() {
    val context = LocalContext.current
    var isPermissionGranted by remember { mutableStateOf(false) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        isPermissionGranted = isGranted
        if (isGranted) {
            Toast.makeText(context, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    fun checkAndRequestCameraPermission() {
        when (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)) {
            PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(context, "Camera Permission Already Granted", Toast.LENGTH_SHORT).show()
                isPermissionGranted = true
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    if (isPermissionGranted) {
        CameraPreviewScreen()
    } else {

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                Modifier
                    .height(170.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(210.dp)
                        .clip(RectangleShape)
                        .background(Color(0xFFFFFFFF))
                        .border(1.dp, Color(0xFF136204), RectangleShape),
                    contentAlignment = Alignment.Center,

                    ) {
                    //            if (selectedImgUri != null) {
                    //                AsyncImage(
                    //                    modifier = Modifier
                    //                        .clip(CircleShape)
                    //                        .size(140.dp)
                    //                        .clip(CircleShape),
                    //                    model = selectedImgUri,
                    //                    contentDescription = null,
                    //                    contentScale = ContentScale.Crop
                    //                )
                    //            } else {
                    IconButton(
                        onClick = {
                            checkAndRequestCameraPermission()
                        },

                        //                photoPickerLauncher.launch(
                        //                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        //                )
                        modifier = Modifier
                            .size(130.dp)
                            .padding(2.dp)
                            .clip(RectangleShape)
                            .align(Alignment.Center),
                        colors = IconButtonDefaults.iconButtonColors(Color(0xFFFFFFFF)),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.addcamera),
                            contentDescription = "Default placeholder",
                            modifier = Modifier
                                .background(Color.White)
                                .size(80.dp),
                            tint = Color(0xFF136204),
                        )
                    }
                    // }
                }
            }
        }
    }
}

@Composable
fun CameraPreviewScreen() {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    LaunchedEffect(lensFacing) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraxSelector,
                    preview,
                    imageCapture
                )
                preview.setSurfaceProvider(previewView.surfaceProvider)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(context))
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        Button(onClick = { captureImage(imageCapture, context) }) {
            Text(text = "Capture Image")
        }
    }
}

private fun captureImage(imageCapture: ImageCapture, context: Context) {
    val name = "CameraxImage.jpeg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        .build()
    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                println("Successs")
            }

            override fun onError(exception: ImageCaptureException) {
                println("Failed $exception")
            }

        })
}

