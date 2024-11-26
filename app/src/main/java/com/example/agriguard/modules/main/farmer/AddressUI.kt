package com.example.agriguard.modules.main.farmer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agriguard.modules.main.MainNav

@Composable
fun AddressUI(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Address",
                fontFamily = FontFamily.SansSerif,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = Color(0xFF136204)
            )
            Spacer(modifier = Modifier.height(10.dp))
            ListAddress(navController)

        }
    }
}

@Composable
fun ListAddress(navController: NavController) {
    val addresses: HashMap<String, Int> = hashMapOf(
        "Ambulong" to 1,
        "Ansilay" to 2,
        "Bagong Sikat" to 3,
        "Bangkal" to 4,
        "Barangay 1 Poblacion" to 5,
        "Barangay 2 Poblacion" to 6,
        "Batasan" to 7,
        "Bayotbot" to 8,
        "Bubog" to 9,
        "Buri" to 10,
        "Camburay" to 11,
        "Caminawit" to 12,
        "Catayungan" to 14,
        "Central" to 15,
        "La Curva" to 16,
        "Labangan Iling" to 17,
        "Mabini" to 18,
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(addresses.entries.toList()) { entry ->
            ListButton(address = entry.key, navController = navController)
        }
    }
}

@Composable
private fun ListButton(address: String, navController: NavController) {
    ElevatedButton(
        onClick = {
            navController.navigate(MainNav.FarmersList)
        },
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor =  Color.White,
            contentColor = Color(0xFF136204)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(58.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        border = BorderStroke(2.dp, Color(0xFF136204))
    ) {
        Text(
            text = address,
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
        )
    }
}