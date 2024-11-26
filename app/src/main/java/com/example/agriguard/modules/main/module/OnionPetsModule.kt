package com.example.agriguard.modules.main.module

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agriguard.R

@Composable
fun OnionPetsModule() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn {
            item {
                OnionPetsSection(
                    "Army Worm",
                    R.drawable.onionpetsp1,
                    "Ang Army Worm (Spodoptera spp.) ay isang peste na kilalang nagiging sanhi ng malaking pinsala sa " +
                            "mga tanim, kabilang ang palay, mais, at iba pang cereal crops. Ang mga larva ng army worm ay kumakain ng mga dahon, na nagiging sanhi ng mabilis na pagkasira ng mga tanim. Maaaring gamitin " +
                            "ang brofeya na gamot upang ma puksa ito."
                )
                OnionPetsSection(
                    "Cut Worm",
                    R.drawable.onionpetsp2,
                    "Ang Cutworm ay isang uri ng peste na maaaring magdulot ng malubhang pinsala sa mga tanim, kabilang ang sibuyas. Ang mga cutworm ay madalas na umaatake sa mga batang halaman, pinaputol " +
                            "ang mga stem sa antas ng lupa, at nagiging sanhi ng pagkamatay ng mga ito."
                )
                OnionPetsSection(
                    "Onion Thrips",
                    R.drawable.onionpetsp3,
                    "Ang mga thrips ay maliliit na insekto na karaniwang nagiging problema sa mga pananim tulad ng sibuyas. Nakakapinsala sila sa pamamagitan ng pagkuha ng mga katas ng halaman, na nagiging sanhi " +
                            "ng pagkulubot o pagkasira ng mga dahon at pagbabawas ng ani."
                )
                OnionPetsSection(
                    "Leaf Mine\n(fly onion)",
                    R.drawable.onionpetsp4,
                    "Ang leaf miner ay isang uri ng peste na nagdudulot ng pinsala sa pamamagitan ng pagbuo ng maliliit na tunnels o mina sa mga dahon ng mga halaman."
                )
            }
        }
    }
}

@Composable
fun OnionPetsSection(
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
    }
}