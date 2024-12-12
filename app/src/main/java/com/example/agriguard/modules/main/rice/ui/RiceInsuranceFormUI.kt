package com.example.agriguard.modules.main.rice.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agriguard.modules.main.onion.ui.CheckStatusField
import com.example.agriguard.modules.main.onion.ui.CropsInfo
import com.example.agriguard.modules.main.onion.ui.OnionDatePicker
import com.example.agriguard.modules.main.onion.ui.TextFieldOnionStatus
import com.example.agriguard.modules.main.rice.model.dto.RiceInsuranceDto
import com.example.agriguard.modules.main.rice.viewmodel.RiceInsuranceViewModel
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.shared.ui.CheckBoxField
import com.example.agriguard.modules.shared.ui.DatePickerField
import com.example.agriguard.modules.shared.ui.TextField
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Composable
fun RiceInsuranceFormUI(
    navController: NavController,
    currentUser: UserDto,
    viewModel: RiceInsuranceViewModel,
//    riceInsuranceDto: RiceInsuranceDto?,
    onSubmit: (RiceInsuranceDto) -> Unit
) {
    val formState by viewModel.formState.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

//    LaunchedEffect(riceInsuranceDto) {
//        if (riceInsuranceDto != null) {
//            viewModel.updateField { riceInsuranceDto }
//        } else {
//            viewModel.resetFormState()
//        }
//    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 140.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                TextFieldRiceStatus(
                    context = context,
                    label = "Insurance ID",
                    value = formState.insuranceId
                ) { value -> viewModel.updateField { it.copy(insuranceId = value) } }
                TextFieldRiceStatus(
                    context = context,
                    "Lender",
                    formState.lender
                ) { value -> viewModel.updateField { it.copy(lender = value) } }
                RiceDatePicker(
                    context = context,
                    "Date",
                    value = formState.fillUpDate,
                    onChange = { value -> viewModel.updateField { it.copy(fillUpDate = value) } }
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RiceCheckBoxField(
                    "New",
                    formState.new
                ) { value -> viewModel.updateField {
                    it.copy(
                        new = value,
                        renewal = !value,
                        selfFinanced = !value,
                        borrowing = !value
                        )
                    }
                }
                RiceCheckBoxField(
                    "Renewal",
                    formState.renewal
                ) { value -> viewModel.updateField { it.copy(
                    renewal = value,
                    new = !value,
                    selfFinanced = !value,
                    borrowing = !value
                    ) }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RiceCheckBoxField(
                    "Self-Financed",
                    formState.selfFinanced
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            selfFinanced = value,
                            new = !value,
                            renewal = !value,
                            borrowing = !value
                        )
                    }
                }
                RiceCheckBoxField(
                    "Borrowing",
                    formState.borrowing
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            borrowing = value,
                            new = !value,
                            renewal = !value,
                            selfFinanced = !value,
                        )
                    }
                }
            }
        }
        TextFieldRiceStatus(
            context = context,
            label = "IP Tribe",
            value = formState.ipTribe,
            onChange = { value -> viewModel.updateField { it.copy(ipTribe = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Street",
            value = formState.street,
            onChange = { value -> viewModel.updateField { it.copy(street = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Barangay",
            value = formState.barangay,
            onChange = { value -> viewModel.updateField { it.copy(barangay = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Municipality",
            value = formState.municipality,
            onChange = { value -> viewModel.updateField { it.copy(municipality = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Province",
            value = formState.province,
            onChange = { value -> viewModel.updateField { it.copy(province = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Bank Name",
            value = formState.bankName,
            onChange = { value -> viewModel.updateField { it.copy(bankName = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Bank Address",
            value = formState.bankAddress,
            onChange = { value -> viewModel.updateField { it.copy(bankAddress = value) } }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RiceStatusField(
                    "Male",
                    formState.male
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            male = value,
                            female = !value
                        )
                    }
                }
                RiceStatusField(
                    "Female",
                    formState.female
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            female = value,
                            male = !value
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RiceStatusField(
                    "Single",
                    formState.single
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            single = value,
                            married = !value && it.married,
                            widow = !value && it.widow
                        )
                    }
                }
                RiceStatusField(
                    "Married",
                    formState.married
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            married = value,
                            single = !value && it.single,
                            widow = !value && it.widow
                        )
                    }
                }
                RiceStatusField(
                    "Widow",
                    formState.widow
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            widow = value,
                            single = !value && it.single,
                            married = !value && it.married
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Legal Beneficiaries",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextFieldRiceStatus(
            context = context,
            label = "Name of Beneficiary",
            value = formState.nameOfBeneficiary,
            onChange = { value -> viewModel.updateField { it.copy(nameOfBeneficiary = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Age",
            value = formState.age,
            onChange = { value -> viewModel.updateField { it.copy(age = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Relationship",
            value = formState.relationship,
            onChange = { value -> viewModel.updateField { it.copy(relationship = value) } }
        )
        Row(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "B.1 Farm Location/LSP",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextFieldRiceStatus(
            context = context,
            label = "Sitio",
            value = formState.sitio,
            onChange = { value -> viewModel.updateField { it.copy(sitio = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Farm Location Barangay",
            value = formState.farmLocationBarangay,
            onChange = { value -> viewModel.updateField { it.copy(farmLocationBarangay = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Farm Location Municipality",
            value = formState.farmLocationMunicipality,
            onChange = { value -> viewModel.updateField { it.copy(farmLocationMunicipality = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Farm Location Province",
            value = formState.farmLocationProvince,
            onChange = { value -> viewModel.updateField { it.copy(farmLocationProvince = value) } }
        )
        Row(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "B.2 Boundaries",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextFieldRiceStatus(
            context = context,
            label = "North",
            value = formState.north,
            onChange = { value -> viewModel.updateField { it.copy(north = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "South",
            value = formState.south,
            onChange = { value -> viewModel.updateField { it.copy(south = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "East",
            value = formState.south,
            onChange = { value -> viewModel.updateField { it.copy(east = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "West",
            value = formState.west,
            onChange = { value -> viewModel.updateField { it.copy(west = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "B.3 Variety",
            value = formState.variety,
            onChange = { value -> viewModel.updateField { it.copy(variety = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "B.4 Plating Method",
            value = formState.platingMethod,
            onChange = { value -> viewModel.updateField { it.copy(platingMethod = value) } }
        )
        RiceDatePicker(
            context = context,
            "B.5 Date of Sowing",
            value = formState.dateOfSowing,
            onChange = { value -> viewModel.updateField { it.copy(dateOfSowing = value) } }
        )
        RiceDatePicker(
            context = context,
            "B.6 Date of Planting",
            value = formState.dateOfPlanting,
            onChange = { value -> viewModel.updateField { it.copy(dateOfPlanting = value) } }
        )
        RiceDatePicker(
            context = context,
            "B.7 Date of Harvest",
            value = formState.dateOfHarvest,
            onChange = { value -> viewModel.updateField { it.copy(dateOfHarvest = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "B.8 Land of Category",
            value = formState.landOfCategory,
            onChange = { value -> viewModel.updateField { it.copy(landOfCategory = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "B.9 Soil Types",
            value = formState.soilTypes,
            onChange = { value -> viewModel.updateField { it.copy(soilTypes = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "B.10 Topography",
            value = formState.topography,
            onChange = { value -> viewModel.updateField { it.copy(topography = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "B.11 Source of Irrigations",
            value = formState.sourceOfIrrigations,
            onChange = { value -> viewModel.updateField { it.copy(sourceOfIrrigations = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "B.12 Tenurial Status",
            value = formState.tenurialStatus,
            onChange = { value -> viewModel.updateField { it.copy(tenurialStatus = value) } }
        )
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "The Coverage",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RiceCheckBoxCoverage(
                    "Rice",
                    formState.rice
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            rice = value,
                        )
                    }
                }
                RiceCheckBoxCoverage(
                    "Multi-risk",
                    formState.multiRisk
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            multiRisk = value,
                        )
                    }
                }
                RiceCheckBoxCoverage(
                    "Natural",
                    formState.natural
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            natural = value,
                        )
                    }
                }
            }
        }
        TextFieldRiceStatus(
            context = context,
            label = "Amount of Cover",
            value = formState.amountOfCover,
            onChange = { value -> viewModel.updateField { it.copy(amountOfCover = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Premium",
            value = formState.premium,
            onChange = { value -> viewModel.updateField { it.copy(premium = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "CLTI/ADSS",
            value = formState.cltiAdss,
            onChange = { value -> viewModel.updateField { it.copy(cltiAdss = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Sum Insured",
            value = formState.sumInsured,
            onChange = { value -> viewModel.updateField { it.copy(sumInsured = value) } }
        )

        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Rice Insurance Details",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RiceStatusField(
                    "Wet",
                    formState.wet
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            wet = value,
                            dry = !value
                        )
                    }
                }
                RiceStatusField(
                    "Dry",
                    formState.dry
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            dry = value,
                            wet = !value
                        )
                    }
                }
            }
        }
        TextFieldRiceStatus(
            context = context,
            label = "CIC No",
            value = formState.cicNo,
            onChange = { value -> viewModel.updateField { it.copy(cicNo = value) } }
        )
        RiceDatePicker(
            context = context,
            "Date CIC Issued",
            value = formState.dateCicIssued,
            onChange = { value -> viewModel.updateField { it.copy(dateCicIssued = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "COC No",
            value = formState.cocNo,
            onChange = { value -> viewModel.updateField { it.copy(cocNo = value) } }
        )
        RiceDatePicker(
            context = context,
            "Date COC Issued",
            value = formState.dateCocIssued,
            onChange = { value -> viewModel.updateField { it.copy(dateCocIssued = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "Period of Cover",
            value = formState.periodOfCover,
            onChange = { value -> viewModel.updateField { it.copy(periodOfCover = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "From",
            value = formState.from,
            onChange = { value -> viewModel.updateField { it.copy(from = value) } }
        )
        TextFieldRiceStatus(
            context = context,
            label = "To",
            value = formState.to,
            onChange = { value -> viewModel.updateField { it.copy(to = value) } }
        )
        Spacer(modifier = Modifier.height(25.dp))
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .heightIn(min = 20.dp, max = 20.dp)
                        .fillMaxWidth()
                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = Color.Gray,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                )
            }
            Text(
                text = "Signature/Thumb Mark Over Printed Name",
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )
            Text(
                text = "Farmer Applicant",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 5.dp),
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .heightIn(min = 50.dp, max = 50.dp)
                        .fillMaxWidth()
                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = Color.Gray,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                )
            }
            Text(
                text = "Signature Over Printed Name",
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )
            Text(
                text = "Supervising Agriculture Technologist",
                modifier = Modifier.padding(top = 5.dp),
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .heightIn(min = 50.dp, max = 50.dp)
                        .fillMaxWidth()
                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = Color.Gray,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                )
            }
            Text(
                text = "",
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif
            )
            Text(
                text = "Date",
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )
        }
        Button(
            onClick = {
                val updatedFormState = formState.copy(userId = currentUser.id!!)
                onSubmit(updatedFormState)
                navController.popBackStack()
            },
            modifier = Modifier
                .width(360.dp)
                .padding(bottom = 50.dp)
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF136204),
                contentColor = Color.White
            )
        ) {
            Text(text = "Submit", fontSize = 17.sp)
        }
    }
}

@Composable
fun RiceDatePicker(
    context: Context,
    label: String,
    value: String?,
    onChange: (String) -> Unit
) {
    var displayedDate by rememberSaveable { mutableStateOf("") }

    val displayDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val saveDateFormat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    LaunchedEffect(value) {
        displayedDate = if (value.isNullOrEmpty()) "" else {
            try {
                val parsedDate = saveDateFormat.parse(value)
                displayDateFormat.format(parsedDate!!)
            } catch (e: ParseException) {
                ""
            }
        }
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)

            displayedDate = displayDateFormat.format(calendar.time)
            onChange(saveDateFormat.format(calendar.time))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                datePickerDialog.show()
            }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(" : ", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.width(13.dp))

            Surface(
                modifier = Modifier
                    .clickable {
                        datePickerDialog.show()
                    }
                    .background(Color.Transparent),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 50.dp, max = 50.dp)
                        .background(Color.White)
                ){
                    Text(
                        text = displayedDate,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier
                            .padding(10.dp)
                            .padding(start = 5.dp)
                            .align(Alignment.CenterStart),
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun RiceCheckBoxField(label: String, value: Boolean, onChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.
    width(160.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Checkbox(checked = value, onCheckedChange = onChange)
        Text(label, modifier = Modifier.padding(start = 1.dp))
    }
}

@Composable
fun RiceCheckBoxCoverage(label: String, value: Boolean, onChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.
    width(120.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Checkbox(checked = value, onCheckedChange = onChange)
        Text(label, modifier = Modifier.padding(start = 1.dp))
    }
}


@Composable
fun TextFieldRiceStatus(
    context: Context,
    label: String,
    value: String?,
    onChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier =  Modifier
                .padding(top = 5.dp)
        )
        Text(
            text = " : ",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,
            modifier =  Modifier
                .padding(top = 5.dp, start = 5.dp, end = 5.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            OutlinedTextField(
                value = value ?: "",
                onValueChange = onChange,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
        }
    }
}

@Composable
fun RiceStatusField(label: String, value: Boolean, onChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.
    width(115.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Checkbox(checked = value, onCheckedChange = onChange)
        Text(label, modifier = Modifier.padding(start = 1.dp))
    }
}