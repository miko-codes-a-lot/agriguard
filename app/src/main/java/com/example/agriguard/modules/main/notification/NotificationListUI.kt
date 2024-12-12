package com.example.agriguard.modules.main.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agriguard.R
import com.example.agriguard.modules.main.notify.model.dto.NotifyDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.shared.ui.NotificationReportDialog

@Composable
fun NotificationListUI(
    navController: NavController,
    notifyList: List<NotifyDto>,
    currentUser: UserDto
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 35.dp))
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
                    text = "Notification Report List",
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
        NotificationList(
            navController = navController,
            notifyList = notifyList,
            currentUser = currentUser
        )
    }
}

@Composable
fun NotificationList(
    navController: NavController,
    notifyList: List<NotifyDto>,
    currentUser: UserDto
) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(notifyList) { notify ->
            NotificationButton(notifyDto = notify, navController = navController, currentUser = currentUser)
        }
    }
}

@Composable
private fun NotificationButton(
    notifyDto: NotifyDto,
    navController: NavController,
    currentUser : UserDto
) {
    var isReportDialogVisible by rememberSaveable { mutableStateOf(false) }

    ElevatedButton(
        onClick = {
            if(currentUser.isFarmers) {
                isReportDialogVisible = true
            }else{
                navController
            }
        },
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFFFFFFFF),
            contentColor = Color(0xFF136204)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = notifyDto.message,
                fontSize = 15.sp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
            )
        }
    }
    if (isReportDialogVisible) {
        NotificationReportDialog(
            onDismiss = { isReportDialogVisible = false }
        )
    }
}
