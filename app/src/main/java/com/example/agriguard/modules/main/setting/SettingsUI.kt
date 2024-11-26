package com.example.agriguard.modules.main.setting

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R
import com.example.agriguard.modules.intro.IntroNav
import com.example.agriguard.modules.main.MainNav

@Preview(showSystemUi = true)
@Composable
fun SettingsPreview() {
    SettingsUI(rememberNavController())
}

@Composable
fun SettingsUI(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Button(
                onClick = {
                    navController.navigate(IntroNav.LogIn) {
                        popUpTo(IntroNav.LogIn) { inclusive = true }
                    }
//                coroutineScope.launch {
//                    showButton = false
//                    vm.signOut()
//                    navController.navigate(IntroNav.Login) {
//                        popUpTo(navController.graph.startDestinationId) {
//                            inclusive = true
//                        }
//                        launchSingleTop = true
//                    }
//                }
                },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF136204)
                )
            ){
                Icon(
                    painter = painterResource(id = R.drawable.exit),
                    contentDescription = "Logout",
                    tint = Color.Red,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        Profile(navController = navController)
    }
}


@Composable
fun Profile(
    navController: NavController,
){
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var selectedProfileImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserImageUI(
//      STAND BY CODE
//            onImageSelected = { newUri ->
//                selectedProfileImageUri = newUri
//                coroutineScope.launch {
//                    val byteArray = getBytesFromUri(context, newUri)
//                    Log.d("getBytesFromUri", "ByteArray: ${byteArray?.size ?: "null"}")
//                    if (byteArray != null) {
//                        userService.saveProfilePicture(currentUser.id!!, byteArray)
//                    }
//                }
//            },
//            currentUserId = currentUser.id!!,
//            userService = userService
        )

        Spacer(modifier = Modifier.padding(vertical = 15.dp))

        UserDetails(navController)

        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        Setting(navController)
    }
}

@Composable
fun UserDetails(navController: NavController) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Juan Dela Cruz",
            fontSize = 17.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color(0xFF136204),
            modifier = Modifier.padding(horizontal = 3.dp)
        )
        IconButton(
            onClick = {
//                navController.navigate("${MainNav.EditSettings}/fullName")
            },
            modifier = Modifier
                .size(30.dp)
                .padding(bottom = 3.dp)
                .clip(CircleShape),
            colors = IconButtonDefaults.iconButtonColors(Color(0xFFFFFFFF)),
        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.editicon),
//                contentDescription = "Edit Details",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(5.dp),
//                tint = Color(0xFF6650a4)
//            )
        }
    }
}

@Composable
fun Setting(navController: NavController){
    val settingsMenu = listOf(
        SettingItem(text = "Email"){
//            navController.navigate("${MainNav.EditSettings}/email")
        },
        SettingItem(text = "Mobile Number"){
//            navController.navigate("${MainNav.EditSettings}/mobileNumber")
        },
        SettingItem(text = "Password"){
//            navController.navigate("${MainNav.EditSettings}/password")
        }
    )
    Column {
        settingsMenu.forEach { menuSettings ->
            Spacer(modifier = Modifier.height(10.dp))
            SettingButton(text = menuSettings.text, onClick = menuSettings.action)
        }
    }
}

@Composable
fun SettingButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick,
        modifier = Modifier
            .width(290.dp)
            .height(70.dp)
            .padding(5.dp)
            .border(2.dp, color = Color(0xFF136204), shape = RoundedCornerShape(5.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color(0xFF136204)
        )
    ) {
        Row(
            modifier = Modifier
        ){
            Text(text = text, fontSize = 16.sp, fontFamily = FontFamily.SansSerif)
            Spacer(modifier = Modifier.weight(0.1f))
//            Icon(painter = painterResource(id = R.drawable.editicon)
//                ,contentDescription = null,
//                modifier = Modifier.size(20.dp)
//            )
        }
    }
}
data class SettingItem(val text: String, val action: () -> Unit)