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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R

@Preview(showBackground = true)
@Composable
fun RiceModuleUIPreview() {
    RiceDiseaseUI(rememberNavController())
}

@Composable
fun RiceDiseaseUI(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn(
        ) {
            item{
                RiceDiseaseSection(
                    "Rice Blast\n\n(Mata-mata)", R.drawable.ricep1,"Ang rice blast ay isang fungal disease na dulot ng Magnaporthe oryzae na maaaring makaapekto sa iba't "+
                    "ibang bahagi ng palay tulad ng dahon, tangkay, at butil. Ito ay isa sa mga pinakamalubhang sakit ng palay. " +
                    "Narito ang mga paggamot at pamamaraan upang maiwasan o mabawasan ang epekto ng rice blast", "- Treatment : Paggamit ng Resistant na Barayti: Ang paggamit ng mga barayti ng palay na may resistensya sa " +
                    "rice blast ay isang epektibong paraan upang maiwasan ang pagkalat ng sakit. Magtanong sa mga lokal na agricultural office para sa mga rekomendadong barayti na angkop sa iyong lugar."
                )
            }
            item{
                RiceDiseaseSection(
        "Bacterial Leaf Blight", R.drawable.ricep2,"Ang Bacterial Leaf Blight (BLB) ay isang malubhang sakit ng palay. Karaniwang lumalabas ang sakit na ito " +
                    "sa mga palayan na may mataas na kahalumigmigan, lalo na sa mga lugar na may mainit at basang klima. " +
                    "Narito ang mga pangunahing sanhi ng Bacterial Leaf Blight.",""
                )
            }
            item{
                RiceDiseaseSection(
                    "Sheath Blight", R.drawable.ricep3,"Ang Sheath Blight ay isang sakit ng palay na dulot ng Rhizoctonia solani, isang fungus na tumutubo sa " +
                    "mga tangkay ng palay, partikular sa mga lower leaf sheaths (mga bahagi ng dahon na nakapulupot sa " +
                    "tangkay). Ito ay maaaring magdulot ng malaking pinsala sa ani, lalo na kung hindi ito agad maagapan. " +
                    "Narito ang mga paggamot at pamamaraan upang makontrol ang Sheath Blight."," - Treatment: Ang tamang pagsasagawa ng fungicide application, paggamit ng resistant varieties, tamang " +
                    "pag-aabono, at pagpapanatili ng malinis na sakahan ay makakatulong upang makontrol ang Sheath " +
                    "Blight. Ang regular na pag-monitor at agaran na aksyon ay mahalaga upang mabawasan ang pinsala ng " +
                    "sakit sa iyong ani."
                )
            }
            item{
                RiceDiseaseSection(
                    "Dirty Panicle", R.drawable.ricep4,"Ang Dirty Panicle ay isang sakit na dulot ng Fusarium, isang uri ng fungus na karaniwang umaatake sa " +
                            "mga panicle ng mga tanim tulad ng palay (rice). Ang sakit na ito ay nagiging sanhi ng pagkakaroon ng " +
                            "mga brown at water-soaked lesions sa mga spikelets, na nagreresulta sa pagkabulok at pag-ani ng mga " +
                            "apektadong bahagi ng tanim."," - Treatment: Mag-spray ng fungicide sa mga unang senyales ng sakit o bago ang pamumukadkad ng mga bulaklak. " +
                            "Sundin ang dosage at rekomendasyon na nakasaad sa label ng produkto."
                )
            }
            item{
                RiceDiseaseSection(
                    "Tungro",R.drawable.ricep5,"Ang tungro ay isang viral disease na nakakaapekto sa mga palay (rice), na dulot ng rice tungro " +
                            "bacilliform virus (RTBV) at rice tungro spherical virus (RTSV). Ang sakit na ito ay ipinapasa ng green leafhopper (Nephotettix virescens), isang uri ng insekto na kumakain ng palay at nagdadala ng virus."," - Treatment: Iwasang magtanim ng magkakaibang yugto ng palay sa isang lugar. Sa pamamagitan ng sabay-sabay na pagtatanim, mababawasan ang presensya ng green leafhopper at virus sa taniman. Alisin at itapon ang " +
                            "mga damo at mga boluntaryong halaman (volunteer plants) na posibleng host ng mga leafhoppers at ng virus. Siguraduhin ding malinis ang paligid ng mga bukirin upang hindi maging taguan ng insekto.\n\n" +
                            "- Siguraduhing gamitin ang insecticides na ito sa maagang yugto ng pag-atake ng leafhopper at sundin ang tamang dami at oras ng aplikasyon upang maiwasan ang pagbuo ng resistensya sa mga insekto. Alisin at " +
                            "itapon ang mga halaman na may sintomas ng tungro upang maiwasan ang pagkalat ng sakit sa iba pang bahagi ng taniman. Magkaroon ng regular na inspeksyon sa mga taniman upang maagang makita ang " +
                            "mga sintomas ng tungro. Agad na gumawa ng aksyon kapag may nakitang palatandaan ng sakit."
                )
            }
            item{
                RiceDiseaseSection(
                    "Brown Spot", R.drawable.ricep6,"Ang brown spot ay isang fungal disease na dulot ng Bipolaris oryzae (dating tinawag na Cochliobolus " +
                            "miyabeanus o Helminthosporium oryzae), na karaniwang nakakaapekto sa palay (Oryza sativa). " +
                            "Nagdudulot ito ng mga maliliit na brown spots sa mga dahon, tangkay, at butil ng palay, na maaaring " +
                            "magresulta sa pagkakaroon ng mababang ani at kalidad ng bigas."," - Treatment: Siguraduhing sapat ang nutrisyon ng lupa, lalo na ang nitrogen at potassium, dahil ang kakulangan ng " +
                            "mga ito ay nagpapataas ng susceptibility ng palay sa brown spot. Iwasan ang pagtatanim ng palay sa parehong lugar taon-taon. Gumamit ng crop rotation kasama ang ibang hindi host plants upang " +
                            "mabawasan ang build-up ng fungal pathogens sa lupa. Siguraduhin ang tamang antas ng tubig at drainage sa bukid upang maiwasan ang stress sa mga halaman, na maaaring maging sanhi ng pagkalat ng " +
                            "sakit. Huwag hayaang mababad sa tubig ang mga palay nang masyadong matagal. Gumamit ng malusog at certified disease-free seeds upang maiwasan ang pagpasok ng fungus sa simula pa lang. Regular na " +
                            "obserbahan ang mga palay para sa mga unang sintomas ng brown spot. Agad na magsagawa ng mga naaangkop na hakbang upang maiwasan ang pagkalat ng sakit. Gamitin ang kombinasyon ng cultural, " +
                            "biological, at chemical control methods upang mas epektibong kontrolin ang brown spot."
                )
            }
//            item{
//                RiceModuleItem()
//            }
        }
    }
}

@Composable
fun RiceDiseaseSection(
    diseaseName: String,
    imageResId: Int,
    description: String,
    descriptionNext: String
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
        Text(
            text = descriptionNext,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}