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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.runtime.remember
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
import com.example.agriguard.modules.shared.ui.DatePickerField
import com.example.agriguard.modules.shared.ui.TextField
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Composable
fun OnionInsuranceFormUI(
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
        Row(
            modifier = Modifier
                .padding(top = 8.dp,bottom = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
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
            onChange = { value -> viewModel.updateField { it.copy(ipTribe = value) } }
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
            onChange = {  }
        )








        TextField(
            "IP Tribe",
            formState.ipTribe
        ) { value -> viewModel.updateField { it.copy(ipTribe = value) } }
        TextField("First Name", currentUser.firstName) { }
        TextField("Middle Name", currentUser.middleName) { }
        TextField("Last Name", currentUser.lastName) { }
        TextField("Date Of Birth", currentUser.dateOfBirth) { }
        TextField("Mobile Number", currentUser.mobileNumber) { }
        TextField("Address", currentUser.address) { }
//        TextField("If married, Name Of Spouse", currentUser.nameOfSpouse) { }
        CheckBoxField(
            "Male",
            formState.male
        ) { value -> viewModel.updateField { it.copy(male = value) } }
        CheckBoxField(
            "Female",
            formState.female
        ) { value -> viewModel.updateField { it.copy(female = value) } }
        CheckBoxField(
            "Single",
            formState.single
        ) { value -> viewModel.updateField { it.copy(single = value) } }
        CheckBoxField("Married", formState.married) { value ->
            viewModel.updateField {
                it.copy(
                    married = value
                )
            }
        }
        CheckBoxField(
            "Widow",
            formState.widow
        ) { value -> viewModel.updateField { it.copy(widow = value) } }
        Text(text = "Legal Beneficiaries")
        TextField("Name", formState.nameOfBeneficiary) { value ->
            viewModel.updateField {
                it.copy(
                    nameOfBeneficiary = value
                )
            }
        }
        TextField("Age", formState.ageOfBeneficiary) { value ->
            viewModel.updateField {
                it.copy(
                    ageOfBeneficiary = value
                )
            }
        }
        TextField(
            "RelationShip",
            formState.relationshipOfBeneficiary
        ) { value -> viewModel.updateField { it.copy(relationshipOfBeneficiary = value) } }
        Text(text = "Name Of Plantation")
        TextField(
            "Farm Location",
            formState.farmLocation
        ) { value -> viewModel.updateField { it.copy(farmLocation = value) } }
        TextField(
            "Area",
            formState.area
        ) { value -> viewModel.updateField { it.copy(area = value) } }
        TextField("Soil Type", formState.soilType) { value ->
            viewModel.updateField {
                it.copy(
                    soilType = value
                )
            }
        }
        TextField(
            "Soil Ph",
            formState.soilPh
        ) { value -> viewModel.updateField { it.copy(soilPh = value) } }
        TextField("Topography", formState.topography) { value ->
            viewModel.updateField {
                it.copy(
                    topography = value
                )
            }
        }
        Text(text = "Variety Of Planted")
        TextField(
            "Variety",
            formState.variety
        ) { value -> viewModel.updateField { it.copy(variety = value) } }
        DatePickerField(
            context = context,
            label = "Date Of Planting",
            value = formState.dateOfPlanting,
            onChange = { value -> viewModel.updateField { it.copy(dateOfPlanting = value) } }
        )
        DatePickerField(
            context = context,
            label = "Estd Date Of Harvest",
            value = formState.estdDateOfHarvest,
            onChange = { value -> viewModel.updateField { it.copy(estdDateOfHarvest = value) } }
        )
        TextField("Age Group", formState.ageGroup) { value ->
            viewModel.updateField {
                it.copy(
                    ageGroup = value
                )
            }
        }
        TextField("No. Of Hills", formState.noOfHills) { value ->
            viewModel.updateField {
                it.copy(
                    noOfHills = value
                )
            }
        }
        TextField(
            "Type Of Irrigation",
            formState.typeOfIrrigation
        ) { value -> viewModel.updateField { it.copy(typeOfIrrigation = value) } }
        TextField(
            "Average Yield",
            formState.averageYield
        ) { value -> viewModel.updateField { it.copy(averageYield = value) } }
        Text(text = "Cost Of Production Inputs (CPI)")
        TextField(
            "land Preparation",
            formState.landPreparation
        ) { value -> viewModel.updateField { it.copy(landPreparation = value) } }
        TextField(
            "Materials Item",
            formState.materialsItem
        ) { value -> viewModel.updateField { it.copy(materialsItem = value) } }
        TextField(
            "Materials Quantity",
            formState.materialsQuantity
        ) { value -> viewModel.updateField { it.copy(materialsQuantity = value) } }
        TextField(
            "Materials Cost",
            formState.materialsCost
        ) { value -> viewModel.updateField { it.copy(materialsCost = value) } }
        TextField(
            "Labor Work Force",
            formState.laborWorkForce
        ) { value -> viewModel.updateField { it.copy(laborWorkForce = value) } }
        TextField(
            "Labor Quantity",
            formState.laborQuantity
        ) { value -> viewModel.updateField { it.copy(laborQuantity = value) } }
        TextField("Labor Cost", formState.laborCost) { value ->
            viewModel.updateField {
                it.copy(
                    laborCost = value
                )
            }
        }
        TextField(
            "Total Cost (PHP)",
            formState.totalCoast
        ) { value -> viewModel.updateField { it.copy(totalCoast = value) } }
        Text(text = "Farm Location/LSP")
        TextField("Sitio", formState.farmLocationSitio) { value ->
            viewModel.updateField {
                it.copy(
                    farmLocationSitio = value
                )
            }
        }
        TextField(
            "Barangay",
            formState.farmLocatioBarangay
        ) { value -> viewModel.updateField { it.copy(farmLocatioBarangay = value) } }
        TextField(
            "Municipality",
            formState.farmLocationMunicipality
        ) { value -> viewModel.updateField { it.copy(farmLocationMunicipality = value) } }
        TextField(
            "Province",
            formState.farmLocationProvince
        ) { value -> viewModel.updateField { it.copy(farmLocationProvince = value) } }
        Text(text = "Boundaries")
        TextField(
            "North",
            formState.north
        ) { value -> viewModel.updateField { it.copy(north = value) } }
        TextField(
            "South",
            formState.south
        ) { value -> viewModel.updateField { it.copy(south = value) } }
        TextField(
            "East",
            formState.east
        ) { value -> viewModel.updateField { it.copy(east = value) } }
        TextField(
            "West",
            formState.west
        ) { value -> viewModel.updateField { it.copy(west = value) } }

        Button(
            onClick = {
                val updatedFormState = formState.copy(userId = currentUser.id!!)
                    onSubmit(updatedFormState)
//                    navController.popBackStack()
                },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
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
            .widthIn(min = 220.dp, max = 260.dp)
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
                value = value.toString(),
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