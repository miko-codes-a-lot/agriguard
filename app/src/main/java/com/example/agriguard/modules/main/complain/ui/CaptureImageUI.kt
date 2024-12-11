package com.example.agriguard.modules.main.complain.ui

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
import android.net.Uri
import android.widget.Toast
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
import androidx.navigation.NavController
import com.example.agriguard.R
import com.example.agriguard.modules.main.complain.viewmodel.ComplaintViewModel
import java.io.File

@Composable
fun CaptureImageUI(
    onDismiss : () -> Unit,
    navController: NavController,
    viewModel: ComplaintViewModel,
) {
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
        CameraPreviewScreen(
            viewModel = viewModel,
            onImageCaptured = { uri ->
                navController.popBackStack()
            },
            onDismiss = onDismiss
        )
    } else {

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                Modifier
                    .height(180.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(215.dp)
                        .clip(RectangleShape)
                        .background(Color(0xFFFFFFFF))
                        .border(1.dp, Color(0xFF136204), RectangleShape),
                    contentAlignment = Alignment.Center,

                    ) {
                    IconButton(
                        onClick = {
                            checkAndRequestCameraPermission()
                        },
                        modifier = Modifier
                            .size(140.dp)
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
                                .size(85.dp),
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
fun CameraPreviewScreen(
    viewModel: ComplaintViewModel,
    onImageCaptured: (Uri) -> Unit,
    onDismiss : () -> Unit,
) {
    val context = LocalContext.current
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember { ImageCapture.Builder().build() }

    LaunchedEffect(Unit) {
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
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        Button(
            onClick = {
                val outputOptions = ImageCapture.OutputFileOptions.Builder(
                    File(context.cacheDir, "captured_image.jpeg")
                ).build()

                imageCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            val file = File(context.cacheDir, "captured_image.jpeg")
                            val imageBytes = file.readBytes()
                            val base64Image = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT)
                            viewModel.updateField { it.copy(imageBase64 = base64Image) }
                            Toast.makeText(context, "Image Captured and Processed", Toast.LENGTH_SHORT).show()
                            onImageCaptured(Uri.fromFile(file))
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Toast.makeText(context, "Error Capturing Image", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                onDismiss()
            }
        ) {
            Text("Capture")
        }
    }
}