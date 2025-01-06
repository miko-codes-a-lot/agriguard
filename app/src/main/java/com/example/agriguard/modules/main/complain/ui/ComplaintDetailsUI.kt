package com.example.agriguard.modules.main.complain.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agriguard.MainActivity
import com.example.agriguard.R
import com.example.agriguard.modules.main.complain.model.dto.ComplainWithUserDto
import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import com.example.agriguard.modules.main.onion.ui.openFile
import com.example.agriguard.modules.main.setting.resizeBitmap
import com.example.agriguard.modules.main.user.model.dto.UserDto
import java.io.ByteArrayOutputStream

@Composable
fun ComplaintDetailsUI(
    title: String,
    complainWithUserDto: ComplainWithUserDto,
    currentUser: UserDto,
    complaintInsurance: ComplaintInsuranceDto,
    status: MutableState<String> = rememberSaveable { mutableStateOf("pending") },
    onClickEdit: () -> Unit = {},
    onClickLike: (isLike: Boolean) -> Unit = {},
) {
    val statesValue = remember(complaintInsurance) {
        listOf(
            "Status" to (complaintInsurance.status ?: "Pending"),
            "Rice" to if (complaintInsurance.rice) "Yes" else "No",
            "Onion" to if (complaintInsurance.onion) "Yes" else "No",
            "Variety" to (complaintInsurance.variety ?: "N/A"),
            "Area Damage" to (complaintInsurance.areaDamage ?: "N/A"),
            "Degree of Damage" to (complaintInsurance.degreeOfDamage ?: "N/A"),
            "Cause of Damage" to (complaintInsurance.causeOfDamage ?: "N/A"),
        ).associate { (label, value) -> label to mutableStateOf(value) }
    }

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        floatingActionButton = {
            if(currentUser.isAdmin){
                ComplaintInsurancePrintIcon (
                    fetchComplaintDetails = complainWithUserDto.complaint,
                    onExportToPDF= { data ->
                        exportComplaintDetails(
                            context = context,
                            user = complainWithUserDto.user,
                            data = data,
                            onFinish = { file ->
                                openFile(context, file)
                            },
                            onError = { e ->
                                Toast.makeText(
                                    context,
                                    "Error creating PDF: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )

                    },
                    context = context
                )
            } else {
                FloatingComplaintsInsuranceIcon(
                    onClickEdit = onClickEdit,
                    onClickLike = onClickLike,
                    currentUser = currentUser,
                    status = status
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 50.dp))

            Text(
                text = title,
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 3.dp, top = 7.dp)
            )

            Spacer(modifier = Modifier.padding(top = 30.dp))

            ImageContainer(imageBase64 = complaintInsurance.imageBase64)

            Spacer(modifier = Modifier.padding(top = 20.dp))

            ViewData(statesValue)

            Spacer(modifier = Modifier.padding(bottom = 80.dp))
        }
    }
}

@Composable
fun FloatingComplaintsInsuranceIcon(
    onClickEdit: () -> Unit = {},
    onClickLike: (isLike: Boolean) -> Unit = {},
    currentUser: UserDto,
    status: MutableState<String>
) {
    val showEditButton = currentUser.isFarmers && (status.value == "pending" || status.value == "rejected")
    val showLikeButton = currentUser.isTechnician && (status.value == "pending" || status.value == "rejected")
    val showDislikeButton = currentUser.isTechnician && status.value == "pending"

    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End
    ) {

        if (showEditButton) {
            FloatingActionButton(
                onClick = onClickEdit,
                containerColor = Color(0xFF136204),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .size(75.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit"
                )
            }
        }
        if (showLikeButton) {
            FloatingActionButton(
                onClick = { onClickLike(true) },
                containerColor = Color(0xFF136204),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .size(75.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.thumb),
                    contentDescription = "Like"
                )
            }
        }
        if (showDislikeButton) {
            FloatingActionButton(
                onClick = { onClickLike(false) },
                containerColor = Color.Red,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .size(75.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.thumb_down),
                    contentDescription = "Dislike"
                )
            }
        }
    }
}

@Composable
fun ImageContainer(imageBase64: String?) {
    val imageBitmap = remember(imageBase64) {
        imageBase64?.let { decodeBase64ToBitmap(it, maxWidth = 500, maxHeight = 500) }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = "No Image Available",
                color = Color.Gray,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        }
    }
}

fun decodeBase64ToBitmap(base64: String, maxWidth: Int, maxHeight: Int): Bitmap? {
    return try {
        val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
        val originalBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        val resizedBitmap = resizeBitmap(originalBitmap, maxWidth, maxHeight)
        compressBitmap(resizedBitmap)
    } catch (e: Exception) {
        null
    }
}

fun compressBitmap(bitmap: Bitmap, quality: Int = 75): Bitmap {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    val byteArray = outputStream.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height

    val ratioBitmap = width.toFloat() / height.toFloat()
    val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

    val finalWidth: Int
    val finalHeight: Int

    if (ratioMax > ratioBitmap) {
        finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
        finalHeight = maxHeight
    } else {
        finalWidth = maxWidth
        finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
    }

    return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true)
}

@Composable
fun ViewData(stateValues: Map<String, MutableState<String>>) {
    val riceState = stateValues["Rice"]?.value ?: "No"
    val onionState = stateValues["Onion"]?.value ?: "No"

    Column {
        stateValues.forEach { (label, state) ->
            if (label == "Rice" && onionState == "Yes") return@forEach
            if (label == "Onion" && riceState == "Yes") return@forEach

            TextContainer(textLabel = label, textValue = state.value)
        }
    }
}

@Composable
fun TextContainer(textLabel: String, textValue: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = textLabel,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(" : ", fontWeight = FontWeight.Bold, color = Color.Black, fontFamily = FontFamily.SansSerif)
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val strokeWidth = 2f
                    val yPosition = size.height - strokeWidth
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, yPosition),
                        end = Offset(size.width, yPosition),
                        strokeWidth = strokeWidth
                    )
                }
            ) {
                    Text(
                        text = textValue,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp)
                            .heightIn(max = 60.dp)
                            .verticalScroll(rememberScrollState()),
                        fontSize = 17.sp,
                        fontFamily = FontFamily.SansSerif,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
            }
        }
    }
}

@Composable
fun ComplaintInsurancePrintIcon (
    fetchComplaintDetails: ComplaintInsuranceDto,
    onExportToPDF: (ComplaintInsuranceDto) -> Unit,
    context: Context
) {
    val activity = context as? MainActivity
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P && activity != null) {
                    activity.requestStoragePermission()
                }
                onExportToPDF(fetchComplaintDetails)
            },
            containerColor = Color(0xFF136204),
            contentColor = Color(0xFFFFFFFF),
            shape = CircleShape,
            modifier = Modifier
                .size(75.dp)
                .offset(x = (-5).dp, y = (-7).dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.printer),
                contentDescription = "Export",
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }
    }
}