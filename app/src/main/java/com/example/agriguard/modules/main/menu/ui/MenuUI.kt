package com.example.agriguard.modules.main.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R
import com.example.agriguard.modules.main.dashboard.ui.DashboardUI
import com.example.agriguard.modules.main.farmer.AddressesUI
import com.example.agriguard.modules.main.menu.model.NavItem
import com.example.agriguard.modules.main.message.MessageListUI
import com.example.agriguard.modules.main.message.MessageUI
import com.example.agriguard.modules.main.notification.NotificationListUI
import com.example.agriguard.modules.main.report.ui.ReportFormValidationUI
import com.example.agriguard.modules.main.complain.ui.ComplaintReportListUI
import com.example.agriguard.modules.main.report.ui.RegistrationMenuUI
import com.example.agriguard.modules.main.report.ui.ReportDashboardUI
import com.example.agriguard.modules.main.setting.SettingsUI
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.ui.UsersUI

@Preview(showSystemUi = true)
@Composable
fun MenuUIPreview() {
    MenuUI(
        navController = rememberNavController(),
        UserDto(isAdmin = true)
    )
}

@Composable
fun MenuUI(
    navController: NavController,
    currentUser: UserDto
) {
    var routeName by rememberSaveable { mutableStateOf("HomeDashboard") }
    val navItems = getNavItems(navController, currentUser)
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF136204)
            ) {
                navItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(painter = item.icon,
                                contentDescription = null,
                                tint = if (routeName == item.routeName) Color(0xFF136204) else Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        },
                        selected = routeName == item.routeName,

                        onClick = {
                            routeName = item.routeName
                        }
                    )
                }
            }
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (routeName) {
                "HomeDashboard" -> DashboardUI(navController, currentUser)
                "AddressList" -> AddressesUI(navController)
                "MessageList" -> MessageListUI(navController)
                "Report" -> ReportDashboardUI()
                "Message" -> MessageUI(navController)
                "FormValidation" -> ReportFormValidationUI(navController)
                "Setting" -> SettingsUI(navController, currentUser)
                "UsersList" -> UsersUI(navController)
                "RegistrationMenu" -> RegistrationMenuUI(navController)
            }
        }
    }
}

@Composable
fun getNavItems(navController: NavController, userDto: UserDto): List<NavItem> {
    return when {
        userDto.isFarmers -> listOf(
            NavItem(
                icon = painterResource(id = R.drawable.home),
                routeName = "HomeDashboard",
                navigation = navController,
            ),
            NavItem(
                icon = painterResource(id = R.drawable.setting),
                routeName = "Setting",
                navigation = navController,
            ),
        )
        userDto.isTechnician -> listOf(
            NavItem(
                icon = painterResource(id = R.drawable.home),
                routeName = "HomeDashboard",
                navigation = navController,
            ),
            NavItem(
                icon = painterResource(id = R.drawable.report_icon),
                routeName = "Report",
                navigation = navController,
            ),
            NavItem(
                icon =  painterResource(id = R.drawable.farmer),
                routeName = "AddressList",
                navigation = navController,
            ),
            NavItem(
                icon = painterResource(id = R.drawable.setting),
                routeName = "Setting",
                navigation = navController,
            ),
        )
        userDto.isAdmin -> listOf(
            NavItem(
                icon = painterResource(id = R.drawable.home),
                routeName = "HomeDashboard",
                navigation = navController,
            ),
            NavItem(
                icon =  painterResource(id = R.drawable.users),
                routeName = "UsersList",
                navigation = navController,
            ),
            NavItem(
                icon = painterResource(id = R.drawable.setting),
                routeName = "Setting",
                navigation = navController,
            ),

            )
        else -> listOf()
    }
}