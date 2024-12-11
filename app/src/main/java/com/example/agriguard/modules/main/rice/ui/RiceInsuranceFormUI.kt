package com.example.agriguard.modules.main.rice.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
    onSubmit: (RiceInsuranceDto) -> Unit
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
        Spacer(modifier = Modifier.padding(top = 50.dp))
        TextField("Insurance ID", formState.insuranceId) { value -> viewModel.updateField { it.copy(insuranceId = value) } }
        TextField("Lender", formState.lender) { value -> viewModel.updateField { it.copy(lender = value) } }
        DatePickerField(
            context = context,
            label = "Fill Up Date",
            value = formState.fillUpDate,
            onChange = { value -> viewModel.updateField { it.copy(fillUpDate = value) } }
        )
        CheckBoxField("New", formState.new) { value -> viewModel.updateField { it.copy(new = value) } }
        CheckBoxField("Renewal", formState.renewal) { value -> viewModel.updateField { it.copy(renewal = value) } }
        CheckBoxField("Self-Financed", formState.selfFinanced) { value -> viewModel.updateField { it.copy(selfFinanced = value) } }
        CheckBoxField("Borrowing", formState.borrowing) { value -> viewModel.updateField { it.copy(borrowing = value) } }

        TextField("IP Tribe", formState.ipTribe) { value -> viewModel.updateField { it.copy(ipTribe = value) } }
        TextField("Street", formState.street) { value -> viewModel.updateField { it.copy(street = value) } }
        TextField("Barangay", formState.barangay) { value -> viewModel.updateField { it.copy(barangay = value) } }
        TextField("Municipality", formState.municipality) { value -> viewModel.updateField { it.copy(municipality = value) } }
        TextField("Province", formState.province) { value -> viewModel.updateField { it.copy(province = value) } }

        TextField("Bank Name", formState.bankName) { value -> viewModel.updateField { it.copy(bankName = value) } }
        TextField("Bank Address", formState.bankAddress) { value -> viewModel.updateField { it.copy(bankAddress = value) } }

        CheckBoxField("Male", formState.male) { value -> viewModel.updateField { it.copy(male = value) } }
        CheckBoxField("Female", formState.female) { value -> viewModel.updateField { it.copy(female = value) } }
        CheckBoxField("Single", formState.single) { value -> viewModel.updateField { it.copy(single = value) } }
        CheckBoxField("Married", formState.married) { value -> viewModel.updateField { it.copy(married = value) } }
        CheckBoxField("Widow", formState.widow) { value -> viewModel.updateField { it.copy(widow = value) } }

        TextField("Name of Beneficiary", formState.nameOfBeneficiary) { value -> viewModel.updateField { it.copy(nameOfBeneficiary = value) } }
        TextField("Age", formState.age) { value -> viewModel.updateField { it.copy(age = value) } }
        TextField("Relationship", formState.relationship) { value -> viewModel.updateField { it.copy(relationship = value) } }

        TextField("Sitio", formState.sitio) { value -> viewModel.updateField { it.copy(sitio = value) } }
        TextField("Farm Location Barangay", formState.farmLocationBarangay) { value -> viewModel.updateField { it.copy(farmLocationBarangay = value) } }
        TextField("Farm Location Municipality", formState.farmLocationMunicipality) { value -> viewModel.updateField { it.copy(farmLocationMunicipality = value) } }
        TextField("Farm Location Province", formState.farmLocationProvince) { value -> viewModel.updateField { it.copy(farmLocationProvince = value) } }

        TextField("North", formState.north) { value -> viewModel.updateField { it.copy(north = value) } }
        TextField("South", formState.south) { value -> viewModel.updateField { it.copy(south = value) } }
        TextField("East", formState.east) { value -> viewModel.updateField { it.copy(east = value) } }
        TextField("West", formState.west) { value -> viewModel.updateField { it.copy(west = value) } }

        TextField("Variety", formState.variety) { value -> viewModel.updateField { it.copy(variety = value) } }
        TextField("Plating Method", formState.platingMethod) { value -> viewModel.updateField { it.copy(platingMethod = value) } }


        DatePickerField(
            context = context,
            label = "Date of Sowing",
            value = formState.dateOfSowing,
            onChange = { value -> viewModel.updateField { it.copy(dateOfSowing = value) } }
        )
        DatePickerField(
            context = context,
            label = "Date of Planting",
            value = formState.dateOfPlanting,
            onChange = { value -> viewModel.updateField { it.copy(dateOfPlanting = value) } }
        )
        DatePickerField(
            context = context,
            label = "Date of Harvest",
            value = formState.dateOfHarvest,
            onChange = { value -> viewModel.updateField { it.copy(dateOfHarvest = value) } }
        )

        TextField("Land of Category", formState.landOfCategory) { value -> viewModel.updateField { it.copy(landOfCategory = value) } }
        TextField("Soil Types", formState.soilTypes) { value -> viewModel.updateField { it.copy(soilTypes = value) } }
        TextField("Topography", formState.topography) { value -> viewModel.updateField { it.copy(topography = value) } }
        TextField("Source of Irrigations", formState.sourceOfIrrigations) { value -> viewModel.updateField { it.copy(sourceOfIrrigations = value) } }
        TextField("Tenurial Status", formState.tenurialStatus) { value -> viewModel.updateField { it.copy(tenurialStatus = value) } }

        CheckBoxField("Rice", formState.rice) { value -> viewModel.updateField { it.copy(rice = value) } }
        CheckBoxField("Multi-risk", formState.multiRisk) { value -> viewModel.updateField { it.copy(multiRisk = value) } }
        CheckBoxField("Natural", formState.natural) { value -> viewModel.updateField { it.copy(natural = value) } }

        TextField("Amount of Cover", formState.amountOfCover) { value -> viewModel.updateField { it.copy(amountOfCover = value) } }
        TextField("Premium", formState.premium) { value -> viewModel.updateField { it.copy(premium = value) } }
        TextField("CLTI/ADSS", formState.cltiAdss) { value -> viewModel.updateField { it.copy(cltiAdss = value) } }
        TextField("Sum Insured", formState.sumInsured) { value -> viewModel.updateField { it.copy(sumInsured = value) } }

        CheckBoxField("Wet", formState.wet) { value -> viewModel.updateField { it.copy(wet = value) } }
        CheckBoxField("Dry", formState.dry) { value -> viewModel.updateField { it.copy(dry = value) } }

        TextField("CIC No", formState.cicNo) { value -> viewModel.updateField { it.copy(cicNo = value) } }
        DatePickerField(
            context = context,
            label = "Date CIC Issued",
            value = formState.dateCicIssued,
            onChange = { value -> viewModel.updateField { it.copy(dateCicIssued = value) } }
        )
        TextField("COC No", formState.cocNo) { value -> viewModel.updateField { it.copy(cocNo = value) } }
        DatePickerField(
            context = context,
            label = "Date COC Issued",
            value = formState.dateCocIssued,
            onChange = { value -> viewModel.updateField { it.copy(dateCocIssued = value) } }
        )
        TextField("Period of Cover", formState.periodOfCover) { value -> viewModel.updateField { it.copy(periodOfCover = value) } }
        TextField("From", formState.from) { value -> viewModel.updateField { it.copy(from = value) } }
        TextField("To", formState.to) { value -> viewModel.updateField { it.copy(to = value) } }

        Button(
            onClick = {
                val updatedFormState = formState.copy(userId = currentUser.id!!)
                onSubmit(updatedFormState)
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.padding(bottom = 50.dp))
    }
}