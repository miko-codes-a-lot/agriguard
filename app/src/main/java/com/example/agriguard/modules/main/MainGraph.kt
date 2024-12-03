package com.example.agriguard.modules.main

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.agriguard.modules.main.dashboard.ui.DashboardUI
import com.example.agriguard.modules.main.farmer.AddressesUI
import com.example.agriguard.modules.main.farmer.ComplaintFormUI
import com.example.agriguard.modules.main.farmer.FarmersUI
import com.example.agriguard.modules.main.farmer.FarmersPreviewUI
import com.example.agriguard.modules.main.farmer.viewmodel.FarmersViewModel
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
import com.example.agriguard.modules.main.user.model.dto.AddressDto
import com.example.agriguard.modules.main.user.service.UserService
import com.example.agriguard.modules.main.user.ui.UserCreateUI
import com.example.agriguard.modules.main.user.ui.UserEditUI
import com.example.agriguard.modules.main.user.ui.UsersUI
import com.example.agriguard.modules.main.user.viewmodel.UserViewModel
import com.example.agriguard.modules.shared.Guard
import com.example.agriguard.modules.shared.ext.toObjectId

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation<MainNav>(startDestination = MainNav.Menu) {
        composable<MainNav.Menu> {
            Guard(navController = navController) { currentUser ->
                MenuUI(navController, currentUser)
            }
        }
        composable<MainNav.Dashboard> {
            Guard(navController = navController) { currentUser ->
                DashboardUI(navController, currentUser)
            }
        }
        composable<MainNav.ComplainForm> {
            Guard(navController = navController) { currentUser ->
                ComplaintFormUI(navController)
            }
        }
        composable<MainNav.Users> {
            Guard(navController = navController) { currentUser ->
                UsersUI(
                    navController,
                )
            }
        }
        composable<MainNav.EditUser> {
            val args = it.toRoute<MainNav.EditUser>()
            val userViewModel: UserViewModel = hiltViewModel()
            val userDto = userViewModel.fetchUser(args.userId)
            val userService: UserService = hiltViewModel<UserViewModel>().userService
            Guard(navController = navController) { currentUser ->
                UserEditUI(
                    navController = navController,
                    currentUser = currentUser,
                    userDto = userDto,
                    userService = userService
                )
            }
        }
        composable<MainNav.Addresses> {
            Guard(navController = navController) { currentUser ->
                AddressesUI(navController)
            }
        }
        composable<MainNav.CreateUser> {
            val args = it.toRoute<MainNav.CreateUser>()
            val farmersViewModel: FarmersViewModel = hiltViewModel()
            val addressDto =
                if(args.addressId != null)
                    farmersViewModel.fetchOneAddress(args.addressId.toObjectId())
                else
                    null
            val userService: UserService = hiltViewModel<UserViewModel>().userService
            Guard(navController = navController) { currentUser ->
                UserCreateUI(
                    navController = navController,
                    currentUser = currentUser,
                    addressDto = addressDto,
                    userService = userService
                )
            }
        }
        composable<MainNav.Farmers> {
            val args = it.toRoute<MainNav.Farmers>()
            val farmersViewModel: FarmersViewModel = hiltViewModel();
            var addressDto: AddressDto? = null;
            if (args.addressId != null) {
                 addressDto =  farmersViewModel.fetchOneAddress(args.addressId.toObjectId())
            }
            Guard(navController = navController) { currentUser ->
                FarmersUI(
                    navController = navController,
                    currentId = currentUser,
                    addressDto = addressDto,
                    status = args.status
                )
            }
        }
        composable<MainNav.FarmersPreview> {
            Guard(navController = navController) { currentUser ->
                FarmersPreviewUI(navController, currentUser)
            }
        }
        composable<MainNav.Message> {
            Guard(navController = navController) { currentUser ->
                MessageUI(navController)
            }
        }
        composable<MainNav.MessageList> {
            Guard(navController = navController) { currentUser ->
                MessageListUI(navController)
            }
        }
        composable<MainNav.NotificationList> {
            Guard(navController = navController) { currentUser ->
                NotificationListUI(navController)
            }
        }
        composable<MainNav.FormValidation> {
            Guard(navController = navController) { currentUser ->
                ReportFormValidationUI(navController)
            }
        }
        composable<MainNav.Setting> {
            Guard(navController = navController) { currentUser ->
                val userService: UserService = hiltViewModel<UserViewModel>().userService
                SettingsUI(navController, currentUser = currentUser, userService = userService)
            }
        }
        composable<MainNav.RiceInsurance> {
            Guard(navController = navController) { currentUser ->
                RiceInsuranceFormUI()
            }
        }
        composable<MainNav.InDemnity> {
            Guard(navController = navController) { currentUser ->
                InDemnityFormUI()
            }
        }
        composable<MainNav.OnionInsurance> {
            Guard(navController = navController) { currentUser ->
                OnionInsuranceFormUI()
            }
        }
        composable<MainNav.RiceDisease> {
            Guard(navController = navController) { currentUser ->
                RiceDiseaseUI(navController)
            }
        }
        composable<MainNav.OnionDisease> {
            Guard(navController = navController) { currentUser ->
                OnionDiseaseUI()
            }
        }
        composable<MainNav.RicePets> {
            Guard(navController = navController) { currentUser ->
                RicePetsModule()
            }
        }
        composable<MainNav.OnionPets> {
            Guard(navController = navController) { currentUser ->
                OnionPetsModule()
            }
        }
        composable<MainNav.OnionWeed> {
            Guard(navController = navController) { currentUser ->
                OnionWeedUI()
            }
        }
        composable<MainNav.RiceWeed> {
            Guard(navController = navController) { currentUser ->
                RiceWeedUI()
            }
        }
        composable<MainNav.ReportList> {
            Guard(navController = navController) { currentUser ->
                ReportListUI(navController)
            }
        }
    }
}