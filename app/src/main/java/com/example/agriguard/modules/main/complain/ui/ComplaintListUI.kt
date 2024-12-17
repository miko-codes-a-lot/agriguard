package com.example.agriguard.modules.main.complain.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R
import com.example.agriguard.modules.main.MainNav
import com.example.agriguard.modules.main.complain.model.dto.ComplainWithUserDto
import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Preview(showSystemUi = true)
@Composable
fun ReportListUIPreview() {
    ComplaintListUI(
        rememberNavController(),
        complaintWithUser = listOf(),
        currentUser = UserDto(),
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ComplaintListUI(
    navController: NavController,
    complaintWithUser: List<ComplainWithUserDto>,
    currentUser: UserDto
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        floatingActionButton = {
            if(currentUser.isFarmers){
                FloatingComplaint(
                    currentUser = currentUser,
                    navController = navController,
                    cameraPermissionState = cameraPermissionState,
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
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
                        text = "Complaints List",
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
            Spacer(modifier = Modifier.height(10.dp))
            ReportList(
                navController = navController,
                complaintWithUser = complaintWithUser,
                currentUser = currentUser
            )
        }
    }
}

@Composable
fun ReportList(
    navController: NavController,
    complaintWithUser: List<ComplainWithUserDto>,
    currentUser: UserDto
) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(items = complaintWithUser) { _, complaint ->
            ReportButton(
                complaintInsurance = complaint,
                navController = navController,
                currentUser = currentUser
            )
        }
    }
}

@Composable
private fun ReportButton(
    complaintInsurance: ComplainWithUserDto,
    navController: NavController,
    currentUser: UserDto,
) {
    val complaint = complaintInsurance.complaint
    val user = complaintInsurance.user
    fun formatIsoDateToDisplay(dateString: String?): String {
        if (dateString.isNullOrEmpty()) return "Date not available"
        return try {
            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            val parsedDate = isoFormat.parse(dateString)
            parsedDate?.let { displayFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            Log.e("InDemnityButton", "Date parsing failed: $dateString", e)
            "Invalid Date"
        }
    }
    val formattedDate = formatIsoDateToDisplay(complaintInsurance.complaint.createdAt)

    ElevatedButton(
        onClick = {
            navController.navigate(MainNav.ComplaintDetails(complaint.id!!))
        },
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFFFFFFFF),
            contentColor = Color(0xFF136204)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(63.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        shape = RectangleShape
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                val text = if (currentUser.isFarmers) formattedDate
                else "${user.firstName} ${user.lastName} - $formattedDate"

                Text(
                    text = text,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(end = 5.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.weight(1f))
            }

            item {
                Text(
                    text = "${complaint.status}",
                    fontSize = 17.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = when (complaint.status) {
                        "approved" -> Color(0xFF136204)
                        "rejected" -> Color.Red
                        else -> Color.Red
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FloatingComplaint(
    currentUser: UserDto,
    navController: NavController,
    cameraPermissionState: PermissionState,
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = {
                if (!cameraPermissionState.status.isGranted) {
                    cameraPermissionState.launchPermissionRequest()
                } else {
                    navController.navigate(MainNav.ComplainCreate)
                }
            },
            containerColor = Color(0xFF136204),
            contentColor = Color(0xFFFFFFFF),
            shape = CircleShape,
            modifier = Modifier
                .size(75.dp)
                .offset(x = (-5).dp, y = (-7).dp)
        ){
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}
