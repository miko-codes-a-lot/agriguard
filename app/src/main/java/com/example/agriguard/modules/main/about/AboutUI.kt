package com.example.agriguard.modules.main.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agriguard.R

@Composable
fun AboutUI (
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                    text = "  About",
                    fontSize = 27.sp,
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
        Spacer(modifier = Modifier.padding(top = 50.dp))

        Text(
            text = "MGA TAGALIKHA NG APLIKASYON",
            fontSize = 17.sp,
            color = Color(0xFF136204),
            modifier = Modifier.padding(bottom = 30.dp),
            textAlign = TextAlign.Center,
        )

        UserRow(
            users = listOf(
                Pair("ANGELICA M. AGUSTIN", R.drawable.person),
                Pair("NICOLE F. LA TORRE", R.drawable.person)
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        UserRow(
            users = listOf(
                Pair("ALLIAH XYZA D. BENOYA", R.drawable.person),
                Pair("ERLYN D. SINGSON", R.drawable.person)
            )
        )
    }
}

@Composable
fun UserRow(users: List<Pair<String, Int>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        users.forEach { (name, imageRes) ->
            Column(
                modifier = Modifier.padding(end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(150.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF136204))
                )
                Text(
                    text = name,
                    fontSize = 15.sp,
                    color = Color(0xFF136204)
                )
            }
        }
    }
}