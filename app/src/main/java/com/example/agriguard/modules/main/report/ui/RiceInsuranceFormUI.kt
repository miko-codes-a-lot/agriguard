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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun RiceInsurancePreview(){
    RiceInsuranceFormUI()
}

@Composable
fun RiceInsuranceFormUI(){
    var isNewChecked by remember { mutableStateOf(true) }
    var isRenewalChecked by remember { mutableStateOf(false) }
    var isSelfFinancedChecked by remember { mutableStateOf(true) }
    var isBorrowingChecked by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    var insuranceID by remember { mutableStateOf("") }
    var lender by remember { mutableStateOf("") }
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
//            .fillMaxSize()
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
//                    InssuranceID And Lender Not Working Properly
                    InfoFirstLine("Insurance ID", insuranceID) { newId -> insuranceID = newId }
                    InfoFirstLine("Lender", lender) { newLender -> lender = newLender }
                    DatePickerField("Date", selectedDate) { newDate ->
                        selectedDate = newDate
                    }
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
                            checked = isNewChecked,
                            onCheckedChange = { isNewChecked = it },
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
                            checked = isRenewalChecked,
                            onCheckedChange = { isRenewalChecked = it },
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
                            checked = isSelfFinancedChecked,
                            onCheckedChange = { isSelfFinancedChecked = it },
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
                            checked = isBorrowingChecked,
                            onCheckedChange = { isBorrowingChecked = it },
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
            FarmerInfo()
        }
        item {
            InfoStatus()
        }
        item{
            LegalBeneficiaries()
        }
        item{
            FarmSeparate()
        }
        item{
            Coverage()
        }
        item{
            PCIC()
        }
        item{
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Certification()
            }
        }
        item{
            Spacer(modifier = Modifier.height(15.dp))
            RiceInsuranceButton()
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
fun FarmerInfo() {
    val labels = listOf(
        "IP Tribe" to remember{ mutableStateOf("") },"FirstName" to remember{ mutableStateOf("") },"MiddleName" to remember{ mutableStateOf("") },"LastName" to remember{ mutableStateOf("") },"Date Of Birth" to remember{ mutableStateOf("") },
        "If married, Name Of Spouse" to remember{ mutableStateOf("") },"No & Street/Sitio" to remember{ mutableStateOf("") },"Barangay" to remember{ mutableStateOf("") },"Municipality" to remember{ mutableStateOf("") },"Province" to remember{ mutableStateOf("") },
        "Mobile Number" to remember{ mutableStateOf("") },"Bank Name" to remember{ mutableStateOf("") },"Bank Account No" to remember{ mutableStateOf("") },"Bank Address" to remember{ mutableStateOf("") }
    )
    LazyColumn(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(labels) { (label, state) ->
            TextFieldStatusContainer(
                label = label,
                value = state.value,
                onValueChange = { newvalue -> state.value = newvalue }
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
fun InfoStatus() {
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
fun LegalBeneficiaries() {
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
                LegalBeneficiariesTextField(
                    label = label,
                    value = state.value,
                    onValueChange = { newvalue -> state.value = newvalue }
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
fun FarmSeparate(){
    val farmDetails = remember { mutableStateListOf(initialFarmDetail()) }
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ){
        IconButton(
            onClick = {
                farmDetails.add(initialFarmDetail())
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
                farmDetails.removeAt(farmDetails.lastIndex)
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
            items(farmDetails) { farmDetail ->
                farmDetail.forEach { (label, state) ->
                    TextFieldSeparateStatus(
                        label = label,
                        value = state.value,
                        onValueChange = { newValue -> state.value = newValue }
                    )
                }
            }
        }
    }
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

fun initialFarmDetail(): List<Pair<String, MutableState<String>>> {
    return listOf(
        "B.1 Farm Location/LSP"  to mutableStateOf(""),
        "Sitio"  to mutableStateOf(""),
        "Barangay"  to mutableStateOf(""),
        "Municipality"  to mutableStateOf(""),
        "Province"  to mutableStateOf(""),
        "B.2 Boundaries" to  mutableStateOf(""),
        "North"  to mutableStateOf(""),
        "South"  to mutableStateOf(""),
        "East"  to mutableStateOf(""),
        "West"  to mutableStateOf(""),
        "B.3 Variety"  to mutableStateOf(""),
        "B.4 Planting Methods"  to mutableStateOf(""),
        "B.5 Date Of Sowing"  to mutableStateOf(""),
        "B.6 Date Of Planting"  to mutableStateOf(""),
        "B.7 Date Of Harvest"  to mutableStateOf(""),
        "B.8 Land Category"  to mutableStateOf(""),
        "B.9 Soil Types"  to mutableStateOf(""),
        "B.10 Topography"  to mutableStateOf(""),
        "B.11 Source Of Irrigation's"  to mutableStateOf(""),
        "B.12 Tenurial Status" to mutableStateOf(""),
    )
}

@Composable
fun Coverage() {
    var isRice by remember { mutableStateOf(true) }
    var isMultiRisk by remember { mutableStateOf(false) }
    var isNaturalDisaster by remember { mutableStateOf(false) }
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
                    checked = isRice,
                    onCheckedChange = { isRice = it },
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
                    checked = isMultiRisk,
                    onCheckedChange = { isMultiRisk = it },
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
                    checked = isNaturalDisaster,
                    onCheckedChange = { isNaturalDisaster = it },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Natural Disaster",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        val coverageStatus = listOf(
            "Amount Of Cover" to remember{ mutableStateOf("") },"Premium" to remember{ mutableStateOf("") },
            "CLTI-ADSS" to remember{ mutableStateOf("") },"Sum insured (SI)" to remember{ mutableStateOf("") },
        )
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
                items(coverageStatus) { (label, state) ->
                    TextFieldCoverage(
                        label = label,
                        value = state.value,
                        onValueChange = { newvalue -> state.value = newvalue }
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
fun PCIC() {
    var isWet by remember { mutableStateOf(true) }
    var isDry by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Rice",
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
                    checked = isWet,
                    onCheckedChange = { isWet = it },
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
                    checked = isDry,
                    onCheckedChange = { isDry = it },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Dry",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        val pcicStatus = listOf(
            "CIC No" to remember { mutableStateOf("") },
            "Date Issued" to remember { mutableStateOf("") },
            "COC No" to remember { mutableStateOf("") },
            "Date Issued" to remember { mutableStateOf("") },
            "Period Of Cover" to remember { mutableStateOf("") },
            "From" to remember { mutableStateOf("") },
            "To" to remember { mutableStateOf("") },
        )
        Row(
            modifier = Modifier
               .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(305.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(pcicStatus) { (label, state) ->
                    TextFieldPCIC(
                        label = label,
                        value = state.value,
                        onValueChange = { newvalue -> state.value = newvalue }
                    )
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
            .padding(top = 15.dp),
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
        Spacer(modifier = Modifier.height(15.dp))
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
        Spacer(modifier = Modifier.height(15.dp))
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
                text = "Date",
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )
        }
    }
}

@Composable
fun RiceInsuranceButton() {
    Button(
        onClick = {},
        modifier = Modifier
            .padding(bottom = 30.dp)
            .width(360.dp)
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF136204),
            contentColor = Color.White
        )
    ){
        Text(text = "Confirm", fontSize = 17.sp)
    }
}
