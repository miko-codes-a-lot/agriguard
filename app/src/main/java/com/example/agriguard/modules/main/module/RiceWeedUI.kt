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
fun RiceWeedUI(){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn {
            item{
                RiceWeeds("Echinochloa Crus-Galli\n         (Bayokibok)",
                    R.drawable.ricew1,
                    "Ang Echinochloa crus-galli, na kilala rin bilang bayokibok o barnyard grass, ay isang invasive na damo na madalas na nagiging sanhi ng problema sa mga taniman, lalo na sa mga palayan."
                )
            }
            item{
                RiceWeeds("   Eclipta Alba\n (Hígis-Manok)",
                    R.drawable.ricew2,
                    "Ang Eclipta alba, na kilala rin bilang false daisy o hairy eclipta, ay isang uri ng damo na karaniwang matatagpuan sa mga taniman at maaari ding maging invasive. Ang mga ito ay may mga medicinal " +
                            "properties ngunit nagiging sanhi rin ng kumpetisyon sa mga pananim."
                )
            }
            item{
                RiceWeeds("Cyperus Difformis",
                    R.drawable.ricew3,
                    "Ang Cyperus difformis, na kilala rin bilang dwarf umbrella sedge o sedge, ay isang uri ng damo na madalas na nagiging problema sa mga taniman, lalo na sa mga palayan. Ang mga sedge na ito ay " +
                            "maaaring maging invasive at nakikipagkumpitensya sa mga pananim para sa nutrients at tubig."
                )
            }
            item{
                RiceWeeds("  White Water Fire  \n (Bergia Capensis)",
                    R.drawable.ricew4,
                    "Ang Bergia capensis, na kilala rin bilang Cape Bergia, ay isang uri ng damo na karaniwang matatagpuan " +
                            "sa mga wetland at mga lugar na may mataas na moisture. Ang mga damong ito ay maaaring makabuo ng mga isyu sa mga taniman."
                )
            }
        }
    }
}

@Composable
fun RiceWeeds(
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
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Justify

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