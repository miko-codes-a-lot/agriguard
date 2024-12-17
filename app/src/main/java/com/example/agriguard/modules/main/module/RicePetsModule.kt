package com.example.agriguard.modules.main.module

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agriguard.R

@Composable
fun RicePetsModule(){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn{
            item{
                RicePetsSection("Brown Plant Hopper", R.drawable.ricepetsp1,"Ang Brown Planthopper (BPH) ay isang peste na karaniwang umaatake sa palay (rice) at nagiging sanhi " +
                        "ng malubhang pinsala sa mga tanim. Ang mga insekto na ito ay sumisipsip ng sap mula sa mga dahon, " +
                        "na nagiging sanhi ng pag-yellowing ng mga dahon at pagbawas sa ani.")
            }
            item{
                RicePetsSection("Stem Borer", R.drawable.ricepetsp2,"Ang Stem Borer ay isang peste na karaniwang umaatake sa mga tanim na palay (rice) at iba pang " +
                        "cereal crops. Ang mga larva ng stem borer ay pumapasok sa mga tangkay ng halaman, na nagiging sanhi ng pagkasira at pagbagsak ng mga ito.")
            }
            item{
                RicePetsSection("Rice Black Bug", R.drawable.ricepetsp3,"Ang Rice Black Bug (Scotinophara coarctata) ay isang peste na kilala sa pagkakaroon ng malubhang " +
                        "epekto sa mga tanim ng palay. Ang mga insekto na ito ay sumisipsip ng sap mula sa mga dahon at butil ng palay, na nagiging sanhi ng pagkasira ng mga tanim at pagbawas ng ani.")
            }
            item{
                RicePetsSection("Green Leaf Hopper", R.drawable.ricepetsp4,"Ang Green Leafhopper (Nephotettix virescens) ay isang peste na karaniwang umaatake sa mga tanim " +
                        "na palay. Ang mga insekto na ito ay sumisipsip ng sap mula sa mga dahon, na nagiging sanhi ng pagkasira at pagbawas ng ani. Bukod pa rito, ang Green Leafhopper ay maaaring magdala ng mga virus " +
                        "na nagiging sanhi ng mga sakit sa palay.")
            }
            item{
                RicePetsSection("Mole Criket", R.drawable.ricepetsp5,"Ang mole cricket ay isang uri ng insekto na karaniwang matatagpuan sa mga taniman, hardin, at golf " +
                        "courses. Sila ay kilala sa kanilang kakayahang magdulot ng pinsala sa mga ugat ng mga halaman, kaya’t mahalaga ang pagkontrol sa kanilang populasyon.")
            }
            item{
                RicePetsSection("Rice Skipper", R.drawable.ricepetsp6,"Ang rice skipper (Antigastra catalaunalis) ay isang uri ng insekto na nagiging sanhi ng pinsala sa mga " +
                        "taniman ng palay. Sila ay kilala sa kanilang kakayahang sirain ang mga dahon ng palay, na nagreresulta sa pagbaba ng ani.")
            }
            item{
                RicePetsSection("White Rice Stem Borer", R.drawable.ricepetsp7,"Ang white rice stem borer (Scirpophaga innotata) ay isang uri ng insekto na nagiging sanhi ng pinsala " +
                        "sa mga taniman ng palay. Sila ay kilala sa kanilang kakayahang sirain ang mga stem ng palay, na nagreresulta sa pagbaba ng ani at kalusugan ng mga pananim.")
            }
        }
    }
}

@Composable
fun RicePetsSection(
    diseaseName: String,
    imageResId: Int,
    description: String
) {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "$diseaseName Image",
            modifier = Modifier
                .height(138.dp)
                .width(250.dp)
                .padding(bottom = 20.dp)
        )
        Text(
            text = diseaseName,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = description,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ){
            Text(
                "Source from Municipal Agriculture Office Brochure",
                fontSize = 13.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                fontStyle = FontStyle.Italic
            )
        }
    }
}