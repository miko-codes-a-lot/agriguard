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
fun InDemnityFormPreview() {
    InDemnityFormUI()
}

@Composable
fun InDemnityFormUI() {
    var selectedDate by remember { mutableStateOf("") }
    var othersProgram by remember { mutableStateOf("") }
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
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InDemnityDatePicker("Date", selectedDate) { newDate ->
                    selectedDate = newDate
                }
            }
        }
        item {
            Text(
                text = "CLAIM FOR INDEMNITY",
                fontFamily = FontFamily.Serif,
                fontSize = 19.sp,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
            )
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "To  :  ",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "The Chief CAD / PCIC RO",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                )
            }
        }
        item {
            FarmerInfoInDemnity()
            ProgramInfo(othersProgram, onValueChange = { newProgram -> othersProgram = newProgram })
        }
        item {
            CropsDamage()
        }
        item {
            LocationSketch()
        }
        item{
            CropsCoast()
        }
        item{
            Verification()
        }
        item {
            Spacer(modifier = Modifier.height(15.dp))
            InDemnityButton()
        }
    }
}

@Composable
fun InDemnityDatePicker(
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
        Column(
            modifier = Modifier
                .widthIn(min = 220.dp, max = 260.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .clickable {
                        datePickerDialog.show()
                    }
                    .background(Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .width(202.dp)
                        .heightIn(min = 50.dp, max = 50.dp)
                        .background(Color.White)
                        .align(Alignment.CenterHorizontally)

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
                            .fillMaxWidth(),
                        fontSize = 17.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun FarmerInfoInDemnity() {
    val labels = listOf(
        "FirstName" to remember{ mutableStateOf("") },"MiddleName" to remember{ mutableStateOf("") },"LastName" to remember{ mutableStateOf("") }, "Address" to remember { mutableStateOf("") }, "Mobile Number" to remember{ mutableStateOf("") },
        "Location of Farm" to remember{ mutableStateOf("") }, "Area Insured" to remember{ mutableStateOf("") }, "Variety Planted" to remember{ mutableStateOf("") }, "Date of Sowing" to remember { mutableStateOf("") }, "Actual Date of Planting" to remember { mutableStateOf("") },
        "CIC Number" to remember { mutableStateOf("") }, "UnderWriter/Cooperative" to remember { mutableStateOf("") }
    )
    LazyColumn(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(labels) { (label, state) ->
            TextFieldInDemnityForm(
                label = label,
                value = state.value,
                onValueChange = { newvalue -> state.value = newvalue }
            )
        }
    }
}

@Composable
fun TextFieldInDemnityForm(label: String, value: String, onValueChange: (String) -> Unit) {
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
fun ProgramInfo( value: String, onValueChange: (String) -> Unit ) {
    var isRegular by remember { mutableStateOf(true) }
    var isPunla by remember { mutableStateOf(false) }
    var isCooperate by remember { mutableStateOf(false) }
    var isSikatSaka by remember { mutableStateOf(false) }
    var isRSBSA by remember { mutableStateOf(false) }
    var isAPCPCAPPBD by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 15.dp)
    ) {
        Text(
            text = "Program",
            fontSize = 17.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
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
                    checked = isRegular,
                    onCheckedChange = { isRegular = it },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Regular",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isPunla,
                    onCheckedChange = { isPunla = it },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Punla",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isCooperate,
                    onCheckedChange = { isCooperate = it },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Cooperate Rice Farming",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
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
                    checked = isRSBSA,
                    onCheckedChange = { isRSBSA = it },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "RSBSA",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isSikatSaka,
                    onCheckedChange = { isSikatSaka = it },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "Sikat Saka",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isAPCPCAPPBD,
                    onCheckedChange = { isAPCPCAPPBD = it },
                    colors = CheckboxDefaults.colors(Color(0xFF136204))
                )
                Text(
                    text = "APCP-CAP-PBD",
                    modifier = Modifier.padding(start = 8.dp)
                )
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
            text = "Others",
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
                onValueChange = onValueChange ,
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
fun CropsDamage(

) {
    val Damagelabels = listOf(
        "Cause of Damage" to remember{ mutableStateOf("") },"Date of Loss Occurrence" to remember{ mutableStateOf("") },"Age/Stage of Cultivation" to remember{ mutableStateOf("") },
        "Area Damaged" to remember{ mutableStateOf("") }, "Extent/Degree of Damage" to remember{ mutableStateOf("") }, "Expected Date of Harvest" to remember{ mutableStateOf("") }
    )
    Column(
        modifier = Modifier
            .padding(top = 25.dp, bottom = 15.dp)
    ) {
        Text(
            text = "Damage Indicators",
            fontSize = 17.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
    }
    LazyColumn(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(Damagelabels) { (label, state) ->
            TextFieldDamageForm(
                label = label,
                value = state.value,
                onValueChange = { newvalue -> state.value = newvalue }
            )
        }
    }
}

@Composable
fun TextFieldDamageForm(label: String, value: String, onValueChange: (String) -> Unit) {
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
fun LocationSketch() {
    val farmLocation = remember { mutableStateListOf(initialFarmLocation()) }
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ){
        IconButton(
            onClick = {
                farmLocation.add(initialFarmLocation())
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
                farmLocation.removeAt(farmLocation.lastIndex)
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
            items(farmLocation) { farmDetail ->
                farmDetail.forEach { (label, state) ->
                    TextFieldFarmLocation(
                        label = label,
                        value = state.value,
                        onValueChange = { newValue -> state.value = newValue }
                    )
                }
            }
        }
    }
}

fun initialFarmLocation(): List<Pair<String, MutableState<String>>> {
    return listOf(
        "North"  to mutableStateOf(""),
        "South"  to mutableStateOf(""),
        "East"  to mutableStateOf(""),
        "West"  to mutableStateOf(""),
    )
}

@Composable
fun TextFieldFarmLocation(label: String, value: String, onValueChange: (String) -> Unit) {
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
fun CropsCoast() {
    val conditionStates = mapOf (
        "Upa Sa Pag-Gawa" to Pair(
            remember { mutableStateOf("") },
            remember { mutableStateOf("") }
        ),
        "Binhi" to Pair(
            remember { mutableStateOf("") },
            remember { mutableStateOf("") }
        ),
        "Abono" to Pair(
            remember { mutableStateOf("") },
            remember { mutableStateOf("") }
        ),
        "Kemikal" to Pair(
            remember { mutableStateOf("") },
            remember { mutableStateOf("") }
        ),
        "Patubig" to Pair(
            remember { mutableStateOf("") },
            remember { mutableStateOf("") }
        ),
        "Iba pa" to Pair(
            remember { mutableStateOf("") },
            remember { mutableStateOf("") }
        ),
        "Kabuuan" to Pair(
            remember { mutableStateOf("") },
            remember { mutableStateOf("") }
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "KABUUANG GASTOS",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
    Row(
        modifier = Modifier
            .padding(top = 5.dp, bottom = 15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "MGA KAILANGAN",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 15.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "BILANG",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(41.dp))
            Text(
                text = "HALAGA",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(337.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(conditionStates.toList()) { (label, state) ->
            LabelAndValue(
                label = label,
                bilangValue = state.first.value,
                onBilangValueChange = { newValue -> state.first.value = newValue },
                halagaValue = state.second.value,
                onHalagaValueChange = { newValue -> state.second.value = newValue }
            )
        }
    }
}

@Composable
fun LabelAndValue(
    label: String,
    bilangValue: String,
    onBilangValueChange: (String) -> Unit,
    halagaValue: String,
    onHalagaValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1.4f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = bilangValue,
                onValueChange = onBilangValueChange,
                modifier = Modifier
                    .weight(0.9f)
                    .align(Alignment.CenterVertically),
                shape = RectangleShape,
//                    .border(1.dp, Color.Black),
                singleLine = true
            )
            OutlinedTextField(
                value = halagaValue,
                onValueChange = onHalagaValueChange,
                modifier = Modifier
                    .weight(0.9f)
                    .align(Alignment.CenterVertically),
                shape = RectangleShape,
//                    .border(1.dp, Color.Black),
                singleLine = true
            )
        }
    }
}

@Composable
fun Verification() {
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
            text = "Signature Over Printed Name",
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "(Name Of Assured Farmer)",
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 8.dp),
            fontFamily = FontFamily.SansSerif,
            color = Color.Black,
        )
    }
}

@Composable
fun InDemnityButton() {
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
