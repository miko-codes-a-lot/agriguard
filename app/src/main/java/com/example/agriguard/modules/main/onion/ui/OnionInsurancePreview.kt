package com.example.agriguard.modules.main.onion.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agriguard.modules.main.onion.model.dto.OnionInsuranceDto
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun OnionInsurancePreview(
    title: String,
    onionInsurance: OnionInsuranceDto,
) {
    val statesValue = remember(onionInsurance) {
        listOf(
            "Fill Up Date" to onionInsurance.fillUpDate,
            "IP Tribe" to (onionInsurance.ipTribe ?: ""),
            "Male" to (if (onionInsurance.male) "Yes" else "No"),
            "Female" to (if (onionInsurance.female) "Yes" else "No"),
            "Single" to (if (onionInsurance.single) "Yes" else "No"),
            "Married" to (if (onionInsurance.married) "Yes" else "No"),
            "Widow" to (if (onionInsurance.widow) "Yes" else "No"),
            "Beneficiary Name" to (onionInsurance.nameOfBeneficiary ?: ""),
            "Beneficiary Age" to (onionInsurance.ageOfBeneficiary ?: ""),
            "Beneficiary Relationship" to (onionInsurance.relationshipOfBeneficiary ?: ""),
            "Farm Location" to (onionInsurance.farmLocation ?: ""),
            "Area" to (onionInsurance.area ?: ""),
            "Soil Type" to (onionInsurance.soilType ?: ""),
            "Soil pH" to (onionInsurance.soilPh ?: ""),
            "Topography" to (onionInsurance.topography ?: ""),
            "Variety" to (onionInsurance.variety ?: ""),
            "Date of Planting" to onionInsurance.dateOfPlanting,
            "Estimated Harvest Date" to onionInsurance.estdDateOfHarvest,
            "Age Group" to (onionInsurance.ageGroup ?: ""),
            "Number of Hills" to (onionInsurance.noOfHills ?: ""),
            "Irrigation Type" to (onionInsurance.typeOfIrrigation ?: ""),
            "Average Yield" to (onionInsurance.averageYield ?: ""),
            "Land Preparation" to (onionInsurance.landPreparation ?: ""),
            "Materials Item" to (onionInsurance.materialsItem ?: ""),
            "Materials Quantity" to (onionInsurance.materialsQuantity ?: ""),
            "Materials Cost" to (onionInsurance.materialsCost ?: ""),
            "Labor Work Force" to (onionInsurance.laborWorkForce ?: ""),
            "Labor Quantity" to (onionInsurance.laborQuantity ?: ""),
            "Labor Cost" to (onionInsurance.laborCost ?: ""),
            "Total Cost" to (onionInsurance.totalCoast ?: ""),
            "Farm Location Sitio" to (onionInsurance.farmLocationSitio ?: ""),
            "Farm Location Barangay" to (onionInsurance.farmLocatioBarangay ?: ""),
            "Farm Location Municipality" to (onionInsurance.farmLocationMunicipality ?: ""),
            "Farm Location Province" to (onionInsurance.farmLocationProvince ?: ""),
            "North" to (onionInsurance.north ?: ""),
            "South" to (onionInsurance.south ?: ""),
            "East" to (onionInsurance.east ?: ""),
            "West" to (onionInsurance.west ?: ""),
            "Status" to (onionInsurance.status ?: "pending"),
            "Created At" to dateFormatContainer(onionInsurance.createdAt ?: ""),
            "Last Updated At" to dateFormatContainer(onionInsurance.lastUpdatedAt ?: "")
        ).associate { (label, value) -> label to mutableStateOf(value) }
    }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 50.dp))
        Text(text = title,
            fontFamily = FontFamily.SansSerif,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 3.dp, top = 7.dp)
        )
        ViewData(statesValue)
        Spacer(modifier = Modifier.padding(bottom = 50.dp))
    }
}

@Composable
fun ViewData(stateValues: Map<String, MutableState<String>>) {
    Column {
        stateValues.forEach { (label, states) ->
            TextContainer(textLabel = label, textValue = states.value)
        }
    }
}

@Composable
fun TextContainer(textLabel: String, textValue: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = textLabel,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(" : ",fontWeight = FontWeight.Bold, color = Color.Black, fontFamily = FontFamily.SansSerif)
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = textValue,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp),
                        fontSize = 17.sp,
                        fontFamily = FontFamily.SansSerif,
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

fun dateFormatContainer(dateString: String): String {
    return try {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dates = isoFormat.parse(dateString)
        displayFormat.format(dates)
    } catch (e: Exception) {
        "Select Date"
    }
}