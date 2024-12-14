package com.example.agriguard.modules.main.complain.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.agriguard.R
import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import com.example.agriguard.modules.main.complain.viewmodel.ComplaintViewModel
import com.example.agriguard.modules.main.user.model.dto.UserDto
import java.io.File
import java.io.FileInputStream
import com.google.accompanist.permissions.*
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ComplaintFormUI(
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
    var expanded by remember { mutableStateOf(false) }
    val listOfCategory = listOf("Rice", "Onion")

    if (isCameraOpen) Modifier.fillMaxSize() else Modifier.height(180.dp)

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    LaunchedEffect(isCameraOpen) {
        if (isCameraOpen && cameraPermissionState.status is PermissionStatus.Granted) {
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

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Image",
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))

        if (isCameraOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape)
                    .background(Color(0xFFFFFFFF))
                    .border(1.dp, Color(0xFF136204), RectangleShape),
                contentAlignment = Alignment.Center,
            ) {
                isCameraOpen = true;
                AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
            }
            Row(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                        if (isCameraOpen) {
                            val file = File(
                                context.cacheDir,
                                "captured_image_${System.currentTimeMillis()}.jpeg"
                            )
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
                                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                            cameraPermissionState.launchPermissionRequest()
                        } else {
                            isCameraOpen = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                ) {
                    Icon(
                        painter = painterResource(id = if (isCameraOpen) R.drawable.new_icon_cam else R.drawable.addcamera),
                        contentDescription = "Default placeholder",
                        modifier = Modifier
                            .background(Color.White)
                            .clip(CircleShape)
                            .size(120.dp),
                        tint = Color(0xFF136204),
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (capturedImageUri != null) 300.dp else 200.dp),
                contentAlignment = Alignment.Center,
            ) {
                if (capturedImageUri != null) {
                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .height(350.dp)
                            .clip(RectangleShape)
                            .background(Color(0xFFFFFFFF)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = capturedImageUri),
                            contentDescription = "Captured Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(190.dp)
                            .background(Color(0xFFFFFFFF))
                            .border(1.dp, Color(0xFF136204), RectangleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        IconButton(
                            onClick = {
                                isCameraOpen = true
                                val file = File(
                                    context.cacheDir,
                                    "captured_image_${System.currentTimeMillis()}.jpeg"
                                )
                                val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

                                imageCapture.takePicture(
                                    outputOptions,
                                    ContextCompat.getMainExecutor(context),
                                    object : ImageCapture.OnImageSavedCallback {
                                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                            val file = outputFileResults.savedUri?.let { uri -> File(uri.path ?: "") }
                                            if (file != null) {
                                                val resizedBitmap = fileToResizedBitmap(file, maxWidth = 800, maxHeight = 800)
                                                val base64Image = resizedBitmap?.let { bitmapToBase64(it) }
                                                viewModel.updateField { it.copy(imageBase64 = base64Image) }

                                                capturedImageUri = Uri.fromFile(file)
                                                Toast.makeText(context, "Image Captured and Resized", Toast.LENGTH_SHORT).show()
                                            }
                                            isCameraOpen = false
                                        }

                                        override fun onError(exception: ImageCaptureException) {
                                            Toast.makeText(
                                                context,
                                                "",
                                                Toast.LENGTH_SHORT
                                            )
                                        }
                                    }
                                )
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
                    }
                }
            }
            Column(
                modifier = Modifier
                    .width(130.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .width(130.dp)
                        .padding(10.dp)
                        .clickable { expanded = !expanded },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (formState.rice) "Rice" else "Onion",
                        modifier = Modifier
                            .padding(start = 17.dp),
                        fontSize = 25.sp,
                        color = Color(0xFF136204),
                        fontWeight = FontWeight.W800,
                        fontFamily = FontFamily.SansSerif,
                    )
                    Icon(
                        painter = if (expanded) painterResource(id = R.drawable.ic_arrow_up) else painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = "Dropdown Icon",
                        tint = Color(0xFF136204),
                        modifier = Modifier.size(24.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(130.dp)
                        .background(color = Color.White)
                        .heightIn(max = 200.dp)
                ) {
                    listOfCategory.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = item,
                                    color = Color(0xFF136204),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .width(130.dp)
                                )
                            },onClick = {
                                expanded = false
                                viewModel.updateField {
                                    it.copy(
                                        rice = item == "Rice",
                                        onion = item == "Onion"
                                    )
                                }
                            }
                        )
                    }
                }
            }
            ComplainTextField(
                "Tinatayang porsyento ng pinsala",
                formState.causeOfDamage
            ) { value -> viewModel.updateField { it.copy(causeOfDamage = value) } }

            Button(
                onClick = {
                    val updatedFormState = formState.copy(userId = currentUser.id!!)
                    onSubmit(updatedFormState)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(top = 30.dp, bottom = 25.dp)
                    .fillMaxWidth()
                    .height(58.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF136204),
                    contentColor = Color(0xFFFFFFFF)
                )
            ){
                Text("Submit",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.White
                )
            }
        }
    }
}

private fun fileToBase64(file: File): String {
    return FileInputStream(file).use { inputStream ->
        val bytes = inputStream.readBytes()
        Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}

@Composable
fun ComplainTextField(label: String, value: String?, onChange: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf(value.orEmpty()) }

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .heightIn(max = 230.dp)
            .border(1.dp, Color(0xFF136204), RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            androidx.compose.material3.TextField(
                value = text,
                onValueChange = {
                    text = it
                    onChange(it)
                },
                modifier = Modifier.fillMaxSize(),
                placeholder = {
                    Text(
                        text = label,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Gray,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.White,
                    focusedTextColor = Color(0xFF136204),
                    unfocusedBorderColor = Color.White
                )
            )
        }
    }
}

fun fileToResizedBitmap(file: File, maxWidth: Int, maxHeight: Int): Bitmap? {
    return try {
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeFile(file.path, options)

        val scaleFactor = maxOf(1, options.outWidth / maxWidth, options.outHeight / maxHeight)
        val scaledOptions = BitmapFactory.Options().apply { inSampleSize = scaleFactor }

        BitmapFactory.decodeFile(file.path, scaledOptions)?.let { scaledBitmap ->
            resizeBitmap(scaledBitmap, maxWidth, maxHeight) // Return the resized bitmap
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun bitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}