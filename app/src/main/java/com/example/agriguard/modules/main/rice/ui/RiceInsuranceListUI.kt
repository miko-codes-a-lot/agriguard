package com.example.agriguard.modules.main.rice.ui

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agriguard.R
import com.example.agriguard.modules.main.MainNav
import com.example.agriguard.modules.main.rice.model.dto.RiceWIthUserDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun RiceInsuranceListUI(
    navController: NavController,
    riceInsuranceList: List<RiceWIthUserDto>,
    currentUser: UserDto,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        floatingActionButton = {
            if(currentUser.isFarmers) {
                FloatingRecordsInsuranceIcon(navController)
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
                        text = "Rice Insurance List",
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
            RiceInsuranceListContainer(
                navController = navController,
                riceInsuranceList = riceInsuranceList,
                currentUser = currentUser
            )
        }
    }
}

@Composable
fun RiceInsuranceListContainer(
    navController: NavController,
    riceInsuranceList: List<RiceWIthUserDto>,
    currentUser: UserDto
) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(items = riceInsuranceList) { _, RiceInsuranceDto ->
            RiceInsuranceButton(
                riceWithUser = RiceInsuranceDto,
                navController = navController,
                currentUser = currentUser
            )
        }
    }
}

@Composable
private fun RiceInsuranceButton(
    riceWithUser: RiceWIthUserDto,
    navController: NavController,
    currentUser: UserDto
) {
    val riceInsurance = riceWithUser.rice
    val user = riceWithUser.user
    val formattedDate = if (riceWithUser.rice.fillUpDate.isNotEmpty()) {
        try {
            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            isoFormat.timeZone = TimeZone.getTimeZone("UTC")
            val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val parsedDate = isoFormat.parse(riceWithUser.rice.fillUpDate)
            parsedDate?.let { displayFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            Log.e("InDemnityButton", "Date parsing failed: ${riceWithUser.rice.fillUpDate}", e)
            "Invalid Date"
        }
    } else {
        "No Date Provided"
    }

    ElevatedButton(
        onClick = {
            navController.navigate(MainNav.RiceInsuranceDetails(riceInsurance.id!!, currentUser.id!!))
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
                else "\uD83D\uDCCC ${user.address},  ${user.firstName} ${user.lastName} - $formattedDate"
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
                    text = "${riceInsurance.status}",
                    fontSize = 17.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = when (riceInsurance.status) {
                        "approved" -> Color(0xFF136204)
                        "rejected" -> Color.Red
                        else -> Color.Red
                    }
                )
            }
        }
    }
}

@Composable
fun FloatingRecordsInsuranceIcon(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate(MainNav.RiceCreate)
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
