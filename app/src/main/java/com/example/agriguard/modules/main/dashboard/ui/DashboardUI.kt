package com.example.agriguard.modules.main.dashboard.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agriguard.R
import com.example.agriguard.modules.main.MainNav
import com.example.agriguard.modules.main.report.ui.PieChart
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.shared.ui.PlantsDialog

@Composable
fun DashboardUI(
    navController: NavController,
    currentUser: UserDto
) {
    var expanded by remember { mutableStateOf(false) }
    val listOfDate = listOf("January","February","March","April","May","June","July","August","September","October","November","December")

    var selectedDate by remember { mutableStateOf("Select Dates") }
    var isSampleFormDialogVisible by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(!currentUser.isFarmers && !currentUser.isTechnician) {
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
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF136204))
                        .border(3.dp, Color(0xFF136204), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.person),
                        contentDescription = "Default placeholder",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "Hello ${currentUser.firstName} ",
                        fontSize = 25.sp,
                        color = Color(0xFF136204),
                        fontWeight = FontWeight.W800,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = "Magandang Araw!",
                        fontSize = 20.sp,
                        color = Color(0xFF136204),
                        fontFamily = FontFamily.SansSerif
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Image",
                    modifier = Modifier.size(105.dp)
                )
            }
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(85.dp)
                    ) {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(15.dp)
                                    .background(Color(0xFF8dc63e))
                            )
                            Text(
                                text = "Damage",
                                fontSize = 16.sp,
                                color = Color(0xFF136204),
                                fontFamily = FontFamily.SansSerif
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .size(15.dp)
                                    .background(Color(0xFF136204))
                            )
                            Text(
                                text = "Undamage",
                                fontSize = 16.sp,
                                color = Color(0xFF136204),
                                fontFamily = FontFamily.SansSerif
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Analytical Report",
                                fontSize = 17.sp,
                                color = Color(0xFF136204),
                                fontFamily = FontFamily.SansSerif
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .width(130.dp)
                                    .background(Color.Transparent)
                                    .clickable { expanded = !expanded }
                                    .wrapContentSize(Alignment.Center)
                                    .drawBehind {
                                        val strokeWidth = 1.dp.toPx()
                                        val y = size.height - strokeWidth / 2
                                        drawLine(
                                            color = Color(0xFF136204),
                                            start = Offset(0f, y),
                                            end = Offset(size.width, y),
                                            strokeWidth = strokeWidth
                                        )
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = selectedDate,
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth(),
                                        fontSize = 16.sp,
                                        color = Color(0xFF136204),
                                        fontFamily = FontFamily.SansSerif,
                                        textAlign = TextAlign.Center,
                                    )
                                    Icon(
                                        painter = if (expanded) painterResource(id = R.drawable.ic_arrow_up) else painterResource(
                                            id = R.drawable.ic_arrow_down
                                        ),
                                        contentDescription = "Dropdown Icon",
                                        tint = Color(0xFF136204),
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier
                                        .width(124.dp)
                                        .background(color = Color.Transparent)
                                        .wrapContentSize(Alignment.TopStart)
                                        .heightIn(max = 165.dp)
                                ) {
                                    listOfDate.forEach { dates ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = dates,
                                                    color = Color(0xFF136204),
                                                    fontFamily = FontFamily.SansSerif,
                                                    fontSize = 16.sp,
                                                     textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                )
                                            }, onClick = {
                                                selectedDate = dates
                                                expanded = false
                                            })
                                    }
                                }
                            }
                        }
                    }
                }
            PieChart()

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = { navController.navigate(MainNav.Addresses) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    if (currentUser.isAdmin) {
                        Text(
                            text = "Create an account for an \n admin or technician",
                            fontSize = 18.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = Color(0xFF136204),
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }

        }else{
            FarmersDashboard(
                navController = navController,
                isSampleFormDialogVisible = isSampleFormDialogVisible,
                onDialogVisibilityChange = { isVisible ->
                    isSampleFormDialogVisible = isVisible
                },
                currentUser = currentUser
            )
        }
    }
}

@Composable
fun FarmersDashboard(
    navController: NavController,
    isSampleFormDialogVisible: Boolean,
    onDialogVisibilityChange: (Boolean) -> Unit,
    currentUser: UserDto
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFFFFFFFF)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF))
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = Color(0xFF136204),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF136204))
                        .border(3.dp, Color(0xFF136204), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.person),
                        contentDescription = "Default placeholder",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "Hello ${currentUser.firstName} ",
                        fontSize = 25.sp,
                        color = Color(0xFF136204),
                        fontWeight = FontWeight.W800,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = "Magandang Araw!",
                        fontSize = 20.sp,
                        color = Color(0xFF136204),
                        fontFamily = FontFamily.SansSerif
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = "Notification",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable { navController.navigate(MainNav.NotificationList) },
                    colorFilter = ColorFilter.tint(Color(0xFF136204))
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .background(Color.White)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                CropsCategory(
                    navController,
                    onCropsMonitoringClick = {
                        onDialogVisibilityChange(true)
                    },
                    currentUser = currentUser
                )
                if (isSampleFormDialogVisible) {
                    PlantsDialog(
                        onDismiss = { onDialogVisibilityChange(false) },
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun CropsCategory(
    navController: NavController,
    onCropsMonitoringClick: () -> Unit,
    currentUser: UserDto
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val actions = if (currentUser.isTechnician) {
            listOf(
                Triple("INFO HUB", R.drawable.crop_monitor, onCropsMonitoringClick),
                Triple("COMPLAINTS", R.drawable.submitted_report) { navController.navigate(MainNav.ComplaintReportList(userId = currentUser.id!!)) },
                Triple("RICE INSURANCE", R.drawable.ricein) { navController.navigate(MainNav.RiceInsuranceList(userId = currentUser.id!!)) },
                Triple("ONION INSURANCE", R.drawable.onionin) { navController.navigate(MainNav.OnionInsuranceList(userId = currentUser.id!!)) },
                Triple("INDEMNITY", R.drawable.inde) { navController.navigate(MainNav.IndemnityList) },
                Triple("MESSAGES", R.drawable.messageicon) { navController.navigate(MainNav.Message) }
            )
        } else {
            listOf(
                Triple("INFO HUB", R.drawable.crop_monitor, onCropsMonitoringClick),
                Triple("COMPLAINTS", R.drawable.submitted_report) { navController.navigate(MainNav.ComplaintReportList(userId = currentUser.id!!)) },
                Triple("RICE INSURANCE", R.drawable.ricein) { navController.navigate(MainNav.RiceInsuranceList(userId = currentUser.id!!)) },
                Triple("ONION INSURANCE", R.drawable.onionin) { navController.navigate(MainNav.OnionInsuranceList(userId = currentUser.id!!)) },
                Triple("INDEMNITY", R.drawable.inde) { navController.navigate(MainNav.IndemnityList) },
                Triple("MESSAGES", R.drawable.messageicon) { navController.navigate(MainNav.Message) }
            )
        }

        items(actions.size) { index ->
            val (title,imageRes, action) = actions[index]

            OutlinedCard(
                onClick = { action() },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                border = BorderStroke(1.dp, Color(0xFF136204)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = title,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        color = Color(0xFF136204),
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily.SansSerif,
                    )
                }
            }
        }
    }
}