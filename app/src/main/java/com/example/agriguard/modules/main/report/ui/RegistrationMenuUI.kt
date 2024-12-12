package com.example.agriguard.modules.main.report.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R
import com.example.agriguard.modules.main.MainNav

@Preview(showSystemUi = true)
@Composable
fun RegistrationMenuPreview(){
    RegistrationMenuUI(
        rememberNavController()
    )
}

@Composable
fun RegistrationMenuUI(navController: NavController){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.agrilogo),
            contentDescription = "Login Image",
            modifier = Modifier.size(210.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Menu(navController = navController)
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun Menu(navController: NavController) {
    Box(
        modifier = Modifier
            .border(
                    BorderStroke(2.dp, Color(0xFF136204)),
                RoundedCornerShape(14.dp),
            )
            .background(
//                Color(0xFFf2fcf0),
                Color(0xFFf6faf5),
                shape = RoundedCornerShape(14.dp)
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val menuItems = getMenuItems(navController)
            items(menuItems) { menuItem ->
                Spacer(modifier = Modifier.padding(top = 4.dp))

                MenuButton(text = menuItem.text, onClick = menuItem.action)

                Spacer(modifier = Modifier.padding(bottom = 10.dp))
            }
        }
    }
}

fun getMenuItems(navController: NavController): List<AppMenuItem> {
    return listOf(
        AppMenuItem(text = "Indemnity") {
            navController.navigate(MainNav.IndemnityCreate)
        },
        AppMenuItem(text = "Rice Insurance") {
            navController.navigate(MainNav.RiceInsuranceForm)
        },
        AppMenuItem(text = "Onion Insurance") {
            navController.navigate(MainNav.OnionInsuranceForm)
        },

    )
}

@Composable
private fun MenuButton(text: String, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor =  Color.White,
            contentColor = Color(0xFF136204)
        ),
        modifier = Modifier
            .width(280.dp)
            .height(63.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        border = BorderStroke(2.dp, Color(0xFF136204))
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}

data class AppMenuItem(val text: String, val action: () -> Unit)