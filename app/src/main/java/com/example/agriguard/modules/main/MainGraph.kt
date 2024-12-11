package com.example.agriguard.modules.main

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.agriguard.modules.main.dashboard.ui.DashboardUI
import com.example.agriguard.modules.main.farmer.AddressesUI
import com.example.agriguard.modules.main.complain.ui.ComplaintInsuranceFormUI
import com.example.agriguard.modules.main.complain.viewmodel.ComplaintViewModel
import com.example.agriguard.modules.main.farmer.FarmersPreviewUI
import com.example.agriguard.modules.main.farmer.FarmersUI
import com.example.agriguard.modules.main.farmer.viewmodel.FarmersViewModel
import com.example.agriguard.modules.main.indemnity.viewmodel.IndemnityViewModel
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
import com.example.agriguard.modules.main.complain.ui.ComplaintReportListUI
import com.example.agriguard.modules.main.indemnity.ui.IndemnityFormUI
import com.example.agriguard.modules.main.onion.ui.OnionInsuranceFormUI
import com.example.agriguard.modules.main.onion.viewmodel.OnionInsuranceViewmodel
import com.example.agriguard.modules.main.indemnity.ui.InDemnityListUI
import com.example.agriguard.modules.main.onion.ui.OnionInsuranceListUI
import com.example.agriguard.modules.main.report.ui.RegistrationMenuUI
import com.example.agriguard.modules.main.report.ui.ReportDashboardUI
import com.example.agriguard.modules.main.report.ui.ReportFormValidationUI
import com.example.agriguard.modules.main.rice.ui.RiceInsuranceFormUI
import com.example.agriguard.modules.main.rice.ui.RiceInsuranceListUI
import com.example.agriguard.modules.main.rice.viewmodel.RiceInsuranceViewModel
import com.example.agriguard.modules.main.setting.SettingsUI
import com.example.agriguard.modules.main.user.model.dto.AddressDto
import com.example.agriguard.modules.main.user.service.UserService
import com.example.agriguard.modules.main.user.ui.UserCreateUI
import com.example.agriguard.modules.main.user.ui.UserEditUI
import com.example.agriguard.modules.main.user.ui.UsersUI
import com.example.agriguard.modules.main.user.viewmodel.UserViewModel
import com.example.agriguard.modules.shared.Guard
import com.example.agriguard.modules.shared.ext.toObjectId
import kotlinx.coroutines.launch

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
//            OnionInsuranceFormUI(
//                navController = navController,
//                currentUser = currentUser,
//                viewModel = viewModel
//            ) { dto ->
//                scope.launch {
//                    val result = viewModel.upsert(dto, currentUser)
//                    if (result.isSuccess) {
//                        Log.d("micool", "good: $result")
//                    } else {
//                        Log.e("micool", "fail: $result")
//                    }
//                }
//            }
        composable<MainNav.ComplainForm> {
            val args = it.toRoute<MainNav.ComplainForm>()
            val viewModel: ComplaintViewModel = hiltViewModel();
            val scope = rememberCoroutineScope()
            Guard(navController = navController) { currentUser ->
                ComplaintInsuranceFormUI(
                    currentUser = currentUser,
                    navController = navController,
                    viewModel = viewModel,
                ){ dto ->
                    scope.launch {
                        val result = viewModel.upsertComplaint(dto, currentUser)
                        if (result.isSuccess) {
                            Log.d("micool", "good: $result")
                        } else {
                            Log.e("micool", "fail: $result")
                        }
                    }
                }
//                ComplaintFormUI(
//                    currentUser = currentUser,
//                    navController = navController,
//                    viewModel = viewModel,
//                ){ dto ->
//                    scope.launch {
//                        val result = viewModel.upsertComplaint(dto, currentUser)
//                        if (result.isSuccess) {
//                            Log.d("micool", "good: $result")
//                        } else {
//                            Log.e("micool", "fail: $result")
//                        }
//                    }
//                }
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
        composable<MainNav.RiceInsuranceForm> {
            Guard(navController = navController) { currentUser ->
                val viewModel: RiceInsuranceViewModel = hiltViewModel()
                val scope = rememberCoroutineScope()
                RiceInsuranceFormUI(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel,
                ) { dto ->
                    scope.launch {
                        val result = viewModel.upsert(dto, currentUser)
                        if (result.isSuccess) {
                            // show success dialog here
                            Log.d("micool", "good: $result")
                        } else {
                            // show fail dialog here
                            Log.e("micool", "fail: $result")
                        }
                    }
                }
            }
        }
        composable<MainNav.RiceInsuranceList> {
            val args = it.toRoute<MainNav.RiceInsuranceList>()
            val riceInsuranceViewModel: RiceInsuranceViewModel = hiltViewModel()
            Guard(navController = navController) { currentUser ->
                val riceInsurance = riceInsuranceViewModel.fetchListRiceInsurance(args.userId)
                RiceInsuranceListUI(
                    navController,
                    riceInsuranceList = riceInsurance,
                    currentUser = currentUser,
                )
            }
        }
        composable<MainNav.InDemnityForm> {
            Guard(navController = navController) { currentUser ->
                val viewModel: IndemnityViewModel = hiltViewModel()
                val scope = rememberCoroutineScope()
                IndemnityFormUI(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel = viewModel
                ) { dto ->
                    scope.launch {
                        val result = viewModel.upsert(dto, currentUser)
                        if (result.isSuccess) {
                            Log.d("micool", "good: $result")
                        } else {
                            Log.e("micool", "fail: $result")
                        }
                    }
                }
            }
        }
        composable<MainNav.InDemnityList> {
            val args = it.toRoute<MainNav.InDemnityList>()
            val indemnityViewModel: IndemnityViewModel = hiltViewModel()
            Guard(navController = navController) { currentUser ->
                val indemnityList = indemnityViewModel.fetchListIndemnity(args.userId)
                    InDemnityListUI(
                        navController = navController,
                        indemnityList = indemnityList,
                        currentUser = currentUser,
                    )
            }
        }
        composable<MainNav.OnionInsuranceForm> {
            Guard(navController = navController) { currentUser ->
                val viewModel: OnionInsuranceViewmodel = hiltViewModel()
                val scope = rememberCoroutineScope()
                OnionInsuranceFormUI(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel = viewModel
                ) { dto ->
                    scope.launch {
                        val result = viewModel.upsert(dto, currentUser)
                        if (result.isSuccess) {
                            Log.d("micool", "good: $result")
                        } else {
                            Log.e("micool", "fail: $result")
                        }
                    }
                }
            }
        }
        composable<MainNav.OnionInsuranceList> {
            val args = it.toRoute<MainNav.OnionInsuranceForm>()
            val onionInsuranceViewModel: OnionInsuranceViewmodel = hiltViewModel()
            Guard(navController = navController) { currentUser ->
                val onionInsurance = onionInsuranceViewModel.fetchListOnionInsurance(args.userId)
                OnionInsuranceListUI(
                    navController = navController,
                    onionInsuranceList = onionInsurance,
                    currentUser = currentUser,
                )
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
        composable<MainNav.ComplaintReportList> {
            Guard(navController = navController) { currentUser ->
                ComplaintReportListUI(navController, currentUser)
            }
        }
        composable<MainNav.Registration> {
            Guard(navController = navController) { currentUser ->
                RegistrationMenuUI(navController)
            }
        }
        composable<MainNav.ReportDashboard> {
            Guard(navController = navController) { currentUser ->
                ReportDashboardUI()
            }
        }
    }
}