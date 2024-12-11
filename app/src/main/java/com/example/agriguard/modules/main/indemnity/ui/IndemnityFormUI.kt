package com.example.agriguard.modules.main.indemnity.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agriguard.modules.main.indemnity.viewmodel.IndemnityViewModel
import com.example.agriguard.modules.main.user.model.dto.IndemnityDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.shared.ui.CheckBoxField
import com.example.agriguard.modules.shared.ui.DatePickerField
import com.example.agriguard.modules.shared.ui.TextField

@Composable
fun IndemnityFormUI(
    navController: NavController,
    currentUser: UserDto,
    viewModel: IndemnityViewModel,
    onSubmit: (IndemnityDto) -> Unit
) {
    val formState by viewModel.formState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(top = 50.dp))
        val context = LocalContext.current
        DatePickerField(
            context = context,
            label = "Date",
            value = formState.fillupdate,
            onChange = { value -> viewModel.updateField { it.copy(fillupdate = value) } }
        )
        TextField("First Name", currentUser.firstName) { }
        TextField("Middle Name", currentUser.middleName) { }
        TextField("Last Name", currentUser.lastName) { }
        TextField("Address", currentUser.address) {  }
        TextField("Mobile Number", currentUser.mobileNumber) {  }
        Text(text = "Program")
        CheckBoxField("Regular", formState.regular) { value -> viewModel.updateField { it.copy(regular = value) } }
        CheckBoxField("Punla", formState.punla) { value -> viewModel.updateField { it.copy(punla = value) } }
        CheckBoxField("Cooperate Rice Farming", formState.cooperativeRice) { value -> viewModel.updateField { it.copy(cooperativeRice = value) } }
        CheckBoxField("RSBSA", formState.rsbsa) { value -> viewModel.updateField { it.copy(rsbsa = value) } }
        CheckBoxField("Sikat", formState.sikat) { value -> viewModel.updateField { it.copy(sikat = value) } }
        CheckBoxField("APCP-C", formState.apcpc) { value -> viewModel.updateField { it.copy(apcpc = value) } }
        TextField("Other", formState.others) { value -> viewModel.updateField { it.copy(others = value) } }
        Text(text = "Damage Indicators")
        TextField("Cause Of Damage", formState.causeOfDamage) { value -> viewModel.updateField { it.copy(causeOfDamage = value) } }

        DatePickerField(
            context = context,
            label = "Date Of Loss",
            value = formState.dateOfLoss,
            onChange = { value -> viewModel.updateField { it.copy(dateOfLoss = value) } }
        )
        TextField("Age/Stage Of Cultivation", formState.ageCultivation) { value -> viewModel.updateField { it.copy(ageCultivation = value) } }
        TextField("Area Damaged", formState.areaDamaged) { value -> viewModel.updateField { it.copy(areaDamaged = value) } }
        TextField("Degree Of Damaged", formState.degreeOfDamage) { value -> viewModel.updateField { it.copy(degreeOfDamage = value) } }
        DatePickerField(
            context = context,
            label = "Expected Date Of Harvest",
            value = formState.expectedDateOfHarvest,
            onChange = { value -> viewModel.updateField { it.copy(expectedDateOfHarvest = value) } }
        )
        TextField("North", formState.north) { value -> viewModel.updateField { it.copy(north = value) } }
        TextField("South", formState.south) { value -> viewModel.updateField { it.copy(south = value) } }
        TextField("East", formState.east) { value -> viewModel.updateField { it.copy(east = value) } }
        TextField("West", formState.west) { value -> viewModel.updateField { it.copy(west = value) } }
        Text(text = "Kabuuang Gastos")
        TextField("Upa Sa Pag-Gawa Bilang", formState.upaSaGawaBilang) { value -> viewModel.updateField { it.copy(upaSaGawaBilang = value) } }
        TextField("Upa Sa Pag-Gawa Halaga", formState.upaSaGawaHalaga) { value -> viewModel.updateField { it.copy(upaSaGawaHalaga = value) } }
        TextField("Binhi Bilang", formState.binhiBilang) { value -> viewModel.updateField { it.copy(binhiBilang = value) } }
        TextField("Binhi Halaga", formState.binhiHalaga) { value -> viewModel.updateField { it.copy(binhiHalaga = value) } }
        TextField("Abono Bilang", formState.abonoBilang) { value -> viewModel.updateField { it.copy(abonoBilang = value) } }
        TextField("Abono Halaga", formState.abonoHalaga) { value -> viewModel.updateField { it.copy(abonoHalaga = value) } }
        TextField("Kemikal Bilang", formState.kemikalBilang) { value -> viewModel.updateField { it.copy(kemikalBilang = value) } }
        TextField("Kemikal Halaga", formState.kemikalHalaga) { value -> viewModel.updateField { it.copy(kemikalHalaga = value) } }
        TextField("Patubig Bilang", formState.patubigBilang) { value -> viewModel.updateField { it.copy(patubigBilang = value) } }
        TextField("Patubig Halaga", formState.patubigHalaga) { value -> viewModel.updateField { it.copy(patubigHalaga = value) } }
        TextField("Iba pa Bilang", formState.ibapaBilang) { value -> viewModel.updateField { it.copy(ibapaBilang = value) } }
        TextField("Iba pa Halaga", formState.ibapaHalaga) { value -> viewModel.updateField { it.copy(ibapaHalaga = value) } }
        TextField("Kabuuan Bilang", formState.kabuuanBilang) { value -> viewModel.updateField { it.copy(kabuuanBilang = value) } }
        TextField("Kabuuan Halaga", formState.kabuuanHalaga) { value -> viewModel.updateField { it.copy(kabuuanHalaga = value) } }

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
