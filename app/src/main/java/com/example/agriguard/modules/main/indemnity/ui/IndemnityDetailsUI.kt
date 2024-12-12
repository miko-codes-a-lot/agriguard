package com.example.agriguard.modules.main.indemnity.ui

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
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun IndemnityDetailsUI(
    title: String,
    currentUser: UserDto,
    indemnity: IndemnityDto,
    onClickEdit: () -> Unit = {},
    onClickLike: (isLike: Boolean) -> Unit = {},
) {
    val status = rememberSaveable { mutableStateOf(indemnity.status) }

    val statesValue = remember(indemnity) {
        listOf(
            "User ID" to indemnity.userId,
            "Fill Up Date" to dateFormatContainer(indemnity.fillupdate.toString()),
            "Regular" to if (indemnity.regular) "Yes" else "No",
            "Punla" to if (indemnity.punla) "Yes" else "No",
            "Cooperative Rice" to if (indemnity.cooperativeRice) "Yes" else "No",
            "RSBSA" to if (indemnity.rsbsa) "Yes" else "No",
            "Sikat" to if (indemnity.sikat) "Yes" else "No",
            "APCPC" to if (indemnity.apcpc) "Yes" else "No",
            "Others" to (indemnity.others ?: ""),
            "Cause of Damage" to (indemnity.causeOfDamage ?: ""),
            "Date of Loss" to dateFormatContainer(indemnity.dateOfLoss.toString()),
            "Age of Cultivation" to (indemnity.ageCultivation ?: ""),
            "Area Damaged" to (indemnity.areaDamaged ?: ""),
            "Degree of Damage" to (indemnity.degreeOfDamage ?: ""),
            "Expected Date of Harvest" to dateFormatContainer(indemnity.expectedDateOfHarvest.toString()),
            "North" to (indemnity.north ?: ""),
            "South" to (indemnity.south ?: ""),
            "East" to (indemnity.east ?: ""),
            "West" to (indemnity.west ?: ""),
            "Upa Sa Gawa Bilang" to (indemnity.upaSaGawaBilang ?: ""),
            "Upa Sa Gawa Halaga" to (indemnity.upaSaGawaHalaga ?: ""),
            "Binhi Bilang" to (indemnity.binhiBilang ?: ""),
            "Binhi Halaga" to (indemnity.binhiHalaga ?: ""),
            "Abono Bilang" to (indemnity.abonoBilang ?: ""),
            "Abono Halaga" to (indemnity.abonoHalaga ?: ""),
            "Kemikal Bilang" to (indemnity.kemikalBilang ?: ""),
            "Kemikal Halaga" to (indemnity.kemikalHalaga ?: ""),
            "Patubig Bilang" to (indemnity.patubigBilang ?: ""),
            "Patubig Halaga" to (indemnity.patubigHalaga ?: ""),
            "Ibapa Bilang" to (indemnity.ibapaBilang ?: ""),
            "Ibapa Halaga" to (indemnity.ibapaHalaga ?: ""),
            "Kabuuan Bilang" to (indemnity.kabuuanBilang ?: ""),
            "Kabuuan Halaga" to (indemnity.kabuuanHalaga ?: ""),
            "Status" to (indemnity.status ?: "Pending"),
        ).associate { (label, value) -> label to mutableStateOf(value) }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 50.dp))
        Text(
            text = title,
            fontFamily = FontFamily.SansSerif,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 3.dp, top = 7.dp)
        )

        if (currentUser.isFarmers && (status.value == "pending" || status.value == "rejected")) {
            Button(
                onClick = {
                    onClickEdit()
                }
            ) {
                Text("Edit")
            }
        } else if (currentUser.isTechnician && (status.value == "pending" || status.value == "rejected")) {
            Button(
                onClick = {
                    status.value = "approved"
                    onClickLike(true)
                }
            ) {
                Text("Like")
            }
            if (status.value == "pending") {
                Button(
                    onClick = {
                        status.value = "rejected"
                        onClickLike(false)
                    }
                ) {
                    Text("Dislike")
                }
            }
        }

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
            Text(" : ", fontWeight = FontWeight.Bold, color = Color.Black, fontFamily = FontFamily.SansSerif)
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
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