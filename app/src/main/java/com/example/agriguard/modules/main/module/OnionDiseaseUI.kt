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
import com.example.agriguard.R

@Preview(showBackground = true)
@Composable
fun OnionModulePreview() {
    OnionDiseaseUI()
}

@Composable
fun OnionDiseaseUI() {
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
                OnionDiseaseSection("Purple Blotch", R.drawable.onionp1,"Ang Purple Blotch ay isang fungal disease na dulot ng Alternaria porri, at karaniwang umaatake sa mga "+
                        "tanim na sibuyas (onion) at bawang (garlic). Ang sakit na ito ay nagdudulot ng mga bilog o pahabang lesions na kulay lila na may dilaw na gilid sa mga dahon at bulaklak. Kung hindi maagapan, maaaring "+
                        "magdulot ito ng malubhang pinsala at mababang ani.","- Treatment: Agapan Agad: Kapag napansin ang mga unang sintomas ng sakit, agad mag-spray ng fungicide upang maiwasan ang pagkalat. Sundin ang dosage na nakasaad sa label ng produkto.\n\n"+
                        "- Regular na Pag-spray: Sa mga lugar na karaniwan ang Purple Blotch, maaaring kailanganin ang regular na pag-spray ng fungicide lalo na kapag ang kondisyon ng panahon ay basa o mahalumigmig, dahil pabor ito sa pagdami ng fungus.\n\n"+
                        "- Tamang Patubig: Iwasan ang patubig mula sa itaas na nagiging sanhi ng pagkabasa ng mga dahon, dahil nakakatulong ito sa pagkalat ng fungal spores.\n\n"+
                        "- Sanitation ng Sakahan: Siguraduhing malinis ang paligid ng taniman, alisin ang mga patay o nahawaang bahagi ng halaman at sunugin ang mga ito upang hindi magpatuloy ang impeksyon."
                )
                OnionDiseaseSection("Stemphylium Blight", R.drawable.onionp2,"Ang Stemphylium Blight ay isang fungal disease na dulot ng Stemphylium vesicarium, na karaniwang " +
                        "nakakaapekto sa mga tanim na sibuyas (onions) at bawang (garlic). Ang sakit na ito ay nagdudulot ng pagkasira ng mga dahon at maaaring makaapekto sa ani kung hindi agad maagapan.",
                        "- Treatment: Ang Stemphylium Blight ay maaaring makontrol gamit ang fungicidesk Timing ng Application: Kapag nakita na ang mga unang sintomas ng sakit tulad ng mga brown o dilaw na spots sa mga dahon, agad na mag-spray ng fungicide. Mag-spray ng fungicide sa tamang timing at sundin ang dosage na nakasaad sa produkto. \n\n- Pagbalanse ng Abono: Gumamit ng tamang dami ng nitrogen, phosphorus, at potassium. \n" +
                        "Iwasan ang sobrang nitrogen na maaaring magdulot ng sobrang paglago ng mga dahon, na paborable sa fungal infection."
                )
                OnionDiseaseSection("Downy Mildew", R.drawable.onionp3, "Ang Downy Mildew ay isang fungal disease na dulot ng mga fungi mula sa genus na Peronospora at " +
                        "karaniwang umaatake sa mga tanim tulad ng sibuyas, bawang, at iba pang mga leafy vegetables. Ang sakit na ito ay nagiging sanhi ng yellowing ng mga dahon at puting o grayish na mycelial growth sa ilalim "+
                        "ng mga dahon, na nagiging sanhi ng pagkasira ng ani.","- Treatment: Timing ng Application: Mag-spray ng fungicide kapag nakita ang mga unang sintomas, tulad ng " +
                        "yellowing ng mga dahon. Maaaring kailanganin ang paulit-ulit na aplikasyon ayon sa rekomendasyon ng label ng produkto.\n\n- Pag-aalis ng Apektadong Halaman: Agad na alisin at itapon ang mga nahawaang dahon upang maiwasan ang pagkalat ng sakit.\n\n" +
                        "- Proper Field Drainage: Siguraduhing may magandang drainage ang sakahan upang maiwasan ang pagkababad ng tubig, na nagiging sanhi ng mataas na kahalumigmigan na pabor sa fungal diseases."
                )
                OnionDiseaseSection("Onion Smut",R.drawable.onionp4,"Ang Onion Smut ay isang fungal disease na dulot ng Urocystis cepulae na karaniwang umaatake sa mga " +
                        "tanim na sibuyas. Ang sakit na ito ay nagdudulot ng mga puting lumot o itim na spore masses sa loob ng mga bulb at dahon, na nagiging sanhi ng pagkasira ng ani at pagbawas sa kalidad ng mga sibuyas.",
           "- Treatment: Mag-spray ng fungicide sa mga buto bago itanim (seed treatment) at sa mga unang senyales ng sakit. Sundin ang dosage at timing na nakasaad sa label ng produkto.\n\n - Agad na alisin at itapon ang mga halaman upang maiwasan ang pagkalat ng sakit.\n\n"+
                    "- Ang Onion Smut ay maaaring makontrol sa pamamagitan ng tamang paggamit ng fungicides tulad ng Thiram, Fuberidazole, at Carbendazim. Ang pagpili ng resistant varieties, tamang pamamahala ng lupa, at cultural practices ay mahalaga upang mapanatili ang kalusugan ng mga tanim at maiwasan ang " +
                   "pagkakaroon ng sakit. Regular na pag-monitor at agaran na aksyon ay makakatulong upang mabawasan ang pinsala sa ani."
                )
                OnionDiseaseSection("Bacterial Brown Rot",R.drawable.onionp5,"Ang Bacterial Brown Rot ng sibuyas ay isang sakit na dulot ng bakterya na Pseudomonas syringae. Ang " +
                        "sakit na ito ay nagiging sanhi ng pagkakaroon ng mga brown, water-soaked lesions sa mga dahon at bulb ng sibuyas, na nagreresulta sa pagkasira at pagkabulok ng ani.","- Treatment: Copper-based bactericides: Tulad ng Copper hydroxide o Copper oxychloride, epektibo ito laban sa mga bacterial diseases."+
                        "- Streptomycin: Maaaring gamitin bilang foliar spray upang makatulong na pigilan ang pagkalat ng bakterya.\n\n- Pag-alis ng Apektadong Halaman: Agad na alisin at itapon ang mga apektadong bahagi ng halaman upang maiwasan ang pagkalat ng sakit.\n\n- Gamit ng Certified Seeds: Siguraduhing malinis at walang sakit ang mga binhi na itatanim.\n\n"+
                        "- Regular na Pag-monitor: Suriin ang mga tanim nang madalas para sa mga unang senyales ng bacterial brown rot. Kapag nakita ang mga sintomas, agad na mag-spray ng bactericide."
                )
                OnionDiseaseSection("Anthracnose\n    (Twister)",R.drawable.onionp6,"Ang anthracnose ay isang fungal disease na dulot ng iba't ibang species ng Colletotrichum, at " +
                        "karaniwang umaatake sa mga halaman tulad ng mangga, sili, at iba pa. Nagpapakita ito ng mga sintomas tulad ng maliliit na itim na spots sa mga dahon, bunga, o tangkay, na maaaring lumaki at "+
                        "magdikit-dikit, sanhi ng pagkabulok ng apektadong bahagi.",""
                )
                OnionDiseaseSection("Pink Root",R.drawable.onionp7,"Ang pink root ay isang sakit na dulot ng soil-borne fungus na Phoma terrestris, na karaniwang " +
                        "nakakaapekto sa mga sibuyas (onion) at bawang. Ang mga apektadong ugat ay nagiging kulay rosas, pagkatapos ay nagiging kayumanggi at itim, na nagdudulot ng pagbagal ng paglaki at pagkamatay ng halaman.",""
                )
            }
        }

    }
}

@Composable
fun OnionDiseaseSection(
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