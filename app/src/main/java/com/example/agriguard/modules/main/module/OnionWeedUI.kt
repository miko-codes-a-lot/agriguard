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
fun OnionWeedUI() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn{
            item{
                OnionWeeds("Nut Grass / Cyperus Rotundus\n                    (Mutha) ",
                    R.drawable.onionw1,
                    "Ang nut grass o Cyperus rotundus, na kilala rin bilang mutha o sedges, ay isang invasive weed na mahirap kontrolin dahil sa matibay nitong ugat (rhizomes at tubers). Makakasira ito sa mga tanim sa pamamagitan ng pag-agaw ng sustansya at tubig mula sa mga halaman."
                )
            }
            item{
                OnionWeeds("    Phyllanthus Amarus\n  (Sampa - Sampalukan) ",
                    R.drawable.onionw2,
                    "Ang Phyllanthus Amarus, o kilala rin sa tawag na Sampa-sampalukan, ay isang uri ng halamang-damo na karaniwang matatagpuan sa mga bukid, hardin, at gilid ng kalsada. Bagama’t kilala ito bilang isang " +
                            "medicinal plant at ginagamit sa tradisyunal na panggagamot (halimbawa, para sa mga sakit sa atay at bato), maaaring ituring itong damo o pest kung tumutubo ito sa mga taniman at pinapalitan ang mga pananim."
                )
            }
            item{
                OnionWeeds("         Desert Horsepurslane\n    (Trianthema portulacastrum) ",
                    R.drawable.onionw3,
                    "Ang Desert Horsepurslane (Trianthema portulacastrum) ay isang mabilis na tumubong damo na makikita sa mga taniman ng mais, palay, gulay, at iba pang pananim. Karaniwan itong problema dahil " +
                            "inaagawan nito ang mga tanim ng sustansya, tubig, at liwanag ng araw, na maaaring magresulta sa pagbaba ng ani."
                )
            }
            item{
                OnionWeeds("Echinochloa colona\n      (pulang puit) ",
                    R.drawable.onionw4,
                    "Ang Echinochloa colona, o kilala sa lokal na tawag na pulang puit, ay isang uri ng damo na madalas " +
                            "makikita sa mga taniman ng palay, mais, at iba pang pananim. Katulad ng ibang mga damo, ito ay nag-aagaw ng sustansya, tubig, at liwanag mula sa mga tanim, na maaaring magresulta sa pagbaba ng ani."
                )
            }
            item{
                OnionWeeds("Indian goosegrass\n        (Paragis) ",
                    R.drawable.onionw5,
                    "Ang Indian goosegrass o Paragis (Eleusine indica) ay isang uri ng damo na karaniwang nakikita sa mga taniman, golf courses, at mga bakuran. Ang damong ito ay maaaring maging invasive at nagiging sanhi ng " +
                            "kompetisyon sa mga pananim para sa sustansya, tubig, at liwanag."
                )
            }
            item{
                OnionWeeds("   Mimosa pudica\n (Makahiya Grass) ",
                    R.drawable.onionw6,
                    "Ang makahiya (Mimosa pudica) ay isang uri ng damo na kilala sa kanyang kakayahang magtago o yumuko kapag hinawakan. Bagama't ito ay may mga medicinal properties at maaaring magbigay ng " +
                            "ilang benepisyo, maaari rin itong maging invasive at hindi kanais-nais sa ilang mga taniman."
                )
            }
        }
    }
}

@Composable
fun OnionWeeds(
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