package com.example.agriguard.modules.main.onion.ui

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agriguard.modules.main.onion.model.dto.OnionInsuranceDto
import com.example.agriguard.modules.main.onion.viewmodel.OnionInsuranceViewmodel
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.shared.ui.CheckBoxField
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Composable
fun OnionInsuranceCreate(
    navController: NavController,
    currentUser: UserDto,
    viewModel: OnionInsuranceViewmodel,
    onSubmit: (OnionInsuranceDto) -> Unit
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
                CropsInfo("Crops", "Onion")
                OnionDatePicker(
                    context = context,
                    "Date",
                    value = formState.fillUpDate,
                    onChange = { value -> viewModel.updateField { it.copy(fillUpDate = value) } }
                )
            }
        }
        TextFieldOnionStatus(
            context = context,
            label = "IP Tribe",
            value = formState.ipTribe,
            onChange = { value -> viewModel.updateField { it.copy(ipTribe = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "FirstName",
            value = currentUser.firstName,
            onChange = { }
        )
        TextFieldOnionStatus(
            context = context,
            label = "MiddleName",
            value = currentUser.middleName,
            onChange = { }
        )
        TextFieldOnionStatus(
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
        TextFieldOnionStatus(
            context = context,
            label = "Mobile Number",
            value = currentUser.mobileNumber,
            onChange = { }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Address",
            value = currentUser.address,
            onChange = { }
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
                CheckBoxField(
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
                CheckBoxField(
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
                CheckStatusField(
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
                CheckStatusField(
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
                CheckStatusField(
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
        TextFieldOnionStatus(
            context = context,
            label = "Name",
            value = formState.nameOfBeneficiary,
            onChange = { value -> viewModel.updateField { it.copy(nameOfBeneficiary = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Age",
            value = formState.ageOfBeneficiary,
            onChange = { value -> viewModel.updateField { it.copy(ageOfBeneficiary = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "RelationShip",
            value = formState.relationshipOfBeneficiary,
            onChange = { value -> viewModel.updateField { it.copy(relationshipOfBeneficiary = value) } }
        )
        Row(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "1. Name Of Plantation",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextFieldOnionStatus(
            context = context,
            label = "Farm Location",
            value = formState.farmLocation,
            onChange = { value -> viewModel.updateField { it.copy(farmLocation = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Area",
            value = formState.area,
            onChange = { value -> viewModel.updateField { it.copy(area = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Soil Type",
            value = formState.soilType,
            onChange = { value -> viewModel.updateField { it.copy(soilType = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Soil Ph",
            value = formState.soilPh,
            onChange = { value -> viewModel.updateField { it.copy(soilPh = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Topography",
            value = formState.topography,
            onChange = { value -> viewModel.updateField { it.copy(topography = value) } }
        )
        Row(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "2. Variety Of Planted",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextFieldOnionStatus(
            context = context,
            label = "Variety",
            value = formState.variety,
            onChange = { value -> viewModel.updateField { it.copy(variety = value) } }
        )
        OnionDatePicker(
            context = context,
            "Date of Planting",
            value = formState.dateOfPlanting,
            onChange = { value -> viewModel.updateField { it.copy(dateOfPlanting = value) } }
        )
        OnionDatePicker(
            context = context,
            "Estd Date of Harvest",
            value = formState.estdDateOfHarvest,
            onChange = { value -> viewModel.updateField { it.copy(estdDateOfHarvest = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Age Group",
            value = formState.ageGroup,
            onChange = { value -> viewModel.updateField { it.copy(ageGroup = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "No. of Hills",
            value = formState.noOfHills,
            onChange = { value -> viewModel.updateField { it.copy(noOfHills = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "3. Type of Irrigation",
            value = formState.typeOfIrrigation,
            onChange = { value -> viewModel.updateField { it.copy(typeOfIrrigation = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "4. Average Yield",
            value = formState.averageYield,
            onChange = { value -> viewModel.updateField { it.copy(averageYield = value) } }
        )
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "5. Cost of Production Inputs (CPI)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextFieldOnionStatus(
            context = context,
            label = "Land Preparation",
            value = formState.landPreparation,
            onChange = { value -> viewModel.updateField { it.copy(landPreparation = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Materials Item",
            value = formState.materialsItem,
            onChange = { value -> viewModel.updateField { it.copy(materialsItem = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Materials Quantity",
            value = formState.materialsQuantity,
            onChange = { value -> viewModel.updateField { it.copy(materialsQuantity = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Materials Cost",
            value = formState.materialsCost,
            onChange = { value -> viewModel.updateField { it.copy(materialsCost = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Labor Work force",
            value = formState.laborWorkForce,
            onChange = { value -> viewModel.updateField { it.copy(laborWorkForce = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Labor Quantity",
            value = formState.laborQuantity,
            onChange = { value -> viewModel.updateField { it.copy(laborQuantity = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Labor Cost",
            value = formState.laborCost,
            onChange = { value -> viewModel.updateField { it.copy(laborCost = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Total Cost (Php)",
            value = formState.totalCoast,
            onChange = { value -> viewModel.updateField { it.copy(totalCoast = value) } }
        )
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "6. Farm Information",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextFieldOnionStatus(
            context = context,
            label = "Sitio",
            value = formState.farmLocationSitio,
            onChange = { value -> viewModel.updateField { it.copy(farmLocationSitio = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Barangay",
            value = formState.farmLocatioBarangay,
            onChange = { value -> viewModel.updateField { it.copy(farmLocatioBarangay = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Municipality",
            value = formState.farmLocationMunicipality,
            onChange = { value -> viewModel.updateField { it.copy(farmLocationMunicipality = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "Province",
            value = formState.farmLocationProvince,
            onChange = { value -> viewModel.updateField { it.copy(farmLocationProvince = value) } }
        )
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Boundaries",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextFieldOnionStatus(
            context = context,
            label = "North",
            value = formState.north,
            onChange = { value -> viewModel.updateField { it.copy(north = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "South",
            value = formState.south,
            onChange = { value -> viewModel.updateField { it.copy(south = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "East",
            value = formState.east,
            onChange = { value -> viewModel.updateField { it.copy(east = value) } }
        )
        TextFieldOnionStatus(
            context = context,
            label = "West",
            value = formState.west,
            onChange = { value -> viewModel.updateField { it.copy(west = value) } }
        )

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
            ) {
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
            ) {
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
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                fontWeight = FontWeight.Bold
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
fun TextFieldOnionStatus(
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
fun CheckStatusField(label: String, value: Boolean, onChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.
    width(115.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Checkbox(checked = value, onCheckedChange = onChange)
        Text(label, modifier = Modifier.padding(start = 1.dp))
    }
}