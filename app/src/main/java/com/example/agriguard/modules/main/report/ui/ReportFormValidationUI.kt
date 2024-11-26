package com.example.agriguard.modules.main.report.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R

@Preview(showSystemUi = true)
@Composable
fun ReportFormUIPreview() {
    ReportFormValidationUI(
        rememberNavController()
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReportFormValidationUI(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.White,
        floatingActionButton = {
            FloatingIcon()
        }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                        text = "Farmer Record Validation",
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
            Spacer(modifier = Modifier.height(10.dp))
            ReportDesign()
        }
    }
}

@Composable
fun ReportDesign(

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(
                BorderStroke(2.dp, Color(0xFF136204))
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Crop Activity Summary",
                fontSize = 18.sp,
                color = Color(0xFF136204),
                fontWeight = FontWeight.W800,
                fontFamily = FontFamily.SansSerif
            )
            Row(
                modifier = Modifier
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Technician",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF136204),
                    )
                    Text(
                        text = "Alex Danilo Ramos",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF136204),
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Admin",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF136204),
                    )
                    Text(
                        text = "Robert Santos",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF136204),
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center
            ){
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Farmer",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF136204),
                    )
                    Text(
                        text = "Juan Dela Cruz",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF136204),
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Damage",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF136204),
                    )
                    Text(
                        text = "50%",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF136204),
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Dates",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF136204),
                    )
                    Text(
                        text = "11-22-2024",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF136204),
                    )
                }
            }

        }
    }
}


@Composable
fun FloatingIcon(
//    navController: NavController,
//    addressDto: AddressDto,
//    currentUser: UserDto,
//    filteredResidences: List<UserDto>,
//    onExportToPDF: (List<UserDto>) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End,
    ) {
        FloatingActionButton(
            onClick = {
//                onExportToPDF(filteredResidences)
            },
            containerColor = Color(0xFF136204),
            contentColor = Color(0xFFFFFFFF),
            shape = CircleShape,
            modifier = Modifier
                .size(75.dp)
                .offset(x = (-5).dp, y = (15).dp)
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
