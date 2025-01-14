package com.example.agriguard.modules.main.farmer

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agriguard.modules.main.MainNav
import com.example.agriguard.modules.main.complain.ui.decodeBase64ToBitmap
import com.example.agriguard.modules.main.user.model.dto.UserDto
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FarmersPreviewUI(
    navController: NavController,
    currentUser: UserDto,
    user: UserDto
) {
    val scrollState = rememberScrollState()
    val imageBitmap = remember(user.validId) {
        user.validId?.let { decodeBase64ToBitmap(it, maxWidth = 500, maxHeight = 500) }
    }
    val statesValue = remember(user) {
        listOf(
            "FirstName" to user.firstName,
            "MiddleName" to (user.middleName ?: ""),
            "LastName" to user.lastName,
            "Address" to (user.address ?: ""),
            "Mobile Number" to (user.mobileNumber ?: ""),
            "Date Of Birth" to farmerDateFormat(user.dateOfBirth),
            "Email" to user.email,
        ).associate { (label, value) -> label to mutableStateOf(value) }
    }
    Scaffold(
        floatingActionButton = {
            if (currentUser.isTechnician) {
                FloatingIconPreview(navController, user)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .background(Color.White)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Profile",
                fontFamily = FontFamily.Serif,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(bottom = 3.dp, top = 7.dp)
            )
            FarmersData(stateValues = statesValue)

            if (imageBitmap != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RectangleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = "Valid ID Preview",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 50.dp))
        }
    }
}

@Composable
fun FarmersData(
    stateValues: Map<String, MutableState<String>>,
) {
    Column {
        stateValues.forEach { (label, state) ->
            TextFieldContain(
                textLabel = label,
                textState = state
            )
        }
    }
}

@Composable
fun TextFieldContain(
    textLabel: String,
    textState: MutableState<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = textLabel,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 18.sp,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(" : ", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = textState.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp),
                        fontSize = 17.sp,
                        fontFamily = FontFamily.SansSerif,
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun FloatingIconPreview(
    navController: NavController,
    user: UserDto
) {
    Column(
        modifier = Modifier
            .background(Color.White),
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate(MainNav.EditUser(user.id!!))
            },
            containerColor = Color(0xFF136204),
            contentColor = Color(0xFFFFFFFF),
            shape = CircleShape,
            modifier = Modifier
                .size(72.dp)
                .offset(x = (-7).dp, y = (5).dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Navigate",
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}

fun farmerDateFormat(dateString: String): String {
    return try {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dates = isoFormat.parse(dateString)
        displayFormat.format(dates)
    } catch (e: Exception) {
        "Select Date"
    }
}
