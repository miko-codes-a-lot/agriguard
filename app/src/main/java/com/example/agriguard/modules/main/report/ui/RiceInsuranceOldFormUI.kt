package com.example.agriguard.modules.main.report.ui

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agriguard.modules.main.rice.model.dto.RiceInsuranceDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Composable
fun RiceInsuranceOldFormUI(
    navController: NavController,
    currentUser: UserDto,
    riceInsuranceDto: RiceInsuranceDto,
    userViewModel: UserViewModel
){
    var riceInsurance by remember { mutableStateOf(riceInsuranceDto) }
    var fillUpdate by remember { mutableStateOf<String?>(riceInsuranceDto.fillUpDate) }
    val isSubmitting by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 16.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    InfoFirstLine("Insurance ID", riceInsurance.insuranceId ?: "") { newId ->
                        riceInsurance = riceInsurance.copy(insuranceId = newId)
                    }
                    InfoFirstLine("Lender", riceInsurance.lender ?: "") { newLender ->
                        riceInsurance = riceInsurance.copy(lender = newLender)
                    }
                    DatePickerField(
                        label = "Date",
                        dateValue = fillUpdate ?: "",
                        showDivider = true,
                        onDateChange = { newDate ->
                            fillUpdate = newDate
                        }
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .height(25.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                                checked = riceInsurance.new,
                            onCheckedChange = { isChecked ->
                                riceInsurance = riceInsurance.copy(new = isChecked)
                            },
                            colors = CheckboxDefaults.colors(Color(0xFF136204))
                        )
                        Text(
                            text = "New",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .height(25.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = riceInsurance.renewal,
                            onCheckedChange = { isChecked ->
                                riceInsurance = riceInsurance.copy(renewal = isChecked)
                            },
                            colors = CheckboxDefaults.colors(Color(0xFF136204))
                        )
                        Text(
                            text = "Re-Newal",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Row(
                        modifier = Modifier
                            .height(25.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = riceInsurance.selfFinanced,
                            onCheckedChange = { isChecked ->
                                riceInsurance = riceInsurance.copy(selfFinanced = isChecked)
                            },
                            colors = CheckboxDefaults.colors(Color(0xFF136204))
                        )
                        Text(
                            text = "Self-Financed",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .height(25.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = riceInsurance.borrowing,
                            onCheckedChange = { isChecked ->
                                riceInsurance = riceInsurance.copy(borrowing = isChecked)
                            },
                            colors = CheckboxDefaults.colors(Color(0xFF136204))
                        )
                        Text(
                            text = "Borrowing",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
        item {
            FarmerInfo(
                currentUser = currentUser ,
                riceInsuranceDto = riceInsurance,
                onUpdate = { updatedDto -> riceInsurance = updatedDto },
//                onUserUpdate = { updatedUser -> editCurrentUser = updatedUser }
            )
        }
        item {
            InfoStatus(
                riceInsuranceDto = riceInsurance,
                onUpdate = { updatedDto -> riceInsurance = updatedDto }
            )
        }
        item {
            LegalBeneficiaries(
                riceInsuranceDto = riceInsurance,
                onUpdate = { updatedDto -> riceInsurance = updatedDto }
            )
        }
        item{
            FarmSeparate(
                riceInsuranceDto = riceInsurance,
                onUpdate = { updatedDto -> riceInsurance = updatedDto }
            )
        }
        item{
            Coverage(
                riceInsuranceDto = riceInsurance,
                onUpdate = { updatedDto -> riceInsurance = updatedDto }
            )
        }
        item{
            PCIC(
                riceInsuranceDto = riceInsurance,
                onUpdate = { updatedDto -> riceInsurance = updatedDto }
            )
        }
        item{
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Certification(
                )
            }
        }
        item{
            Spacer(modifier = Modifier.height(15.dp))
            RiceInsuranceButton(
                riceInsuranceDto = remember { mutableStateOf(riceInsurance) },
                selectedDate = fillUpdate ?: "",
                isSubmitting = remember { mutableStateOf(isSubmitting) },
                userViewModel = userViewModel,
                navController = navController,
                currentUser = currentUser
            )
        }
    }
}

@Composable
fun InfoFirstLine(label: String, value: String, onValueChange: (String) -> Unit) {
    Column{
        Row(
            modifier = Modifier
                .widthIn(min = 220.dp, max = 260.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = " : ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Box(
                modifier = Modifier
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
            ) {
                OutlinedTextField(
                    value = value,
                     onValueChange = onValueChange,
                    textStyle = TextStyle(fontSize = 16.sp, fontFamily = FontFamily.SansSerif),
                    modifier = Modifier
                        .heightIn(min = 50.dp, max = 50.dp),
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
}

@Composable
fun DatePickerField(
    label: String,
    dateValue: String,
    onDateChange: (String) -> Unit,
    showDivider: Boolean = true
//    isError: Boolean,
//    onErrorChange: (Boolean) -> Unit,
//    errorMessage: String = ""
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                isoFormat.timeZone = TimeZone.getTimeZone("UTC")
                val dateISO = isoFormat.format(calendar.time)
                onDateChange(dateISO)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val displayDate = try {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateValue)
        date?.let { displayFormat.format(it) } ?: "Select Date"
    } catch (e: Exception) {
        "Select Date"
    }

    Row(
        modifier = Modifier
            .widthIn(min = 220.dp, max = 260.dp)
//            .fillMaxWidth()
            .clickable {
                datePickerDialog.show()
            }
    ){
        Row(
            modifier = Modifier
            .widthIn(min = 220.dp, max = 260.dp),
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
                        .width(202.dp)
                        .heightIn(min = 50.dp, max = 50.dp)
                        .background(Color.White)
                ){
//                    if (isError) {
//                        Text(
//                            text = errorMessage,
//                            color = Color.Red,
//                            modifier = Modifier.padding(top = 4.dp),
//                            fontSize = 12.sp
//                        )
//                    }else {
                        Text(
                            text = displayDate,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier
                                .padding(10.dp)
                                .padding(start = 5.dp)
                                .align(Alignment.CenterStart),
                            fontSize = 17.sp,
                            color = Color.Black
                        )
//                    }
                    if (showDivider) {
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
}

@Composable
fun FarmerInfo(
    currentUser: UserDto,
    riceInsuranceDto: RiceInsuranceDto,
    onUpdate: (RiceInsuranceDto) -> Unit,
) {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    val dateOfBirthFormatted = try {
        val date = isoDateFormat.parse(currentUser.dateOfBirth)
        date?.let { dateFormat.format(it) } ?: currentUser.dateOfBirth
    } catch (e: Exception) {
        currentUser.dateOfBirth
    }
    val labels = listOf(
        "IP Tribe" to riceInsuranceDto.ipTribe.orEmpty(),
        "First Name" to currentUser.firstName,
        "Middle Name" to currentUser.middleName.orEmpty(),
        "Last Name" to currentUser.lastName,
        "Date of Birth" to dateOfBirthFormatted,
        "If Married, Name of Spouse" to currentUser.nameOfSpouse.orEmpty(),
        "No & Street/Sitio" to riceInsuranceDto.street.orEmpty(),
        "Barangay" to riceInsuranceDto.barangay.orEmpty(),
        "Municipality" to riceInsuranceDto.municipality.orEmpty(),
        "Province" to riceInsuranceDto.province.orEmpty(),
        "Mobile Number" to currentUser.mobileNumber.orEmpty(),
        "Bank Name" to riceInsuranceDto.bankName.orEmpty(),
        "Bank Address" to riceInsuranceDto.bankAddress.orEmpty()
    )
    LazyColumn(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(labels) { (label, value) ->
            TextFieldStatusContainer(
                label = label,
                value = value,
                onValueChange = { newValue ->
                    onUpdate(
                        when (label) {
                            "IP Tribe" -> riceInsuranceDto.copy(ipTribe = newValue)
                            "No & Street/Sitio" -> riceInsuranceDto.copy(street = newValue)
                            "Barangay" -> riceInsuranceDto.copy(barangay = newValue)
                            "Municipality" -> riceInsuranceDto.copy(municipality = newValue)
                            "Province" -> riceInsuranceDto.copy(province = newValue)
                            "Bank Name" -> riceInsuranceDto.copy(bankName = newValue)
                            "Bank Address" -> riceInsuranceDto.copy(bankAddress = newValue)
                            else -> riceInsuranceDto
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun TextFieldStatusContainer(label: String, value: String, onValueChange: (String) -> Unit) {
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
                value = value,
                onValueChange = onValueChange,
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
fun InfoStatus(
    riceInsuranceDto: RiceInsuranceDto,
    onUpdate: (RiceInsuranceDto) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = riceInsuranceDto.male,
                    onCheckedChange = { isChecked ->
                        onUpdate(riceInsuranceDto.copy(male = isChecked, female = !isChecked))
                    },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Male",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = riceInsuranceDto.female,
                    onCheckedChange = { isChecked ->
                        onUpdate(riceInsuranceDto.copy(female = isChecked, male = !isChecked))
                    },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "FeMale",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = riceInsuranceDto.single,
                    onCheckedChange = { isChecked ->
                        onUpdate(riceInsuranceDto.copy(single = isChecked, married = false, widow = false))
                    },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Single",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = riceInsuranceDto.married,
                    onCheckedChange = { isChecked ->
                        onUpdate(riceInsuranceDto.copy(married = isChecked, single = false, widow = false))
                    },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Married",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = riceInsuranceDto.widow,
                    onCheckedChange = { isChecked ->
                        onUpdate(riceInsuranceDto.copy(widow = isChecked, single = false, married = false))
                    },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Widow",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun LegalBeneficiaries(
    riceInsuranceDto: RiceInsuranceDto,
    onUpdate: (RiceInsuranceDto) -> Unit
) {

    val infoStatus = listOf(
        "Name"  to riceInsuranceDto.nameOfBeneficiary,
        "Age" to riceInsuranceDto.age,
        "RelationShip" to riceInsuranceDto.relationship,
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Legal Beneficiaries",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        LazyColumn(
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(infoStatus) { (label, state) ->
                LegalBeneficiariesTextField(
                    label = label,
                    value = state ?: "",
                    onValueChange = { newValue ->
                        val updatedDto = when (label) {
                            "Name" -> riceInsuranceDto.copy(nameOfBeneficiary = newValue)
                            "Age" -> riceInsuranceDto.copy(age = newValue)
                            "RelationShip" -> riceInsuranceDto.copy(relationship = newValue)
                            else -> riceInsuranceDto
                        }
                        onUpdate(updatedDto)
                    }
                )
            }
        }
    }
}

@Composable
fun LegalBeneficiariesTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
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
                        value = value,
                        onValueChange = onValueChange,
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
    }
}

@Composable
fun FarmSeparate(
    riceInsuranceDto: RiceInsuranceDto,
    onUpdate: (RiceInsuranceDto) -> Unit
){
    val farmSeparateDetails = remember {
        mutableStateListOf(
            "B.1 Farm Location/LSP" to mutableStateOf(""),
            "Sitio" to mutableStateOf(riceInsuranceDto.sitio.orEmpty()),
            "Barangay" to mutableStateOf(riceInsuranceDto.farmLocationBarangay.orEmpty()),
            "Municipality" to mutableStateOf(riceInsuranceDto.municipality.orEmpty()),
            "Province" to mutableStateOf(riceInsuranceDto.farmLocationProvince.orEmpty()),
            "B.2 Boundaries" to mutableStateOf(""),
            "North" to mutableStateOf(riceInsuranceDto.north.orEmpty()),
            "South" to mutableStateOf(riceInsuranceDto.south.orEmpty()),
            "East" to mutableStateOf(riceInsuranceDto.east.orEmpty()),
            "West" to mutableStateOf(riceInsuranceDto.west.orEmpty()),
            "B.3 Variety" to mutableStateOf(riceInsuranceDto.variety.orEmpty()),
            "B.4 Planting Methods" to mutableStateOf(riceInsuranceDto.platingMethod.orEmpty()),
            "B.5 Date Of Sowing" to mutableStateOf(riceInsuranceDto.dateOfSowing.toString()),
            "B.6 Date Of Planting" to mutableStateOf(riceInsuranceDto.dateOfPlanting.toString()),
            "B.7 Date Of Harvest" to mutableStateOf(riceInsuranceDto.dateOfHarvest.toString()),
            "B.8 Land Category" to mutableStateOf(riceInsuranceDto.landOfCategory.orEmpty()),
            "B.9 Soil Types" to mutableStateOf(riceInsuranceDto.soilTypes.orEmpty()),
            "B.10 Topography" to mutableStateOf(riceInsuranceDto.topography.orEmpty()),
            "B.11 Source Of Irrigation's" to mutableStateOf(riceInsuranceDto.sourceOfIrrigations.orEmpty()),
            "B.12 Tenurial Status" to mutableStateOf(riceInsuranceDto.tenurialStatus.orEmpty())
        )
    }

//    Row(
//        modifier = Modifier
//            .padding(top = 15.dp)
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.End
//    ){
//        IconButton(
//            onClick = {
//                farmSeparateDetails.addAll(initialDetails())
//            },
//            modifier = Modifier
//                .size(21.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = "Add Farm Detail",
//                tint = Color(0xFF136204)
//            )
//        }
//        Spacer(modifier = Modifier.width(10.dp))
//        IconButton(
//            onClick = {
//                if (farmSeparateDetails.isNotEmpty()) {
//                    farmSeparateDetails.removeAt(farmSeparateDetails.lastIndex)
//                }
//            },
//            modifier = Modifier
//                .size(21.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Delete,
//                contentDescription = "Delete Farm Detail",
//                tint = Color(0xFF136204)
//            )
//        }
//    }

    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
    ){
        LazyColumn(
            modifier = Modifier
                .heightIn(min = 0.dp, max = 360.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(farmSeparateDetails) { (label, state) ->
                if (label in listOf("B.5 Date Of Sowing", "B.6 Date Of Planting", "B.7 Date Of Harvest")) {
                    FarmerDatePickerField(
                        label = label,
                        dateValue = state.value,
                        onDateChange = { newDate ->
                            state.value = newDate
                            val updatedDto = when (label) {
                                "B.5 Date Of Sowing" -> riceInsuranceDto.copy(dateOfSowing = newDate)
                                "B.6 Date Of Planting" -> riceInsuranceDto.copy(dateOfPlanting = newDate)
                                "B.7 Date Of Harvest" -> riceInsuranceDto.copy(dateOfHarvest = newDate)
                                else -> riceInsuranceDto
                            }
                            onUpdate(updatedDto)
                        }
                    )
                } else {
                    TextFieldSeparateStatus(
                        label = label,
                        value = state.value,
                        onValueChange = { newValue ->
                            state.value = newValue
                            val updatedDto = when (label) {
                                "Sitio" -> riceInsuranceDto.copy(sitio = newValue)
                                "Barangay" -> riceInsuranceDto.copy(farmLocationBarangay = newValue)
                                "Municipality" -> riceInsuranceDto.copy(municipality = newValue)
                                "Province" -> riceInsuranceDto.copy(farmLocationProvince = newValue)
                                "North" -> riceInsuranceDto.copy(north = newValue)
                                "South" -> riceInsuranceDto.copy(south = newValue)
                                "East" -> riceInsuranceDto.copy(east = newValue)
                                "West" -> riceInsuranceDto.copy(west = newValue)
                                else -> riceInsuranceDto
                            }
                            onUpdate(updatedDto)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FarmerDatePickerField(
    label: String,
    dateValue: String,
    onDateChange: (String) -> Unit,
    showDivider: Boolean = true
//    isError: Boolean,
//    onErrorChange: (Boolean) -> Unit,
//    errorMessage: String = ""
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                isoFormat.timeZone = TimeZone.getTimeZone("UTC")
                val dateISO = isoFormat.format(calendar.time)

                Log.d("FarmerDatePicker", "Selected Date: $dateISO")

                onDateChange(dateISO)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val displayDate = try {
        if (dateValue.isNotEmpty()) {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateValue)
            date?.let { displayFormat.format(it) } ?: "Select Date"
        } else {
            "Select Date"
        }
    } catch (e: Exception) {
        "Select Date"
    }

    Row(
        modifier = Modifier
            .widthIn(min = 220.dp, max = 260.dp)
//            .fillMaxWidth()
            .clickable {
                Log.d("FarmerDatePicker", "Opening Date Picker for: $label")
                datePickerDialog.show()
            }
    ){
        Row(
            modifier = Modifier
                .widthIn(min = 220.dp, max = 260.dp),
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
                        .width(202.dp)
                        .heightIn(min = 50.dp, max = 50.dp)
                        .background(Color.White)
                ){
//                    if (isError) {
//                        Text(
//                            text = errorMessage,
//                            color = Color.Red,
//                            modifier = Modifier.padding(top = 4.dp),
//                            fontSize = 12.sp
//                        )
//                    }else {
                    Text(
                        text = displayDate,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier
                            .padding(10.dp)
                            .padding(start = 5.dp)
                            .align(Alignment.CenterStart),
                        fontSize = 17.sp,
                        color = Color.Black
                    )
//                    }
                    if (showDivider) {
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
}

fun initialDetails(): List<Pair<String, MutableState<String>>> {
    return listOf(
        "Sitio" to mutableStateOf(""),
        "Barangay" to mutableStateOf(""),
        "Municipality" to mutableStateOf(""),
        "Province" to mutableStateOf(""),
        "North" to mutableStateOf(""),
        "South" to mutableStateOf(""),
        "East" to mutableStateOf(""),
        "West" to mutableStateOf(""),
        "B.3 Variety" to mutableStateOf(""),
        "B.4 Planting Methods" to mutableStateOf(""),
        "B.5 Date Of Sowing" to mutableStateOf(""),
        "B.6 Date Of Planting" to mutableStateOf(""),
        "B.7 Date Of Harvest" to mutableStateOf(""),
        "B.8 Land Category" to mutableStateOf(""),
        "B.9 Soil Types" to mutableStateOf(""),
        "B.10 Topography" to mutableStateOf(""),
        "B.11 Source Of Irrigation's" to mutableStateOf(""),
        "B.12 Tenurial Status" to mutableStateOf("")
    )
}

@Composable
fun TextFieldSeparateStatus(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .border(1.dp, Color.Gray, RectangleShape)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(0.6f)
        )
        if(label != "B.1 Farm Location/LSP" && label != "B.2 Boundaries"){
            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    singleLine = true
                )
            }
        }
    }
}

@Composable
fun Coverage(
    riceInsuranceDto: RiceInsuranceDto,
    onUpdate: (RiceInsuranceDto) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "The Coverage",
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = riceInsuranceDto.rice,
                    onCheckedChange = { onUpdate(riceInsuranceDto.copy(rice = it)) },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Rice",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = riceInsuranceDto.multiRisk,
                    onCheckedChange = { onUpdate(riceInsuranceDto.copy(multiRisk = it)) },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Multi-Risk",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = riceInsuranceDto.natural,
                    onCheckedChange = { onUpdate(riceInsuranceDto.copy(natural = it)) },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Natural Disaster",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        val coverageFields = remember {
            mutableStateListOf(
                "Amount Of Cover" to mutableStateOf(riceInsuranceDto.amountOfCover.orEmpty()),
                "Premium" to mutableStateOf(riceInsuranceDto.premium.orEmpty()),
                "CLTI-ADSS" to mutableStateOf(riceInsuranceDto.cltiAdss.orEmpty()),
                "Sum Insured (SI)" to mutableStateOf(riceInsuranceDto.sumInsured.orEmpty()),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            LazyColumn(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(244.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(coverageFields) { (label, state) ->
                    TextFieldCoverage(
                        label = label,
                        value = state.value,
                        onValueChange = { newValue ->
                            state.value = newValue
                            val updatedDto = when (label) {
                                "Amount Of Cover" -> riceInsuranceDto.copy(amountOfCover = newValue)
                                "Premium" -> riceInsuranceDto.copy(premium = newValue)
                                "CLTI-ADSS" -> riceInsuranceDto.copy(cltiAdss = newValue)
                                "Sum Insured (SI)" -> riceInsuranceDto.copy(sumInsured = newValue)
                                else -> riceInsuranceDto
                            }
                            onUpdate(updatedDto)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TextFieldCoverage(label: String, value: String, onValueChange: (String) -> Unit) {
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
                value = value,
                onValueChange = onValueChange,
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
fun myDatePicker(stateCIC: MutableState<String>, textCIC: MutableState<String>): DatePickerDialog {
    val calendar = Calendar.getInstance()

    return DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }

            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val isoDateString = isoFormat.format(selectedDate.time)

            val friendlyFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            val friendlyDateString = friendlyFormat.format(selectedDate.time)

            stateCIC.value = isoDateString
            textCIC.value = friendlyDateString

            Log.d("DatePicker", "Selected Date (ISO): $isoDateString")
            Log.d("DatePicker", "Selected Date (Friendly): $friendlyDateString")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}


//@Composable
//fun myDatePicker(stateCIC: MutableState<String>, textCIC: MutableState<String>): DatePickerDialog {
//    val calendar = Calendar.getInstance()
//    return DatePickerDialog(
//        LocalContext.current,
//        { _, year, month, dayOfMonth ->
//            val selectedDate = Calendar.getInstance().apply {
//                set(year, month, dayOfMonth)
//            }
//
//            val isoFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            val isoDateString = isoFormat.format(selectedDate.time)
//
//            val friendlyFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
//            val friendlyDateString = friendlyFormat.format(selectedDate.time)
//
//            stateCIC.value = isoDateString
//            textCIC.value = friendlyDateString
//        },
//        calendar.get(Calendar.YEAR),
//        calendar.get(Calendar.MONTH),
//        calendar.get(Calendar.DAY_OF_MONTH)
//    )
//}

@Composable
fun PCIC(
    riceInsuranceDto: RiceInsuranceDto,
    onUpdate: (RiceInsuranceDto) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rice Insurance Details",
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = riceInsuranceDto.wet,
                    onCheckedChange = {
                        onUpdate(riceInsuranceDto.copy(wet = true, dry = false))
                    },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Wet",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = riceInsuranceDto.dry,
                    onCheckedChange = {
                        onUpdate(riceInsuranceDto.copy(wet = false, dry = true))
                    },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Dry",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        val dateCicIssued = rememberSaveable { mutableStateOf(riceInsuranceDto.dateCicIssued) }
        val dateCocIssued = rememberSaveable { mutableStateOf(riceInsuranceDto.dateCocIssued) }

        val pcicStatus = remember {
            mutableStateListOf(
                "CIC No" to mutableStateOf(riceInsuranceDto.cicNo.orEmpty()),
                "Date CIC Issued" to dateCicIssued,
                "COC No" to mutableStateOf(riceInsuranceDto.cocNo.orEmpty()),
                "Date COC Issued" to dateCocIssued,
                "Period Of Cover" to mutableStateOf(riceInsuranceDto.periodOfCover.orEmpty()),
                "From" to mutableStateOf(riceInsuranceDto.from.orEmpty()),
                "To" to mutableStateOf(riceInsuranceDto.to.orEmpty()),
            )
        }

        val stateCIC = rememberSaveable { mutableStateOf(riceInsuranceDto.dateCicIssued) }
        val stateCOC = rememberSaveable { mutableStateOf(riceInsuranceDto.dateCocIssued) }
        val textCIC = rememberSaveable { mutableStateOf("") }
        val textCOC = rememberSaveable { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
//            Column {
//                val cicDialogPicker = myDatePicker(stateCIC, textCIC)
//                Column {
//                    TextField(
//                        value = textCIC.value,
//                        onValueChange = { },
//                        label = { Text("Date CIC Issued") },
//                        readOnly = true,
//                    )
//
//                    Button(onClick = {
//                        cicDialogPicker.show()
//                    }) {
//                        Text("Pick a date")
//                    }
//                }
//            }
            LazyColumn(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(305.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(pcicStatus) { (label, state) ->
                    if (label == "Date CIC Issued") {
                        val cicDialogPicker = myDatePicker(stateCIC, textCIC)
//                        FarmerDatePickerField(
//                            label = label,
//                            dateValue = state.value,
//                            onDateChange = { newDate ->
//                                state.value = newDate
//                                val updatedDto = when (label) {
//                                    "Date CIC Issued" -> riceInsuranceDto.copy(dateCicIssued = newDate)
//                                    "Date COC Issued" -> riceInsuranceDto.copy(dateCocIssued = newDate)
//                                    else -> riceInsuranceDto
//                                }
//                                onUpdate(updatedDto)
//                            }
//                        )
                        Column {
                            TextField(
                                value = textCIC.value,
                                onValueChange = { },
                                label = { Text("Date CIC Issued") },
                                readOnly = true,
                            )

                            Button(onClick = {
                                cicDialogPicker.show()
                            }) {
                                Text("Pick a date")
                            }
                        }
                    } else if (label == "Date COC Issued"){
                        val cicDialogPicker = myDatePicker(stateCOC, textCOC)
                        Column {
                            TextField(
                                value = textCOC.value,
                                onValueChange = { },
                                label = { Text("Date COC Issued") },
                                readOnly = true,
                            )

                            Button(onClick = {
                                cicDialogPicker.show()
                            }) {
                                Text("Pick a date")
                            }
                        }

                    } else {
                        TextFieldPCIC(
                            label = label,
                            value = state.value,
                            onValueChange = { newValue ->
                                state.value = newValue
                                val updatedDto = when (label) {
                                    "CIC No" -> riceInsuranceDto.copy(cicNo = newValue)
                                    "COC No" -> riceInsuranceDto.copy(cocNo = newValue)
                                    "Period Of Cover" -> riceInsuranceDto.copy(periodOfCover = newValue)
                                    "From" -> riceInsuranceDto.copy(from = newValue)
                                    "To" -> riceInsuranceDto.copy(to = newValue)
                                    else -> riceInsuranceDto
                                }
                                onUpdate(updatedDto)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextFieldPCIC(label: String, value: String, onValueChange: (String) -> Unit) {
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
                value = value,
                onValueChange = onValueChange,
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
fun Certification() {
    Column(
        modifier = Modifier
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
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
//                    .widthIn(min = 250.dp, max = 270.dp),
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
    }
}

@Composable
fun RiceInsuranceButton(
    riceInsuranceDto: MutableState<RiceInsuranceDto>,
    selectedDate: String,
    isSubmitting: MutableState<Boolean>,
    userViewModel: UserViewModel,
    navController: NavController,
    currentUser: UserDto
) {
    val isSubmittingState by remember { mutableStateOf(isSubmitting) }
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            if (!isSubmitting.value) {
                if (selectedDate.isEmpty()) {
                    Log.e("Validation", "Date must be selected before submission")
                    return@Button
                }

                riceInsuranceDto.value = riceInsuranceDto.value.copy(
                    fillUpDate = selectedDate,
                    userId = currentUser.id ?: "",
                    new = riceInsuranceDto.value.new
                )

//                isSubmittingState.value = true
            }
        },
        modifier = Modifier
            .padding(bottom = 50.dp)
            .width(360.dp)
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF136204),
            contentColor = Color.White
        )
    ){
        Text(text = "Confirm", fontSize = 17.sp, color = Color.White)
    }

//    LaunchedEffect(isSubmittingState.value) {
//        try {
////            val result = userViewModel.upsertRiceInsurance(riceInsuranceDto.value)
//            if (result.isSuccess) {
//                Log.d("Success", "RiceInsurance successfully saved!")
//                navController.popBackStack()
//            } else {
//                Log.e("Error", "Failed to save riceInsurance: ${result.exceptionOrNull()?.message}")
//            }
//        } catch (e: Exception) {
//            Log.e("Error", "Submission failed: ${e.message}")
//        } finally {
//            isSubmittingState.value = false
//        }
//    }
}