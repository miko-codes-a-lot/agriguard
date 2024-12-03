package com.example.agriguard.modules.main.user.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R
import com.example.agriguard.modules.main.MainNav
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.viewmodel.UserViewModel

@Preview(showSystemUi = true)
@Composable
fun UsersListPreview() {
    UsersUI(
        rememberNavController(),
    )
}

@Composable
fun UsersUI(
    navController: NavController,
) {
    val userViewModel: UserViewModel = hiltViewModel()
    var searchQuery by remember { mutableStateOf("") }
    val users by produceState<List<UserDto>>(emptyList(), userViewModel) {
        value = userViewModel.fetchUsers()
    }
    val filteredFarmers = users.filter {
        it.firstName.contains(searchQuery, ignoreCase = true) ||
        it.middleName!!.contains(searchQuery, ignoreCase = true) ||
        it.lastName.contains(searchQuery, ignoreCase = true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF)),
            floatingActionButton = {
                    FloatParentFloatingIcon(
                        navController,
                    )
            }
        ) {padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UsersSearchIcon(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { searchQuery = it },
                )
                Spacer(modifier = Modifier.padding(bottom = 3.dp))
                LazyColumn(
                    modifier = Modifier
                        .background(Color.White)
//                        .height(588.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(filteredFarmers) { user ->
                        UsersSingleLine(userDto = user,navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun UsersSearchIcon(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
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
fun UsersSingleLine(
    userDto: UserDto,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    navController.navigate(MainNav.EditUser(userId = userDto.id!!))
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.users),
                contentDescription = "Person Icon",
                tint = Color(0xFF136204),
                modifier = Modifier.size(26.dp)
            )
            Text(
                text = userDto.email ?: "${userDto.firstName} ${userDto.lastName}",
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFF136204)
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FloatParentFloatingIcon(
    navController: NavController,
    ) {
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End
    ) {

        FloatingActionButton(
            onClick = { navController.navigate(MainNav.CreateUser(addressId = null)) },
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
