package com.example.agriguard.modules.intro

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.agriguard.modules.intro.login.ui.ForgotPasswordUI
import com.example.agriguard.modules.intro.login.ui.LogInUI
import com.example.agriguard.modules.intro.login.ui.ResetPasswordUI
import com.example.agriguard.modules.intro.login.ui.TokenVerificationUI
import com.example.agriguard.modules.intro.splash.SplashUI
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.viewmodel.UserViewModel

fun NavGraphBuilder.introGraph(navController: NavController) {
    navigation<IntroNav>(startDestination = IntroNav.Splash) {

        composable<IntroNav.Splash> {
            SplashUI(navController)
        }

        composable<IntroNav.Login> {
            LogInUI(userDto = UserDto(), navController)
        }

        composable<IntroNav.ForgotPassword> {
            ForgotPasswordUI(navController)
        }

        composable<IntroNav.TokenVerification> {
            val args = it.toRoute<IntroNav.TokenVerification>()
            val userViewModel: UserViewModel = hiltViewModel()
            val userDto = userViewModel.fetchUserByEmail(args.email)
            if (userDto != null) {
                TokenVerificationUI(email = userDto.email, navController = navController)
            }
        }

        composable<IntroNav.ResetPassword> {
            val args = it.toRoute<IntroNav.ResetPassword>()
            val userViewModel: UserViewModel = hiltViewModel()
            val userDto = userViewModel.fetchUserByEmailAndToken(args.email, args.passwordToken)
            if(userDto != null){
                ResetPasswordUI(email = userDto.email, token = userDto.resetPasswordToken!!, navController = navController)
            }
        }
    }
}