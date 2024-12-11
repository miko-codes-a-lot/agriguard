package com.example.agriguard.modules.main.complain.ui

import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.agriguard.R
import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import com.example.agriguard.modules.main.complain.viewmodel.ComplaintViewModel
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.ui.validateForm
import com.example.agriguard.modules.shared.ui.TextField
import java.io.File
import java.io.FileInputStream

@Composable
fun ComplaintInsuranceFormUI(
    navController: NavController,
    currentUser: UserDto,
    viewModel: ComplaintViewModel,
    onSubmit: (ComplaintInsuranceDto) -> Unit
) {
    val formState by viewModel.formState.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember { ImageCapture.Builder().build() }
    var isCameraOpen by remember { mutableStateOf(false) }
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 0.dp, vertical = 4.dp)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFF136204),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Complain Form",
                    fontSize = 25.sp,
                    color = Color(0xFF136204),
                    fontWeight = FontWeight.W800,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Image",
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))

        Box(
            modifier = Modifier
                .size(215.dp)
                .clip(RectangleShape)
                .background(Color(0xFFFFFFFF))
                .border(1.dp, Color(0xFF136204), RectangleShape),
            contentAlignment = Alignment.Center,

            ) {

        }

        LaunchedEffect(isCameraOpen) {
            if (isCameraOpen) {
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
        }

        Box(
            modifier = Modifier
                .size(215.dp)
                .clip(RectangleShape)
                .background(Color(0xFFFFFFFF))
                .border(1.dp, Color(0xFF136204), RectangleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isCameraOpen) {
                AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
            } else {
                capturedImageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(model = uri),
                        contentDescription = "Captured Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        TextField("Treatment", formState.causeOfDamage) { value -> viewModel.updateField { it.copy(causeOfDamage = value) } }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    if (isCameraOpen) {
                        val file = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpeg")
                        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

                        imageCapture.takePicture(
                            outputOptions,
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                    val base64Image = fileToBase64(file)
                                    viewModel.updateField { it.copy(imageBase64 = base64Image) }

                                    capturedImageUri = Uri.fromFile(file)
                                    Toast.makeText(context, "Image Captured and Converted to Base64", Toast.LENGTH_SHORT).show()
                                    isCameraOpen = false
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    Toast.makeText(context, "Error Capturing Image", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    } else {
                        isCameraOpen = true
                    }
                }
            ) {
                Text(if (isCameraOpen) "Capture" else "Open Camera")
            }
        }

        Button(
            onClick = {
                val updatedFormState = formState.copy(userId = currentUser.id!!)
                onSubmit(updatedFormState)
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }

    }

}

private fun fileToBase64(file: File): String {
    return FileInputStream(file).use { inputStream ->
        val bytes = inputStream.readBytes()
        Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}
