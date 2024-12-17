package com.example.agriguard.modules.main.farmer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.agriguard.modules.main.MainNav
import com.example.agriguard.modules.main.farmer.enum.FarmerStatus
import com.example.agriguard.modules.main.farmer.viewmodel.FarmersViewModel
import com.example.agriguard.modules.main.user.model.dto.AddressDto
import com.example.agriguard.modules.main.user.model.dto.UserDto

@Composable
fun AddressesUI(
    navController: NavController,
    currentUser: UserDto
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(55.dp))
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Address",
                fontFamily = FontFamily.SansSerif,
                fontSize = 23.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = Color(0xFF136204)
            )
            Spacer(modifier = Modifier.height(10.dp))
            ListAddress(
                navController,
                currentUser
            )

        }
    }
}

@Composable
fun ListAddress(
    navController: NavController,
    currentUser: UserDto
) {
    val farmersViewModel: FarmersViewModel = hiltViewModel()
    val addresses = farmersViewModel.fetchAddresses()

    val filteredAddresses = if (currentUser.isTechnician) {
        addresses.filter { it.name == currentUser.address }
    } else {
        addresses
    }
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 45.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(filteredAddresses) { address ->
            ListButton(
                addressDto = address,
                onClick = {
                    val farmerStatus = FarmerStatus.FARMER

                    val farmersRoute = MainNav.Farmers(
                        status = farmerStatus.name,
                        addressId = address.id,
                    )

                    navController.navigate(farmersRoute)
                }
            )
        }
    }
}

@Composable
private fun ListButton(
    addressDto: AddressDto,
    onClick: () -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(
//            containerColor =  Color.White,
//            contentColor = Color(0xFF136204)
            containerColor =  Color(0xFF136204),
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(58.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        border = BorderStroke(2.dp, Color(0xFFFFFFFF))
    ) {
        Text(
            text = addressDto.name,
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
        )
    }
}