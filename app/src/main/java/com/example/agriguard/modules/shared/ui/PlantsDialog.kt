package com.example.agriguard.modules.shared.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.agriguard.R

@Composable
fun PlantsDialog(
    onDismiss : () -> Unit,
    navController: NavController
) {
    val showSampleFormUI = remember { mutableStateOf(false) }
    val selectedPlant = remember { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 700.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .clickable {
                            selectedPlant.value = "Rice"
                            showSampleFormUI.value = true
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rice_icon),
                            contentDescription = "Rice",
                            modifier = Modifier.size(120.dp)
                        )
                        Text(
                            text = "Rice",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(top = 20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .clickable {
                            selectedPlant.value = "Onion"
                            showSampleFormUI.value = true
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.onion_icon),
                            contentDescription = "Onion",
                            modifier = Modifier.size(120.dp)
                                .padding(start = 15.dp)
                        )
                        Text(
                            text = "Onion",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(top = 20.dp)
                        )
                    }
                }
            }
        }
        if (showSampleFormUI.value) {
            DiseasePetsDialog(
                selectedPlant = selectedPlant.value,
                onDismiss = { showSampleFormUI.value = false
                    onDismiss()
                },
                navController = navController
            )
        }
    }
}