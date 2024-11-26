package com.example.agriguard.modules.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.agriguard.modules.main.dashboard.ui.DashboardUI
import com.example.agriguard.modules.main.farmer.AddressUI
import com.example.agriguard.modules.main.farmer.ComplaintFormUI
import com.example.agriguard.modules.main.farmer.FarmersListUI
import com.example.agriguard.modules.main.farmer.FarmersPreviewUI
import com.example.agriguard.modules.main.menu.ui.MenuUI
import com.example.agriguard.modules.main.message.MessageListUI
import com.example.agriguard.modules.main.message.MessageUI
import com.example.agriguard.modules.main.module.OnionDiseaseUI
import com.example.agriguard.modules.main.module.OnionPetsModule
import com.example.agriguard.modules.main.module.OnionWeedUI
import com.example.agriguard.modules.main.module.RiceDiseaseUI
import com.example.agriguard.modules.main.module.RicePetsModule
import com.example.agriguard.modules.main.module.RiceWeedUI
import com.example.agriguard.modules.main.notification.NotificationListUI
import com.example.agriguard.modules.main.report.ui.InDemnityFormUI
import com.example.agriguard.modules.main.report.ui.OnionInsuranceFormUI
import com.example.agriguard.modules.main.report.ui.ReportFormValidationUI
import com.example.agriguard.modules.main.report.ui.ReportListUI
import com.example.agriguard.modules.main.report.ui.RiceInsuranceFormUI
import com.example.agriguard.modules.main.setting.SettingsUI
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.ui.UserCreateUI
import com.example.agriguard.modules.main.user.ui.UsersList
import com.example.agriguard.modules.shared.Guard

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation<MainNav>(startDestination = MainNav.Menu) {
        composable<MainNav.Menu> {
            Guard(navController = navController) { currentUser ->
                MenuUI(navController, currentUser)
//                MenuUI(navController, UserDto(isAdmin = true))
            }
        }
        composable<MainNav.Dashboard> {
            DashboardUI(navController)
        }
        composable<MainNav.ComplainForm> {
            ComplaintFormUI(navController)
        }
        composable<MainNav.UsersList> {
            UsersList(navController)
        }
        composable<MainNav.Address> {
            AddressUI(navController)
        }
        composable<MainNav.CreateUser> {
            Guard(navController = navController) { currentUser ->
                UserCreateUI(navController, currentUser)
            }
        }
        composable<MainNav.FarmersList> {
            FarmersListUI(navController)
        }
        composable<MainNav.FarmersPreview> {
            FarmersPreviewUI(navController)
        }
        composable<MainNav.Message> {
            MessageUI(navController)
        }
        composable<MainNav.MessageList> {
            MessageListUI(navController)
        }
        composable<MainNav.NotificationList> {
            NotificationListUI(navController)
        }
        composable<MainNav.FormValidation> {
            ReportFormValidationUI(navController)
        }
        composable<MainNav.Setting> {
            SettingsUI(navController)
        }
        composable<MainNav.RiceInsurance> {
            RiceInsuranceFormUI()
        }
        composable<MainNav.InDemnity> {
            InDemnityFormUI()
        }
        composable<MainNav.OnionInsurance> {
            OnionInsuranceFormUI()
        }
        composable<MainNav.RiceDisease> {
            RiceDiseaseUI(navController)
        }
        composable<MainNav.OnionDisease> {
            OnionDiseaseUI()
        }
        composable<MainNav.RicePets> {
            RicePetsModule()
        }
        composable<MainNav.OnionPets> {
            OnionPetsModule()
        }
        composable<MainNav.OnionWeed> {
            OnionWeedUI()
        }
        composable<MainNav.RiceWeed> {
            RiceWeedUI()
        }
        composable<MainNav.ReportList> {
            ReportListUI(navController)
        }
    }
}