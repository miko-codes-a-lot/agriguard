package com.example.agriguard.modules.main.farmer

import android.content.pm.PackageManager
import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R
import com.example.agriguard.modules.main.MainNav

@Preview(showSystemUi = true)
@Composable
fun ComplaintFormPreview(){
    ComplaintFormUI(
        rememberNavController()
    )
}

@Composable
fun ComplaintFormUI(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 0.dp, vertical = 4.dp)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFF136204),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Complain Form",
                    fontSize = 25.sp,
                    color = Color(0xFF136204),
                    fontWeight = FontWeight.W800,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Image",
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        UploadImageUI()
        DropDownCategory()
        Damage()
        Maintenance()
        ButtonSaveForm(navController)
    }

}

@Composable
fun DropDownCategory(

) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Rice") }
    val listOfCategory = listOf("Rice", "Onion")
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .width(130.dp)
                .padding(10.dp)
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedCategory,
                modifier = Modifier
                    .padding(start = 17.dp),
                fontSize = 25.sp,
                color = Color(0xFF136204),
                fontWeight = FontWeight.W800,
                fontFamily = FontFamily.SansSerif,
            )
            Icon(
                painter = if (expanded) painterResource(id = R.drawable.ic_arrow_up) else painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = "Dropdown Icon",
                tint = Color(0xFF136204),
                modifier = Modifier.size(24.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(130.dp)
                .background(color = Color.White)
                .heightIn(max = 200.dp)
        ) {
            listOfCategory.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            color = Color(0xFF136204),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(130.dp)
                        )
                    },onClick = {
                        selectedCategory = item
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Damage() {
    var text by remember { mutableStateOf("") }
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .heightIn(max = 130.dp)
            .border(1.dp, Color(0xFF136204), RoundedCornerShape(8.dp))
    ) {
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .fillMaxSize(),
            placeholder = {
                Text(
                    text = "Cause of Damage...",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Gray,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color.White,
                focusedTextColor = Color(0xFF136204),
                unfocusedBorderColor = Color.White
            )
        )

    }

}

@Composable
fun Maintenance() {
    var text by remember { mutableStateOf("") }
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        modifier = Modifier
            .padding(top = 10.dp)
            .background(Color.White)
            .fillMaxWidth()
            .heightIn(max = 130.dp)
            .border(1.dp, Color(0xFF136204), RoundedCornerShape(8.dp))
    ) {
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .fillMaxSize(),
            placeholder = {
                Text(
                    text = "Treatment...",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Gray,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color.White,
                focusedTextColor = Color(0xFF136204),
                unfocusedBorderColor = Color.White
            )
        )

    }
}

@Composable
fun ButtonSaveForm(
    navController: NavController
) {
    Button(
        onClick = {
        },
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(58.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF136204),
            contentColor = Color(0xFFFFFFFF)
        )
    ){
        Text("Submit",
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.White
        )
    }
}