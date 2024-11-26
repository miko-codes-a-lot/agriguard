package com.example.agriguard.modules.main.farmer

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.agriguard.R
import com.example.agriguard.modules.main.MainNav

@Preview(showSystemUi = true)
@Composable
fun FarmersPrev() {
    FarmersListUI(
        rememberNavController()
    )
}

@Composable
fun FarmersListUI(navController: NavController) {
    val listFarmers = listOf(
        "Mark Smith Perez","Aron Gonzales","Juan Dela Cruz","Pedro Santos","Mike Santos",
        "Ronnie Lopez","Jhon Paul Perez","Ryan Martinez","Michael Jackson","John Cena",
        "Mark John Paul","Aron Gonzales","Juan Dela Cruz","Pedro Santos","Mike Santos"

    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Scaffold(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
                .fillMaxSize(),
            floatingActionButton = {
                FloatingIconUserList(navController = navController)
            }

        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
                    .padding(padding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchIcon()
                Spacer(modifier = Modifier.padding(bottom = 3.dp))
                LazyColumn(
                    modifier = Modifier
                        .background(Color.White)
//                        .height(600.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(listFarmers) { farmer ->
                        Farmers(farmerName = farmer,navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Farmers(
    farmerName: String,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(MainNav.FarmersPreview)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            FarmersImageContainer()
            Text(
                text = farmerName,
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f),
                color = Color.Black
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFF136204)
        )
    }
}

@Composable
fun FarmersImageContainer(imageUri: Uri? = null) {
    Box(
        Modifier
            .height(45.dp)
    ){
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF136204))
                .border(3.dp, Color(0xFF136204), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "Default placeholder",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun SearchIcon() {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        onValueChange = {  },
        leadingIcon = {
            IconButton(onClick = { } ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Clear Search",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Black,
            focusedBorderColor = Color.Black,
            disabledBorderColor = Color.Gray,
            errorBorderColor = Color.Red,
            cursorColor = Color.Black
        ),
        placeholder = {
            Text(text = "Search...", color = Color.Gray)
        }
    )
}

@Composable
fun FloatingIconUserList(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = { navController.navigate(MainNav.CreateUser) },
            containerColor = Color(0xFF136204),
            contentColor = Color(0xFFFFFFFF),
            shape = CircleShape,
            modifier = Modifier
                .size(75.dp)
                .offset(y = (20).dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}