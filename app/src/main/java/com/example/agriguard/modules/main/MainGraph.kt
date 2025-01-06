package com.example.agriguard.modules.main

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.agriguard.modules.main.chat.model.viewmodel.ChatViewModel
import com.example.agriguard.modules.main.chat.ui.ChatDirectUI
import com.example.agriguard.modules.main.chat.ui.ChatLobbyUI
import com.example.agriguard.modules.main.complain.model.dto.ComplainWithUserDto
import com.example.agriguard.modules.main.complain.ui.ComplaintDetailsUI
import com.example.agriguard.modules.main.complain.ui.ComplaintFormUI
import com.example.agriguard.modules.main.complain.ui.ComplaintListUI
import com.example.agriguard.modules.main.complain.viewmodel.ComplaintViewModel
import com.example.agriguard.modules.main.dashboard.ui.DashboardUI
import com.example.agriguard.modules.main.farmer.AddressesUI
import com.example.agriguard.modules.main.farmer.FarmersPreviewUI
import com.example.agriguard.modules.main.farmer.FarmersUI
import com.example.agriguard.modules.main.farmer.viewmodel.FarmersViewModel
import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityWithUserDto
import com.example.agriguard.modules.main.indemnity.ui.IndemnityDetailsUI
import com.example.agriguard.modules.main.indemnity.ui.IndemnityListUI
import com.example.agriguard.modules.main.indemnity.ui.IndemnityFormUI
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
import com.example.agriguard.modules.main.notify.ui.NotificationListUI
import com.example.agriguard.modules.main.notify.viewmodel.NotifyViewModel
import com.example.agriguard.modules.main.onion.model.dto.OnionWithUserDto
import com.example.agriguard.modules.main.onion.ui.OnionInsuranceCreate
import com.example.agriguard.modules.main.onion.ui.OnionInsuranceDetails
import com.example.agriguard.modules.main.onion.ui.OnionInsuranceListUI
import com.example.agriguard.modules.main.onion.viewmodel.OnionInsuranceViewmodel
import com.example.agriguard.modules.main.report.ui.RegistrationMenuUI
import com.example.agriguard.modules.main.report.ui.ReportDashboardUI
import com.example.agriguard.modules.main.report.ui.ReportFormValidationUI
import com.example.agriguard.modules.main.rice.model.dto.RiceWIthUserDto
import com.example.agriguard.modules.main.rice.ui.RiceInsuranceFormDetails
import com.example.agriguard.modules.main.rice.ui.RiceInsuranceFormUI
import com.example.agriguard.modules.main.rice.ui.RiceInsuranceListUI
import com.example.agriguard.modules.main.rice.viewmodel.RiceInsuranceViewModel
import com.example.agriguard.modules.main.setting.EditSettingsUI
import com.example.agriguard.modules.main.setting.SettingsUI
import com.example.agriguard.modules.main.user.model.dto.AddressDto
import com.example.agriguard.modules.main.user.service.UserService
import com.example.agriguard.modules.main.user.ui.UserValidIdUI
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
                AddressesUI(navController, currentUser)
            }
        }
        composable<MainNav.ChatDirect> {
            val args = it.toRoute<MainNav.ChatDirect>()
            Guard(navController = navController) { currentUser ->
                val chatViewModel: ChatViewModel = hiltViewModel()
                val isChatReady = remember { mutableStateOf(false) }
                val userViewModel: UserViewModel = hiltViewModel()

                val receiver = userViewModel.fetchUser(args.userId)

                LaunchedEffect(key1 = "message") {
                    Log.d("ChatDirect", "Creating or fetching chat...")

                    Log.d("micool", "I have to run this before I run the code below...")
                    chatViewModel.findOneChatOrCreate(currentUser, receiver).also {
                        Log.d("ChatDirect", "Creating or fetching chat...")
                    }
                    isChatReady.value = true
                }

                val messages = if (isChatReady.value) {
                    chatViewModel.fetchDirectMessages(currentUser, receiver)
                        .collectAsStateWithLifecycle(
                            initialValue = emptyList()
                        ).value
                } else {
                    emptyList()
                }

                ChatDirectUI(
                    messages = messages,
                    currentUserId = currentUser.id!!,
                    onSendMessage = { message ->
                        chatViewModel.sendMessage(currentUser, receiver, message)
                    }
                )
            }
        }
        composable<MainNav.ChatLobby> {
            Guard(navController = navController) { currentUser ->
                val chatViewModel: ChatViewModel = hiltViewModel()
                val usersChat by chatViewModel.fetchUsers(currentUser.id.toObjectId()).collectAsStateWithLifecycle(
                    initialValue = listOf()
                )
                ChatLobbyUI(navController = navController, data = usersChat)
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
            val args = it.toRoute<MainNav.FarmersPreview>()
            val userViewModel: UserViewModel = hiltViewModel()
            Guard(navController = navController) { currentUser ->
                val userDto = userViewModel.fetchUser(args.userId)
                Log.d("userDto","$userDto")
                FarmersPreviewUI(navController = navController, currentUser = currentUser, user = userDto)
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
                val notifyViewModel: NotifyViewModel = hiltViewModel()
                val notifyList = notifyViewModel.fetchAllNotify()

                val scope = rememberCoroutineScope()
                NotificationListUI(
                    notifyList,
                    onClick = { notifyDto ->
                        scope.launch {
                            notifyViewModel.markAsRead(notifyDto)
                        }

                        if (notifyDto.documentType?.contains("indemnity", ignoreCase = true) == true) {
                            navController.navigate(MainNav.IndemnityDetails(notifyDto.documentId!!, currentUser.id!!))
                        }
                    }
                )
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
        composable<MainNav.ComplainCreate> {
            val viewModel: ComplaintViewModel = hiltViewModel()
            val scope = rememberCoroutineScope()
            Guard(navController = navController) { currentUser ->
                ComplaintFormUI(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel = viewModel,
                    onSubmit = { updatedDto ->
                        scope.launch {
                            viewModel.upsert(updatedDto, currentUser)
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
        composable<MainNav.ComplainEdit> {
            val args = it.toRoute<MainNav.RiceInsuranceEdit>()
            val viewModel: ComplaintViewModel = hiltViewModel()
            val riceInsuranceDto = viewModel.fetchOne(args.id)
            viewModel.setFormState(riceInsuranceDto)

            val scope = rememberCoroutineScope()
            Guard(navController = navController) { currentUser ->
                ComplaintFormUI(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel = viewModel,
                    onSubmit = { data ->
                        scope.launch {
                            viewModel.upsert(data, currentUser)
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
        composable<MainNav.ComplaintDetails> {
            val args = it.toRoute<MainNav.ComplaintDetails>()
            val viewModel: ComplaintViewModel = hiltViewModel()
            val scope = rememberCoroutineScope()
            val userViewModel: UserViewModel = hiltViewModel()
            Guard(navController = navController) { currentUser ->
                val complainWithUserDto = remember {
                    val complaints = viewModel.fetchOne(args.id)
                    val farmerUserDto = userViewModel.fetchUser(complaints.userId)
                    ComplainWithUserDto(complaints, farmerUserDto)
                }
                val complaintDto = viewModel.fetchOne(args.id)
                val status = rememberSaveable { mutableStateOf(complaintDto.status ?: "pending") }
                ComplaintDetailsUI(
                    title = "Complaints Details",
                    complainWithUserDto = complainWithUserDto,
                    currentUser = currentUser,
                    complaintInsurance = complaintDto.copy(status = status.value),
                    status = status,
                    onClickEdit = {
                        navController.navigate(MainNav.ComplainEdit(args.id))
                    },
                    onClickLike = { isLike ->
                        scope.launch {
                            val newStatus = if (isLike) "approved" else "rejected"
                            status.value = newStatus
                            scope.launch {
                                complaintDto.status = newStatus
                                val result = viewModel.upsert(complaintDto, currentUser)

                                if (result.isSuccess) {
                                    Log.d("micool", "Update succeeded: $result")
                                } else {
                                    status.value = complaintDto.status ?: "pending"
                                    Log.e("micool", "Update failed: $result")
                                }
                            }
                        }
                    }
                )
            }
        }
        composable<MainNav.RiceCreate> {
            val viewModel: RiceInsuranceViewModel = hiltViewModel()
            val scope = rememberCoroutineScope()
            Guard(navController = navController) { currentUser ->
                RiceInsuranceFormUI(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel = viewModel,
                    onSubmit = { updatedDto ->
                        scope.launch {
                            viewModel.upsert(updatedDto, currentUser)
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
        composable<MainNav.RiceInsuranceEdit> {
            Guard(navController) { currentUser ->
                val args = it.toRoute<MainNav.RiceInsuranceEdit>()
                val viewModel: RiceInsuranceViewModel = hiltViewModel()
                val riceInsuranceDto = viewModel.fetchOne(args.id)
                viewModel.setFormState(riceInsuranceDto)

                val scope = rememberCoroutineScope()
                RiceInsuranceFormUI(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel = viewModel
                ) { data ->
                    scope.launch {
                        val result = viewModel.upsert(data, currentUser)
                        if (result.isSuccess) {
                            Log.d("micool", "good: $result")
                        } else {
                            Log.e("micool", "fail: $result")
                        }
                    }
                }
            }
        }
        composable<MainNav.RiceInsuranceDetails> {
            Guard(navController) { currentUser ->
                val args = it.toRoute<MainNav.RiceInsuranceDetails>()
                val viewModel: RiceInsuranceViewModel = hiltViewModel()
                val userViewModel: UserViewModel = hiltViewModel()
                val riceWIthUserDto = remember {
                    val riceInsurance = viewModel.fetchOne(args.id)
                    val farmerUserDto = userViewModel.fetchUser(riceInsurance.userId)
                    RiceWIthUserDto(riceInsurance, farmerUserDto)
                }
                val riceInsuranceDto = viewModel.fetchOne(args.id)
                val status = rememberSaveable { mutableStateOf(riceInsuranceDto.status ?: "pending") }
                val scope = rememberCoroutineScope()
                RiceInsuranceFormDetails(
                    title = "RiceInsurance Details",
                    riceWIthUserDto = riceWIthUserDto,
                    currentUser = currentUser,
                    riceInsurance = riceInsuranceDto.copy(status = status.value),
                    status = status,
                    onClickEdit = {
                        navController.navigate(MainNav.RiceInsuranceEdit(args.id))
                    },
                    onClickLike = { isLike ->
                        val newStatus = if (isLike) "approved" else "rejected"
                        status.value = newStatus
                        scope.launch {
                            riceInsuranceDto.status = newStatus
                            val result = viewModel.upsert(riceInsuranceDto, currentUser)

                            if (result.isSuccess) {
                                Log.d("micool", "Update succeeded: $result")
                            } else {
                                status.value = riceInsuranceDto.status ?: "pending"
                                Log.e("micool", "Update failed: $result")
                            }
                        }
                    }
                )
            }
        }
        composable<MainNav.RiceInsuranceList> {
            val riceInsuranceViewModel: RiceInsuranceViewModel = hiltViewModel()
            Guard(navController = navController) { currentUser ->
                val riceInsurance = riceInsuranceViewModel.fetchAll(currentUser)
                RiceInsuranceListUI(
                    navController,
                    riceInsuranceList = riceInsurance,
                    currentUser = currentUser,
                )
            }
        }
        composable<MainNav.IndemnityDetails> {
                val args = it.toRoute<MainNav.IndemnityDetails>()
                val viewModel: IndemnityViewModel = hiltViewModel()
                val userViewModel: UserViewModel = hiltViewModel()
                val scope = rememberCoroutineScope()
            Guard(navController) { currentUser ->
                val indemnityWithUser = remember {
                    val indemnityDto = viewModel.fetchOne(args.id)
                    val farmerUserDto = userViewModel.fetchUser(indemnityDto.userId)
                    IndemnityWithUserDto(indemnityDto, farmerUserDto)
                }
                val status = rememberSaveable { mutableStateOf(indemnityWithUser.indemnity.status ?: "pending") }
                IndemnityDetailsUI(
                    title = "Indemnity Details",
                    indemnityWithUser = indemnityWithUser.copy(
                        indemnity = indemnityWithUser.indemnity.copy(status = status.value)
                    ),
                    currentUser = currentUser,
                    indemnity = indemnityWithUser.indemnity.copy(status = status.value),
                    status = status,
                    onClickEdit = {
                        navController.navigate(MainNav.IndemnityEdit(args.id))
                    },
                    onClickLike = { isLike ->
                        val newStatus = if (isLike) "approved" else "rejected"
                        status.value = newStatus
                        scope.launch {
                            indemnityWithUser.indemnity.status = newStatus
                            val result = viewModel.upsert(indemnityWithUser.indemnity, currentUser)

                            if (result.isSuccess) {
                                Log.d("micool", "Update succeeded: $result")
                            } else {
                                status.value = indemnityWithUser.indemnity.status ?: "pending"
                                Log.e("micool", "Update failed: $result")
                            }
                        }
                    }
                )
            }
        }
        composable<MainNav.IndemnityEdit> {
            Guard(navController) { currentUser ->
                val args = it.toRoute<MainNav.IndemnityEdit>()
                val viewModel: IndemnityViewModel = hiltViewModel()
                val indemnityDto = viewModel.fetchOne(args.id)
                viewModel.setFormState(indemnityDto)

                val scope = rememberCoroutineScope()
                IndemnityFormUI(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel = viewModel
                ) { data ->
                    scope.launch {
                        val result = viewModel.upsert(data, currentUser)
                        if (result.isSuccess) {
                            Log.d("micool", "good: $result")
                        } else {
                            Log.e("micool", "fail: $result")
                        }
                    }
                }
            }
        }
        composable<MainNav.IndemnityCreate> {
            Guard(navController) { currentUser ->
                val viewModel: IndemnityViewModel = hiltViewModel()

                val scope = rememberCoroutineScope()
                IndemnityFormUI(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel = viewModel
                ) { data ->
                    scope.launch {
                        val result = viewModel.upsert(data, currentUser)
                        if (result.isSuccess) {
                            Log.d("micool", "good: $result")
                        } else {
                            Log.e("micool", "fail: $result")
                        }
                    }
                }
            }
        }
        composable<MainNav.IndemnityList> {
            val indemnityViewModel: IndemnityViewModel = hiltViewModel()
            Guard(navController = navController) { currentUser ->
                val indemnityWithUser = indemnityViewModel.fetchAll(currentUser)
                    IndemnityListUI(
                        navController = navController,
                        indemnityWithUser = indemnityWithUser,
                        currentUser = currentUser,
                    )
            }
        }
        composable<MainNav.OnionEdit> {
            Guard(navController) { currentUser ->
                val args = it.toRoute<MainNav.IndemnityEdit>()
                val viewModel: OnionInsuranceViewmodel = hiltViewModel()
                val onionDto = viewModel.fetchOne(args.id)
                viewModel.setFormState(onionDto)

                val scope = rememberCoroutineScope()
                OnionInsuranceCreate(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel = viewModel
                ) { data ->
                    scope.launch {
                        val result = viewModel.upsert(data, currentUser)
                        if (result.isSuccess) {
                            Log.d("micool", "good: $result")
                        } else {
                            Log.e("micool", "fail: $result")
                        }
                    }
                }
            }
        }
        composable<MainNav.OnionDetails> {
            Guard(navController) { currentUser ->
                val args = it.toRoute<MainNav.OnionDetails>()
                val userViewModel: UserViewModel = hiltViewModel()
                val viewModel: OnionInsuranceViewmodel = hiltViewModel()
                val onionWithUserDto = remember {
                    val complaints = viewModel.fetchOne(args.id)
                    val farmerUserDto = userViewModel.fetchUser(complaints.userId)
                    OnionWithUserDto(complaints, farmerUserDto)
                }
                val onionInsuranceDto = viewModel.fetchOne(args.id)
                val status = rememberSaveable { mutableStateOf(onionInsuranceDto.status ?: "pending") }
                val scope = rememberCoroutineScope()
                OnionInsuranceDetails(
                    title = "Onion Details",
                    onionWithUserDto = onionWithUserDto,
                    currentUser = currentUser,
                    onionInsurance = onionInsuranceDto.copy(status = status.value),
                    status = status,
                    onClickEdit = {
                        navController.navigate(MainNav.OnionEdit(args.id))
                    },
                    onClickLike = { isLike ->
                        scope.launch {
                            val newStatus = if (isLike) "approved" else "rejected"
                            status.value = newStatus
                            scope.launch {
                                onionInsuranceDto.status = newStatus
                                val result = viewModel.upsert(onionInsuranceDto, currentUser)

                                if (result.isSuccess) {
                                    Log.d("micool", "Update succeeded: $result")
                                } else {
                                    status.value = onionInsuranceDto.status ?: "pending"
                                    Log.e("micool", "Update failed: $result")
                                }
                            }
                        }
                    },
                )
            }
        }
        composable<MainNav.OnionCreate> {
            Guard(navController = navController) { currentUser ->
                val viewModel: OnionInsuranceViewmodel = hiltViewModel()
                val scope = rememberCoroutineScope()
                OnionInsuranceCreate(
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
        composable<MainNav.IndemnityCreate> {
            Guard(navController) { currentUser ->
                val viewModel: IndemnityViewModel = hiltViewModel()

                val scope = rememberCoroutineScope()
                IndemnityFormUI(
                    navController = navController,
                    currentUser = currentUser,
                    viewModel = viewModel
                ) { data ->
                    scope.launch {
                        val result = viewModel.upsert(data, currentUser)
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
            val onionInsuranceViewModel: OnionInsuranceViewmodel = hiltViewModel()
            Guard(navController = navController) { currentUser ->
                val onionWithUsers = onionInsuranceViewModel.fetchAll(currentUser)
                OnionInsuranceListUI(
                    navController = navController,
                    onionWithUsers = onionWithUsers,
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
        composable<MainNav.ComplaintList> {
            val viewModel: ComplaintViewModel = hiltViewModel()

            Guard(navController = navController) { currentUser ->
            val complaintWithUser = viewModel.fetchList(currentUser)
                ComplaintListUI(
                    navController = navController,
                    complaintWithUser = complaintWithUser,
                    currentUser = currentUser
                )
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
        composable<MainNav.UserValidId> {
            val args = it.toRoute<MainNav.UserValidId>()
            Guard(navController = navController) { currentUser ->
                val userService: UserService = hiltViewModel<UserViewModel>().userService
                val userDto = userService.fetchOne(args.userId)
                UserValidIdUI(userDto = userDto, userService = userService)
            }
        }
        composable("${MainNav.EditSettings}/{settingType}") { backStackEntry ->
            val settingType = backStackEntry.arguments?.getString("settingType") ?: ""
            Guard(navController = navController) { currentUser ->
                EditSettingsUI(navController, settingType, currentUser)
            }
        }
    }
}