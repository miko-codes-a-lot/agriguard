package com.example.agriguard.modules.main.user.ui

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.service.UserService
import com.example.agriguard.modules.main.user.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateUploadId (
    currentUser: UserDto,
    userService: UserService = hiltViewModel<UserViewModel>().userService
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var selectedProfileImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Upload ID",
            fontSize = 17.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(30.dp))
        UploadIdUI(
            onImageSelected = { newUri ->
                selectedProfileImageUri = newUri
                coroutineScope.launch {
                    val byteArray = getBytesFromUri(context, newUri)
                    Log.d("getBytesFromUri", "ByteArray: ${byteArray?.size ?: "null"}")
                    if (byteArray != null) {
                        userService.saveValidId(currentUser.id!!, byteArray)
                    }
                }
            },
            currentUserId = currentUser.id!!,
            userService = userService
        )
    }
}