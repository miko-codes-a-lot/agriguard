package com.example.agriguard.modules.main.indemnity.ui
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agriguard.modules.main.indemnity.viewmodel.IndemnityViewModel
import com.example.agriguard.modules.main.onion.ui.CheckStatusField
import com.example.agriguard.modules.main.onion.ui.OnionDatePicker
import com.example.agriguard.modules.main.onion.ui.TextFieldOnionStatus
import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Composable
fun IndemnityFormUI(
    navController: NavController,
    currentUser: UserDto,
    viewModel: IndemnityViewModel,
    onSubmit: (IndemnityDto) -> Unit
) {
    val formState by viewModel.formState.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
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
                OnionDatePicker(
                    context = context,
                    "Date",
                    value = formState.fillUpdate,
                    onChange = { value -> viewModel.updateField { it.copy(fillUpdate = value) } }
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "CLAIM FOR INDEMNITY",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
        TextFieldIndemnityStatus(
            context = context,
            label = "To  :  ",
            value = "The Chief CAD / PCIC RO",
            onChange = { }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "First Name",
            value = currentUser.firstName,
            onChange = { }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "MiddleName",
            value = currentUser.middleName,
            onChange = { }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "LastName",
            value = currentUser.lastName,
            onChange = { }
        )
        val saveDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

        val displayDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        val formattedDate = try {
            val parsedDate = saveDateFormat.parse(currentUser.dateOfBirth)
            displayDateFormat.format(parsedDate)
        } catch (e: ParseException) {
        }

        TextFieldOnionStatus(
            context = context,
            label = "Date Of Birth",
            value = formattedDate.toString(),
            onChange = { }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "Mobile Number",
            value = currentUser.mobileNumber,
            onChange = { }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "Address",
            value = currentUser.address,
            onChange = { }
        )
        Row(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Program",
                fontSize = 17.sp,
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                CheckStatusField(
                    "Regular",
                    formState.regular
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            regular = value,
                        )
                    }
                }
                CheckStatusField(
                    "Punla",
                    formState.punla
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            punla = value,
                        )
                    }
                }
                CheckStatusField(
                    "C0-0P Rice",
                    formState.cooperativeRice
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            cooperativeRice = value,
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
                CheckStatusField(
                    "RSBSA",
                    formState.rsbsa
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            rsbsa = value,
                        )
                    }
                }
                CheckStatusField(
                    "Sikat",
                    formState.sikat
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            sikat = value,
                        )
                    }
                }
                CheckStatusField(
                    "APCP-C",
                    formState.apcpc
                ) { value ->
                    viewModel.updateField {
                        it.copy(
                            apcpc = value,
                        )
                    }
                }
            }
        }
        TextFieldIndemnityStatus(
            context = context,
            label = "Other",
            value = formState.others,
            onChange = { value -> viewModel.updateField { it.copy(others = value) } }
        )
        Row(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Damage Indicators",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextFieldIndemnityStatus(
            context = context,
            label = "Cause Of Damage",
            value = formState.causeOfDamage,
            onChange = { value -> viewModel.updateField { it.copy(causeOfDamage = value) } }
        )
        IndemnityDatePicker(
            context = context,
            "Date Of Loss",
            value = formState.dateOfLoss,
            onChange = { value -> viewModel.updateField { it.copy(dateOfLoss = value) } }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "Age/Stage Of Cultivation",
            value = formState.ageCultivation,
            onChange = { value -> viewModel.updateField { it.copy(ageCultivation = value) } }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "Area Damaged",
            value = formState.areaDamaged,
            onChange = { value -> viewModel.updateField { it.copy(areaDamaged = value) } }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "Degree Of Damaged",
            value = formState.degreeOfDamage,
            onChange = { value -> viewModel.updateField { it.copy(degreeOfDamage = value) } }
        )
        IndemnityDatePicker(
            context = context,
            "Expected Date Of Harvest",
            value = formState.expectedDateOfHarvest,
            onChange = { value -> viewModel.updateField { it.copy(expectedDateOfHarvest = value) } }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "North",
            value = formState.north,
            onChange = { value -> viewModel.updateField { it.copy(north = value) } }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "South",
            value = formState.south,
            onChange = { value -> viewModel.updateField { it.copy(south = value) } }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "East",
            value = formState.east,
            onChange = { value -> viewModel.updateField { it.copy(east = value) } }
        )
        TextFieldIndemnityStatus(
            context = context,
            label = "West",
            value = formState.west,
            onChange = { value -> viewModel.updateField { it.copy(west = value) } }
        )
        Row(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kabuuang Gastos",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
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
                Spacer(modifier = Modifier.width(43.dp))
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Upa Sa Pag-Gawa",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .width(150.dp),
                    textAlign = TextAlign.Center
                )
                TextFieldIndeminity(
                    "Upa Sa Pag-Gawa Bilang",
                    formState.upaSaGawaBilang
                ) { value -> viewModel.updateField { it.copy(upaSaGawaBilang = value) } }
                TextFieldIndeminity(
                    "Upa Sa Pag-Gawa Halaga",
                    formState.upaSaGawaHalaga
                ) { value -> viewModel.updateField { it.copy(upaSaGawaHalaga = value) } }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Binhi",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .width(150.dp),
                    textAlign = TextAlign.Center
                )
                TextFieldIndeminity(
                    "Binhi Bilang",
                    formState.binhiBilang
                ) { value -> viewModel.updateField { it.copy(binhiBilang = value) } }
                TextFieldIndeminity(
                    "Binhi Halaga",
                    formState.binhiHalaga
                ) { value -> viewModel.updateField { it.copy(binhiHalaga = value) } }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Abono",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .width(150.dp),
                    textAlign = TextAlign.Center
                )
                TextFieldIndeminity(
                    "Abono Bilang",
                    formState.abonoBilang
                ) { value -> viewModel.updateField { it.copy(abonoBilang = value) } }
                TextFieldIndeminity(
                    "Abono Halaga",
                    formState.abonoHalaga
                ) { value -> viewModel.updateField { it.copy(abonoHalaga = value) } }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Kemikal",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .width(150.dp),
                    textAlign = TextAlign.Center
                )
                TextFieldIndeminity(
                    "Kemikal Bilang",
                    formState.kemikalBilang
                ) { value -> viewModel.updateField { it.copy(kemikalBilang = value) } }
                TextFieldIndeminity(
                    "Kemikal Halaga",
                    formState.kemikalHalaga
                ) { value -> viewModel.updateField { it.copy(kemikalHalaga = value) } }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Patubig",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .width(150.dp),
                    textAlign = TextAlign.Center
                )
                TextFieldIndeminity(
                    "Patubig Bilang",
                    formState.patubigBilang
                ) { value -> viewModel.updateField { it.copy(patubigBilang = value) } }
                TextFieldIndeminity(
                    "Patubig Halaga",
                    formState.patubigHalaga
                ) { value -> viewModel.updateField { it.copy(patubigHalaga = value) } }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Iba pa",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .width(150.dp),
                    textAlign = TextAlign.Center
                )
                TextFieldIndeminity(
                    "Iba pa Bilang",
                    formState.ibapaBilang
                ) { value -> viewModel.updateField { it.copy(ibapaBilang = value) } }
                TextFieldIndeminity(
                    "Iba pa Halaga",
                    formState.ibapaHalaga
                ) { value -> viewModel.updateField { it.copy(ibapaHalaga = value) } }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Kabuuan",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .width(150.dp),
                    textAlign = TextAlign.Center
                )
                TextFieldIndeminity(
                    "Kabuuan Bilang",
                    formState.kabuuanBilang
                ) { value -> viewModel.updateField { it.copy(kabuuanBilang = value) } }
                TextFieldIndeminity(
                    "Kabuuan Halaga",
                    formState.kabuuanHalaga
                ) { value -> viewModel.updateField { it.copy(kabuuanHalaga = value) } }

            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${currentUser.firstName} ${currentUser.middleName} ${currentUser.lastName}".uppercase(),
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .heightIn(min = 30.dp, max = 30.dp)
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

        Button(
            onClick = {
                val updatedFormState = formState.copy(userId = currentUser.id!!)
                onSubmit(updatedFormState)
                navController.popBackStack()
            },
            modifier = Modifier
                .width(360.dp)
                .padding(bottom = 50.dp, top = 20.dp)
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
fun IndemnityDatePicker(
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
fun TextFieldIndemnityStatus(
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
fun TextFieldIndeminity(label: String, value: String?, onChange: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf(value.orEmpty()) }
    Row(
        modifier = Modifier
            .width(105.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onChange(it)
            },
            shape = RectangleShape,
            singleLine = true
        )
    }
}