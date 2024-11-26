package com.example.agriguard.modules.main.report.ui

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Preview(showSystemUi = true)
@Composable
fun OnionInsuranceFormPreview() {
    OnionInsuranceFormUI()
}

@Composable
fun OnionInsuranceFormUI() {
    var selectedDate by remember { mutableStateOf("") }
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Row(
                modifier = Modifier
                    .padding(top = 8.dp,bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    CropsInfo("Crops", "Onion")

                    OnionDatePicker("Date", selectedDate) { newDate ->
                        selectedDate = newDate
                    }
                }
            }
        }
        item{
            FarmerOnionInfo()
        }
        item {
            InfoOntionStatus()
        }
        item{
            LegalOnionBeneficiaries()
        }
        item {
            Plantation()
        }
        item {
            Planted()
        }
        item {
            CostAndProduction()
        }
        item {
            CropsInformation()
        }
        item {
            OnionInsuranceVerification()
        }
        item {
            Spacer(modifier = Modifier.height(15.dp))
            OnionInsuranceButton()
        }
    }
}

@Composable
fun CropsInfo(label: String, value: String) {
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
            Spacer(modifier = Modifier.width(5.dp))

            Text(" : ", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.width(13.dp))
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
                    onValueChange = {},
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
fun OnionDatePicker(
    label: String,
    dateValue: String,
    onDateChange: (String) -> Unit,
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
//                onErrorChange(false)
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
//        "Select Date"
        ""
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
fun FarmerOnionInfo() {
    val labels = listOf(
        "IP Tribe" to remember{ mutableStateOf("") },"FirstName" to remember{ mutableStateOf("") },"MiddleName" to remember{ mutableStateOf("") },"LastName" to remember{ mutableStateOf("") },"Date Of Birth" to remember{ mutableStateOf("") },
        "Mobile Number" to remember{ mutableStateOf("") },"Address" to remember { mutableStateOf("") },"If married, Name Of Spouse" to remember{ mutableStateOf("") },
    )
    LazyColumn(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(labels) { (label, state) ->
            TextFieldOnionStatus(
                label = label,
                value = state.value,
                onValueChange = { newvalue -> state.value = newvalue }
            )
        }
    }
}

@Composable
fun TextFieldOnionStatus(label: String, value: String, onValueChange: (String) -> Unit) {
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
fun InfoOntionStatus() {
    var isMale by remember { mutableStateOf(true) }
    var isFeMale by remember { mutableStateOf(false) }
    var isSingle by remember { mutableStateOf(true) }
    var isMarried by remember { mutableStateOf(false) }
    var isWidow by remember { mutableStateOf(false) }
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
                    checked = isMale,
                    onCheckedChange = { isMale = it },
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
                    checked = isFeMale,
                    onCheckedChange = { isFeMale = it },
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
                    checked = isSingle,
                    onCheckedChange = { isSingle = it },
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
                    checked = isMarried,
                    onCheckedChange = { isMarried = it },
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
                    checked = isWidow,
                    onCheckedChange = { isWidow = it },
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
fun LegalOnionBeneficiaries() {
    val infoStatus = listOf("Name" to remember { mutableStateOf("") },
        "Age" to remember { mutableStateOf("") }, "RelationShip" to remember { mutableStateOf("") } )
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
                LegalBeneficiaries(
                    label = label,
                    value = state.value,
                    onValueChange = { newvalue -> state.value = newvalue }
                )
            }
        }
    }
}

@Composable
fun LegalBeneficiaries(label: String, value: String, onValueChange: (String) -> Unit) {
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
fun Plantation() {
    val plantationForm = remember { mutableStateListOf(plantationLocation()) }

    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ){
        Text(
            text = "1. Name Of Plantation",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                plantationForm.add(plantationLocation())
            },
            modifier = Modifier
                .size(21.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Farm Detail",
                tint = Color(0xFF136204)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(
            onClick = {
                plantationForm.removeAt(plantationForm.lastIndex)
            },
            modifier = Modifier
                .size(21.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Farm Detail",
                tint = Color(0xFF136204)
            )
        }
    }
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
            items(plantationForm) { farmDetail ->
                farmDetail.forEach { (label, state) ->
                    TextFieldOnionFarmLocation(
                        label = label,
                        value = state.value,
                        onValueChange = { newValue -> state.value = newValue }
                    )
                }
            }
        }
    }
}

fun plantationLocation(): List<Pair<String, MutableState<String>>> {
    return listOf(
        "Farm Location" to mutableStateOf(""),
        "Area" to mutableStateOf(""),
        "Soil Type" to mutableStateOf(""),
        "Soil pH" to mutableStateOf(""),
        "Topography" to mutableStateOf("")
    )
}

@Composable
fun TextFieldOnionFarmLocation(label: String, value: String, onValueChange: (String) -> Unit) {
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

@Composable
fun Planted() {
    val plantedForm = remember { mutableStateListOf(plantedLabelAndValue()) }
    val typeOfIrrigation = remember { mutableStateOf("") }
    val averageYield = remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ){
        Text(
            text = "2. Variety Of Planted",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                plantedForm.add(plantedLabelAndValue())
            },
            modifier = Modifier
                .size(21.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Farm Detail",
                tint = Color(0xFF136204)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(
            onClick = {
                plantedForm.removeAt(plantedForm.lastIndex)
            },
            modifier = Modifier
                .size(21.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Farm Detail",
                tint = Color(0xFF136204)
            )
        }
    }
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
            items(plantedForm) { farmDetail ->
                farmDetail.forEach { (label, state) ->
                    TextFieldOnionPlanted(
                        label = label,
                        value = state.value,
                        onValueChange = { newValue -> state.value = newValue }
                    )
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "3. Type of Irrigation",
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
                value = typeOfIrrigation.value,
                onValueChange = { typeOfIrrigation.value = it },
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
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "4. Average Yield",
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
                value = averageYield.value,
                onValueChange = { averageYield.value = it },
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

fun plantedLabelAndValue(): List<Pair<String, MutableState<String>>> {
    return listOf(
        "Variety" to mutableStateOf(""),
        "Date of Planting" to mutableStateOf(""),
        "Estd Date of Harvest" to mutableStateOf(""),
        "Age Group" to mutableStateOf(""),
        "No. of Hills" to mutableStateOf("")
    )
}

@Composable
fun TextFieldOnionPlanted(label: String, value: String, onValueChange: (String) -> Unit) {
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

@Composable
fun CostAndProduction() {
    val costAndProductionForm = remember { mutableStateListOf(costAndProductionLabelAndValue()) }
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ){
        Text(
            text = "5. Cost of Production Inputs (CPI)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                costAndProductionForm.add(costAndProductionLabelAndValue())
            },
            modifier = Modifier
                .size(21.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Farm Detail",
                tint = Color(0xFF136204)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(
            onClick = {
                costAndProductionForm.removeAt(costAndProductionForm.lastIndex)
            },
            modifier = Modifier
                .size(21.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Farm Detail",
                tint = Color(0xFF136204)
            )
        }
    }
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
            items(costAndProductionForm) { farmDetail ->
                farmDetail.forEach { (label, state) ->
                    TextFieldCostAndProduction(
                        label = label,
                        value = state.value,
                        onValueChange = { newValue -> state.value = newValue }
                    )
                }
            }
        }
    }
}

fun costAndProductionLabelAndValue(): List<Pair<String, MutableState<String>>> {
    return listOf(
        "Land Preparation" to mutableStateOf(""),
        "Materials Item" to mutableStateOf(""),
        "Materials Quantity" to mutableStateOf(""),
        "Materials Cost" to mutableStateOf(""),
        "Labor Work force" to mutableStateOf(""),
        "Labor Quantity" to mutableStateOf(""),
        "Labor Cost" to mutableStateOf(""),
        "Total Cost (Php)" to mutableStateOf(""),
    )
}

@Composable
fun TextFieldCostAndProduction(label: String, value: String, onValueChange: (String) -> Unit) {
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

@Composable
fun CropsInformation() {
    val cropsInformationForm = remember { mutableStateListOf(cropsLabelAndValue()) }
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ){
        Text(
            text = "6. Farm Information",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                cropsInformationForm.add(costAndProductionLabelAndValue())
            },
            modifier = Modifier
                .size(21.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Farm Detail",
                tint = Color(0xFF136204)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(
            onClick = {
                cropsInformationForm.removeAt(cropsInformationForm.lastIndex)
            },
            modifier = Modifier
                .size(21.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Farm Detail",
                tint = Color(0xFF136204)
            )
        }
    }
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
    ){
        LazyColumn(
            modifier = Modifier
                .heightIn(min = 0.dp, max = 340.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(cropsInformationForm) { farmDetail ->
                farmDetail.forEach { (label, state) ->
                    TextFieldCropsInfo(
                        label = label,
                        value = state.value,
                        onValueChange = { newValue -> state.value = newValue }
                    )
                }
            }
        }
    }
}

fun cropsLabelAndValue(): List<Pair<String, MutableState<String>>> {
    return listOf(
        "Farm Location/LSP" to mutableStateOf(""),
        "Sitio" to mutableStateOf(""),
        "Barangay" to mutableStateOf(""),
        "Municipality" to mutableStateOf(""),
        "Province" to mutableStateOf(""),
        "Boundaries" to mutableStateOf(""),
        "North" to mutableStateOf(""),
        "South" to mutableStateOf(""),
        "East" to mutableStateOf(""),
        "West" to mutableStateOf(""),
    )
}

@Composable
fun TextFieldCropsInfo(label: String, value: String, onValueChange: (String) -> Unit) {
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
        if(label != "Farm Location/LSP" && label != "Boundaries") {
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
fun OnionInsuranceVerification() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 55.dp),
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
            text = "Signature of Proposer",
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.padding(top = 25.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
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
            text = "Name and Signature of Supervising PT",
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun OnionInsuranceButton() {
    Button(
        onClick = {},
        modifier = Modifier
            .width(360.dp)
            .padding(bottom = 30.dp)
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF136204),
            contentColor = Color.White
        )
    ){
        Text(text = "Confirm", fontSize = 17.sp)
    }
}
