package com.example.agriguard.modules.main.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.agriguard.R
import com.example.agriguard.modules.main.MainNav

@Composable
fun SampleFormUI(
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
//                            navController.navigate(MainNav.RiceModule)
                        }
                ){
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
//                            navController.navigate(MainNav.OnionModule)
                        }
                ){
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
//                val items = listOf(
//                    ItemData(
//                        R.drawable.fm,
//                        "Rice",
//                        "Sanhi ito ng rice blast na kung saan inaatake ng sakit ang batok ng uhay kaya't nangingitim ang huling buko",
//                        "- Isinagawa ang pag papahinga ng lupa at makipagsabayan ng tanim.\n- Gumamit ng matibay na barayti gaya ng PSB Rc 82, NSIC Rc 122, at PSB Rc 10.\n - Panatilihing malinis ang palayan."
//                    ),
//                    ItemData(
//                        R.drawable.onion,
//                        "Onion",
//                        "Ang mga sibuyas na nasira ng smut, and onion smut (Urocystis cepulae) ay isang uri ng fungus na umaatake sa mga sibuyas at leek",
//                        "- Paggamit ng mga uri ng sibuyas na may resistensya.\n- Paggamot sa buto gamit ang fungicide upang protektahan ang mga lumilitaw na punla\n - Pag lalagay ng mga soil fumigants."
//                    ),
//                    ItemData(
//                        R.drawable.fm,
//                        "Rice",
//                        "Sanhi ito ng rice blast na kung saan inaatake ng sakit ang batok ng uhay kaya't nangingitim ang huling buko",
//                        "- Isinagawa ang pag papahinga ng lupa at makipagsabayan ng tanim.\n- Gumamit ng matibay na barayti gaya ng PSB Rc 82, NSIC Rc 122, at PSB Rc 10.\n - Panatilihing malinis ang palayan."
//                    ),
//                 )
//                LazyRow(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(30.dp),
//                    contentPadding = PaddingValues(horizontal = 16.dp)
//                ) {
//                    items(items.size) { index ->
//                        ImageWithText(
//                            imageRes = items[index].imageRes,
//                            text1 = items[index].text1,
//                            text2 = items[index].text2,
//                            text3 = items[index].text3,
//                        )
//                    }
//                }
            }
        }
    }
}

@Composable
fun ImageWithText(imageRes: Int,text1: String,text2: String, text3: String) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(135.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFFFFFFF))
                .border(2.dp, Color(0xFF136204), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,

            ) {
            AsyncImage(
                model = imageRes,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .height(240.dp)
                    .size(200.dp)
            )
        }
        Text(
            text = text1,
            modifier = Modifier
                .padding(top = 5.dp),
            fontSize = 25.sp,
            color = Color(0xFF136204),
            fontWeight = FontWeight.W800,
            fontFamily = FontFamily.SansSerif,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedCard(
            modifier = Modifier
                .width(280.dp)
                .border(1.dp, Color(0xFF136204), RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Cause of Damage",
                    modifier = Modifier
                        .padding(top = 15.dp),
                    lineHeight = 20.sp,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color =  Color(0xFF136204)
                )
            }
            Text(
                text = text2,
                modifier = Modifier
                    .padding(10.dp),
                lineHeight = 20.sp,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                color =  Color(0xFF136204),
            )

        }
        OutlinedCard(
            modifier = Modifier
                .padding(top = 15.dp)
                .width(280.dp)
                .border(1.dp, Color(0xFF136204), RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Treatment",
                    modifier = Modifier
                        .padding(top = 15.dp),
                    lineHeight = 20.sp,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color =  Color(0xFF136204),
                )
            }
            Text(
                text = text3,
                modifier = Modifier
                    .padding(10.dp),
                lineHeight = 20.sp,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                color =  Color(0xFF136204),
            )
        }
    }
}
data class ItemData(val imageRes: Int, val text1: String, val text2: String, val text3: String)