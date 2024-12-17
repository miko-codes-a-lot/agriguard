package com.example.agriguard.modules.intro.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R
import com.example.agriguard.modules.intro.login.model.dto.LoginDto
import com.example.agriguard.modules.intro.login.viewmodel.LoginViewModel
import com.example.agriguard.modules.intro.login.viewmodel.UserState
import com.example.agriguard.modules.main.MainNav
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.shared.ext.AlertLoginUI
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun LogInUIPreview() {
    LogInUI(navController = rememberNavController())
}

@Composable
fun LogInUI(
    userDto: UserDto? = null,
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFF136204)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF136204)),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "Welcome to login",
                fontSize = 25.sp,
                fontWeight = FontWeight.W600,
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 130.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 250.dp)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(Color.White)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val listOfLabel = listOf("Email","Password")

            val statesValue = remember {
                listOfLabel.associateWith {
                    mutableStateOf(
                        when (it) {
                            "Email" -> userDto?.email ?: ""
                            "Password" -> userDto?.password ?: ""
                            else -> ""
                        }
                    )
                }
            }
            ContainerLabelAndValue(statesValue = statesValue)

            ButtonLogin(
                navController = navController,
                statesValue = statesValue
            )

            ForgetPassword(
                navController = navController
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun ContainerLabelAndValue(
    statesValue: Map<String, MutableState<String>>
) {
    statesValue.forEach { (label, state) ->
        UserLog(
            loginLabel = label,
            loginValue = state.value,
            onValueChange = { newInputValue ->
                state.value = newInputValue
            },
        )
    }
}

@Composable
fun ButtonLogin(
    navController: NavController,
    statesValue: Map<String, MutableState<String>>
) {
    val vm = UserState.current
    val coroutineScope = rememberCoroutineScope()
    var showButton by remember { mutableStateOf(true) }

    val loginViewModel: LoginViewModel = hiltViewModel()
    val showAlert = rememberSaveable { mutableStateOf(false) }

    if (showAlert.value) {
        AlertLoginUI(
            onDismiss = { showAlert.value = false },
        )
    }

    if(showButton) {
        ElevatedButton(
            onClick = {
                val loginDto = LoginDto(
                    email = statesValue["Email"]?.value ?: "",
                    password = statesValue["Password"]?.value ?: "",
                )
                val isSuccess = loginViewModel.login(loginDto)

                if (isSuccess) {
                    showButton = false
                    coroutineScope.launch {
                        showButton = false
                        vm.signIn()
                        loginViewModel.loadSpecificDataByUser()

                        navController.navigate(MainNav.Menu) {
                            popUpTo(0)
                        }
                    }
                } else {
                    showAlert.value = true
                }
            },
            modifier = Modifier
                .width(340.dp)
                .height(55.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = Color(0xFF136204),
                contentColor = Color(0xFFFFFFFF)
            ),
            enabled = true,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 3.dp
            )
        ) {
            Text(
                text = "Login",
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
        }
    }else{
        CircularProgressIndicator(
            modifier = Modifier
                .padding(top = 15.dp)
                .size(40.dp),
            color = Color(0xFF136204),
        )
    }
}

@Composable
fun UserLog(
    loginLabel: String,
    loginValue: String,
    onValueChange: (String) -> Unit,
) {
    val isPasswordField = loginLabel == "Password"
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(340.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = loginValue,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(text = loginLabel,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xFF136204)
                )
            },
            textStyle = TextStyle(
                color = Color(0xFF136204),
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
            ),
            leadingIcon = {
                if (loginLabel.contains("Email")) {
                    Icon(
                        painter = painterResource(id = R.drawable.email), contentDescription = "Person",tint = Color(0xFF136204)
                    )
                } else {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color(0xFF136204))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 4f
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFF136204),
                        start = androidx.compose.ui.geometry.Offset(0f, y),
                        end = androidx.compose.ui.geometry.Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.Gray
            ),
            visualTransformation = if (isPasswordField && !isPasswordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                if (isPasswordField) {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (isPasswordVisible) R.drawable.visibilityon else R.drawable.visibility_off
                            ),
                            tint = Color(0xFF136204),
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            }
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ForgetPassword(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
    ){
        Row(
            modifier = Modifier
                .width(340.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Forgot Password? "
            )

            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .offset(x = (-10).dp )
                ) {
                Box {
                    Text(
                        text = "Click here",
                        color = Color.Red,
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(1.dp)
                            .background(Color.Red)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }

}

