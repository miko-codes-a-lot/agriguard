package com.example.agriguard.modules.main.rice.ui

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agriguard.MainActivity
import com.example.agriguard.R
import com.example.agriguard.modules.main.onion.model.dto.OnionInsuranceDto
import com.example.agriguard.modules.main.onion.ui.OnionInsurancePrintIcon
import com.example.agriguard.modules.main.onion.ui.exportOnionDetails
import com.example.agriguard.modules.main.onion.ui.openFile
import com.example.agriguard.modules.main.rice.model.dto.RiceInsuranceDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RiceInsuranceFormDetails(
    title: String,
    currentUser: UserDto,
    userDto: UserDto,
    riceInsurance: RiceInsuranceDto,
    status: MutableState<String> = rememberSaveable { mutableStateOf("pending") },
    onClickEdit: () -> Unit = {},
    onClickLike: (isLike: Boolean) -> Unit = {},
) {
    val statesValue = remember(riceInsurance) {
        listOf(
            "Status" to (riceInsurance.status ?: "Pending"),
            "Insurance ID" to (riceInsurance.insuranceId ?: ""),
            "Fill Up Date" to dateFormatContainer(riceInsurance.fillUpDate),
            "Lender" to (riceInsurance.lender ?: ""),
            "New" to if (riceInsurance.new) "Yes" else "No",
            "Renewal" to if (riceInsurance.renewal) "Yes" else "No",
            "Self Financed" to if (riceInsurance.selfFinanced) "Yes" else "No",
            "Borrowing" to if (riceInsurance.borrowing) "Yes" else "No",
            "IP Tribe" to (riceInsurance.ipTribe ?: ""),
            "Street" to (riceInsurance.street ?: ""),
            "Barangay" to (riceInsurance.barangay ?: ""),
            "Municipality" to (riceInsurance.municipality ?: ""),
            "Province" to (riceInsurance.province ?: ""),
            "Bank Name" to (riceInsurance.bankName ?: ""),
            "Bank Address" to (riceInsurance.bankAddress ?: ""),
            "Gender (Male)" to if (riceInsurance.male) "Yes" else "No",
            "Gender (Female)" to if (riceInsurance.female) "Yes" else "No",
            "Marital Status" to when {
                riceInsurance.single -> "Single"
                riceInsurance.married -> "Married"
                riceInsurance.widow -> "Widow"
                else -> "Unknown"
            },
            "Beneficiary Name" to (riceInsurance.nameOfBeneficiary ?: ""),
            "Beneficiary Age" to (riceInsurance.age ?: ""),
            "Relationship to Beneficiary" to (riceInsurance.relationship ?: ""),
            "Farm Location" to "${riceInsurance.farmLocationBarangay}, ${riceInsurance.farmLocationMunicipality}, ${riceInsurance.farmLocationProvince}",
            "Farm Coordinates" to "N: ${riceInsurance.north}, S: ${riceInsurance.south}, E: ${riceInsurance.east}, W: ${riceInsurance.west}",
            "Variety" to (riceInsurance.variety ?: ""),
            "Plating Method" to (riceInsurance.platingMethod ?: ""),
            "Date of Sowing" to dateFormatContainer(riceInsurance.dateOfSowing),
            "Date of Planting" to dateFormatContainer(riceInsurance.dateOfPlanting),
            "Date of Harvest" to dateFormatContainer(riceInsurance.dateOfHarvest),
            "Land Category" to (riceInsurance.landOfCategory ?: ""),
            "Soil Types" to (riceInsurance.soilTypes ?: ""),
            "Topography" to (riceInsurance.topography ?: ""),
            "Source of Irrigation" to (riceInsurance.sourceOfIrrigations ?: ""),
            "Tenurial Status" to (riceInsurance.tenurialStatus ?: ""),
            "Insurance Type (Rice)" to if (riceInsurance.rice) "Yes" else "No",
            "Insurance Type (Multi Risk)" to if (riceInsurance.multiRisk) "Yes" else "No",
            "Natural Insurance" to if (riceInsurance.natural) "Yes" else "No",
            "Amount of Coverage" to (riceInsurance.amountOfCover ?: ""),
            "Premium" to (riceInsurance.premium ?: ""),
            "CLTI Adss" to (riceInsurance.cltiAdss ?: ""),
            "Sum Insured" to (riceInsurance.sumInsured ?: ""),
            "Wet Insurance" to if (riceInsurance.wet) "Yes" else "No",
            "Dry Insurance" to if (riceInsurance.dry) "Yes" else "No",
            "CIC Number" to (riceInsurance.cicNo ?: ""),
            "Date of CIC Issue" to dateFormatContainer(riceInsurance.dateCicIssued),
            "COC Number" to (riceInsurance.cocNo ?: ""),
            "Date of COC Issue" to dateFormatContainer(riceInsurance.dateCocIssued),
            "Period of Cover" to (riceInsurance.periodOfCover ?: ""),
        ).associate { (label, value) -> label to mutableStateOf(value) }
    }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        floatingActionButton = {
            if(currentUser.isAdmin){
                RiceInsurancePrintIcon(
                    fetchRiceDetails = riceInsurance,
                    onExportToPDF= { data ->
                        exportRiceDetails(
                            context = context,
                            user = userDto,
                            data = data,
                            onFinish = { file ->
                                openFile(context, file)
                            },
                            onError = { e ->
                                Toast.makeText(
                                    context,
                                    "Error creating PDF: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )

                    },
                    context = context
                )
            } else {
                FloatingRiceInsuranceIcon(
                    onClickEdit = onClickEdit,
                    onClickLike = onClickLike,
                    currentUser = currentUser,
                    status = status
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp),
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

            Spacer(modifier = Modifier.padding(bottom = 80.dp))
        }
    }
}

@Composable
fun FloatingRiceInsuranceIcon(
    onClickEdit: () -> Unit = {},
    onClickLike: (isLike: Boolean) -> Unit = {},
    currentUser: UserDto,
    status: MutableState<String>
) {
    val showEditButton = currentUser.isFarmers && (status.value == "pending" || status.value == "rejected")
    val showLikeButton = currentUser.isTechnician && (status.value == "pending" || status.value == "rejected")
    val showDislikeButton = currentUser.isTechnician && status.value == "pending"

    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End
    ) {

        if (showEditButton) {
            FloatingActionButton(
                onClick = onClickEdit,
                containerColor = Color(0xFF136204),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .size(75.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit"
                )
            }
        }
        if (showLikeButton) {
            FloatingActionButton(
                onClick = { onClickLike(true) },
                containerColor = Color(0xFF136204),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .size(75.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.thumb),
                    contentDescription = "Like"
                )
            }
        }
        if (showDislikeButton) {
            FloatingActionButton(
                onClick = { onClickLike(false) },
                containerColor = Color.Red,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .size(75.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.thumb_down),
                    contentDescription = "Dislike"
                )
            }
        }
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

@Composable
fun RiceInsurancePrintIcon (
    fetchRiceDetails: RiceInsuranceDto,
    onExportToPDF: (RiceInsuranceDto) -> Unit,
    context: Context
) {
    val activity = context as? MainActivity
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P && activity != null) {
                    activity.requestStoragePermission()
                }
                onExportToPDF(fetchRiceDetails)
            },
            containerColor = Color(0xFF136204),
            contentColor = Color(0xFFFFFFFF),
            shape = CircleShape,
            modifier = Modifier
                .size(75.dp)
                .offset(x = (-5).dp, y = (-7).dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.printer),
                contentDescription = "Export",
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }
    }
}