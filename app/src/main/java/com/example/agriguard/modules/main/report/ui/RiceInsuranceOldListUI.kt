package com.example.agriguard.modules.main.report.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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

@Composable
fun RiceInsuranceOldListUI(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    text = "Rice Insurance List",
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
//        RiceInsuranceList(navController)
    }
}

//@Composable
//fun RiceInsuranceList(navController: NavController) {
//    val indemnityList = listOf(
//        "Submitted Files" to "May 25, 2024",
//        "Submitted Files" to "Sep 02, 2024",
//    )
//
//    LazyColumn(
//        modifier = Modifier
//            .padding(bottom = 50.dp)
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(10.dp)
//    ) {
//        itemsIndexed(items = indemnityList) { _, (files, date) ->
//            RiceButton(file = files, date = date, navController = navController)
//        }
//    }
//}
//
//@Composable
//private fun RiceButton(file: String, date: String, navController: NavController) {
//    ElevatedButton(
//        onClick = { navController.navigate(MainNav.RiceInsuranceForm) },
//        colors = ButtonDefaults.elevatedButtonColors(
//            containerColor = Color(0xFFFFFFFF),
//            contentColor = Color(0xFF136204)
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(63.dp),
//        elevation = ButtonDefaults.elevatedButtonElevation(
//            defaultElevation = 4.dp,
//            pressedElevation = 8.dp
//        ),
//        shape = RectangleShape
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "$file Report",
//                fontSize = 17.sp,
//                textAlign = TextAlign.Start,
//                fontWeight = FontWeight.Bold,
//                fontFamily = FontFamily.SansSerif,
//            )
//            Spacer(modifier = Modifier.weight(1f))
//            Text(
//                text = date,
//                fontSize = 15.sp,
//                textAlign = TextAlign.End,
//                fontWeight = FontWeight.Bold,
//                fontFamily = FontFamily.SansSerif,
//            )
//        }
//    }
//}
