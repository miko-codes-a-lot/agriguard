package com.example.agriguard.modules.main.farmer

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.agriguard.R
import com.example.agriguard.modules.main.MainNav
import com.example.agriguard.modules.main.chat.ui.PlaceholderImage
import com.example.agriguard.modules.main.chat.ui.decodeBase64ToBitmap
import com.example.agriguard.modules.main.farmer.enum.FarmerStatus
import com.example.agriguard.modules.main.farmer.viewmodel.FarmersViewModel
import com.example.agriguard.modules.main.setting.resizeBitmap
import com.example.agriguard.modules.main.user.model.dto.AddressDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.shared.ext.toObjectId
import kotlinx.coroutines.delay

@Composable
fun FarmersUI(
    navController: NavController,
    currentId: UserDto,
    addressDto: AddressDto?,
    status: String,
) {
    val farmersViewModel: FarmersViewModel = hiltViewModel()
    var debouncedQuery by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    LaunchedEffect(searchQuery) {
        delay(500L)
        debouncedQuery = searchQuery
    }

    val farmers = when {
        status == FarmerStatus.FARMER.name -> {
            farmersViewModel.fetchUsers(currentId.id.toObjectId(), currentId.isAdmin, addressDto?.name)
        }
        else -> {
            farmersViewModel.fetchUsers(currentId.id.toObjectId(), currentId.isAdmin, addressDto?.name)
        }
    }

    val filteredFarmers = farmers.filter {
        it.firstName.contains(debouncedQuery, ignoreCase = true) ||
        it.middleName?.contains(debouncedQuery, ignoreCase = true) ?: false ||
        it.lastName.contains(debouncedQuery, ignoreCase = true)
    }
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
                if (addressDto != null) {
                    FloatingIconUserList(
                        navController = navController,
                            addressDto
                    )
                }
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
                SearchIcon(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { searchQuery = it },
                )
                Spacer(modifier = Modifier.padding(bottom = 3.dp))
                LazyColumn(
                    modifier = Modifier
                        .background(Color.White)
//                        .height(600.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(filteredFarmers) { farmer ->
                        FarmersListContainer(userDto = farmer,navController)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchIcon(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchQuery,
        onValueChange = { onSearchQueryChanged(it) },
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
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChanged("") }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Clear Search",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedBorderColor = Color.Black,
            disabledBorderColor = Color.Gray,
            errorBorderColor = Color.Red,
            cursorColor = Color.Black
        ),
        placeholder = {
            Text(
                text = "Search...",
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        }
    )
}

@Composable
fun FarmersListContainer(
    userDto: UserDto,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate(MainNav.FarmersPreview(userDto.id!!))
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FarmersImageContainer(userDto.userProfile)
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${userDto.firstName} ${userDto.middleName} ${userDto.lastName}",
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
fun FarmersImageContainer(imageBase64: String? = null) {
    Box(
        Modifier
            .height(55.dp)
    ){
        Box(
            modifier = Modifier
                .size(51.dp)
                .clip(CircleShape)
                .background(Color(0xFF136204))
                .border(1.dp, Color(0xFF136204), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (imageBase64 != null && imageBase64.isNotBlank()) {
                val resizedBitmap = remember(imageBase64) {
                    decodeBase64ToBitmap(imageBase64)?.let { bitmap ->
                        resizeBitmap(bitmap, maxWidth = 500, maxHeight = 500)
                    }
                }

                if (resizedBitmap != null) {
                    Image(
                        bitmap = resizedBitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(51.dp)
                            .clip(CircleShape)
                    )
                } else {
                    PlaceholderImage()
                }
            } else {
                PlaceholderImage()
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
    navController: NavController,
    addressDto: AddressDto,
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = { navController.navigate(MainNav.CreateUser(addressDto.id)) },
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