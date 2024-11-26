package com.example.agriguard.modules.intro

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.agriguard.modules.intro.login.LogInUI
import com.example.agriguard.modules.intro.splash.SplashUI
import com.example.agriguard.modules.main.user.model.dto.UserDto

fun NavGraphBuilder.introGraph(navController: NavController) {
    navigation<IntroNav>(startDestination = IntroNav.Splash) {

        composable<IntroNav.Splash> {
            SplashUI(navController)
        }
        composable<IntroNav.LogIn> {
            LogInUI(userDto = UserDto(), navController)
        }
    }
}