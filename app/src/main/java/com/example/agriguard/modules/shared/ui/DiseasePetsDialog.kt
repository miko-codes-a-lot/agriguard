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
import com.example.agriguard.modules.main.MainNav

@Composable
fun DiseasePetsDialog(
    selectedPlant: String,
    onDismiss : () -> Unit,
    navController: NavController
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
//                .wrapContentHeight()
                .fillMaxWidth()
                .heightIn(max = 700.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            if(selectedPlant == "Rice"){
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
                                navController.navigate(MainNav.RiceDisease)
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
                                painter = painterResource(id = R.drawable.cropdisease),
                                contentDescription = "Disease",
                                modifier = Modifier.size(120.dp)
                            )
                            Text(
                                text = "Plant Disease",
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
                                navController.navigate(MainNav.RicePets)
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
                                painter = painterResource(id = R.drawable.insect),
                                contentDescription = "Pesticides",
                                modifier = Modifier.size(120.dp)
                                    .padding(start = 15.dp)
                            )
                            Text(
                                text = "Insects Pesticides",
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
                                navController.navigate(MainNav.RiceWeed)
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
                                painter = painterResource(id = R.drawable.weedsdisease),
                                contentDescription = "Pesticides",
                                modifier = Modifier.size(120.dp)
                                    .padding(start = 15.dp)
                            )
                            Text(
                                text = "Weed Infestation",
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 20.dp)
                            )
                        }
                    }
                }
            }else if (selectedPlant == "Onion") {
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
                                navController.navigate(MainNav.OnionDisease)
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
                                painter = painterResource(id = R.drawable.cropdisease),
                                contentDescription = "Disease",
                                modifier = Modifier.size(120.dp)
                            )
                            Text(
                                text = "Plant Disease",
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
                                navController.navigate(MainNav.OnionPets)
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
                                painter = painterResource(id = R.drawable.insect),
                                contentDescription = "Pesticides",
                                modifier = Modifier.size(120.dp)
                                    .padding(start = 15.dp)
                            )
                            Text(
                                text = "Insects Pesticides",
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
                                navController.navigate(MainNav.OnionWeed)
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
                                painter = painterResource(id = R.drawable.weedsdisease),
                                contentDescription = "Pesticides",
                                modifier = Modifier.size(120.dp)
                                    .padding(start = 15.dp)
                            )
                            Text(
                                text = "Weed Infestation",
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
        }
    }
}
