package com.example.agriguard.modules.main.complain.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ComplaintInsurancePreview(
    title: String,
    complaintInsurance: ComplaintInsuranceDto,
) {
    val statesValue = remember(complaintInsurance) {
        listOf(
            "User ID" to complaintInsurance.userId,
            "Rice" to if (complaintInsurance.rice) "Yes" else "No",
            "Onion" to if (complaintInsurance.onion) "Yes" else "No",
            "Cause of Damage" to (complaintInsurance.causeOfDamage ?: "N/A"),
            "Treatment" to (complaintInsurance.treatment ?: "N/A"),
            "Image Base64" to (complaintInsurance.imageBase64 ?: "No Image"),
            "Status" to (complaintInsurance.status ?: "Pending"),
            "Created By" to (complaintInsurance.createdById?.toString() ?: "N/A"),
            "Created At" to dateFormatContainer(complaintInsurance.createdAt.toString()),
            "Last Updated By" to (complaintInsurance.lastUpdatedById?.toString() ?: "N/A"),
            "Last Updated At" to dateFormatContainer(complaintInsurance.lastUpdatedAt.toString()),
            "Deleted By" to (complaintInsurance.deletedById?.toString() ?: "N/A"),
            "Deleted At" to dateFormatContainer(complaintInsurance.deletedAt?.toString() ?: "N/A"),
        ).associate { (label, value) -> label to mutableStateOf(value) }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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

        Spacer(modifier = Modifier.padding(top = 10.dp))

        ImageContainer(imageBase64 = complaintInsurance.imageBase64)

        ViewData(statesValue)

        Spacer(modifier = Modifier.padding(bottom = 50.dp))
    }
}

@Composable
fun ImageContainer(imageBase64: String?) {
    val imageBitmap = remember(imageBase64) {
        imageBase64?.let { decodeBase64ToBitmap(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp)
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

fun decodeBase64ToBitmap(base64: String): Bitmap? {
    return try {
        val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        null
    }
}

@Composable
fun ViewData(stateValues: Map<String, MutableState<String>>) {
    Column {
        stateValues.forEach { (label, states) ->
            TextContainer(textLabel = label, textValue = states.value)
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
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = textValue,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp),
                        fontSize = 17.sp,
                        fontFamily = FontFamily.SansSerif,
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

fun dateFormatContainer(dateString: String): String {
    return try {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dates = isoFormat.parse(dateString)
        displayFormat.format(dates)
    } catch (e: Exception) {
        "Select Date"
    }
}
