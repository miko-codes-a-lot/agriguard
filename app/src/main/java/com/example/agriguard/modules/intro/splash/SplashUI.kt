package com.example.agriguard.modules.intro.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R
import com.example.agriguard.modules.intro.IntroNav
import com.example.agriguard.modules.main.MainNav

@Preview(showSystemUi = true)

@Composable
fun SplashUIPreview() {
    SplashUI(
        rememberNavController()
    )
}

@Composable
fun SplashUI (navController: NavController) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.agrilogo), contentDescription = "Login Image",
            modifier = Modifier.size(400.dp))

        Spacer(modifier = Modifier.height(85.dp))

        Button(
            onClick = { navController.navigate(IntroNav.LogIn) },
            modifier = Modifier
                .padding(bottom = 50.dp)
                .height(50.dp)
                .width(125.dp),
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF136204)),
            shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
            enabled = true,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp
            )
        ){
            Text(
                text = "Get Started",
                color = Color.White
            )
        }
    }
}