package com.example.agriguard.modules.intro.login.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agriguard.modules.intro.IntroNav
import com.example.agriguard.modules.intro.login.model.dto.LoginDto
import com.example.agriguard.modules.intro.login.service.AuthService
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.service.UserService
import com.example.agriguard.modules.shared.ext.hashPassword
import com.example.agriguard.modules.shared.ext.verifyPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val userService: UserService,
    application: Application
): ViewModel()  {
    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("Preferences", Context.MODE_PRIVATE)

    fun login(loginDto: LoginDto): Boolean {

        val userDto = authService.login(loginDto)

        if (userDto != null) {
            if (userDto.id == null) return false
            saveUserSession(userDto.id ?: "")
            return true
        }
        return false

//        return true
    }

    private fun saveUserSession(userId: String) {
        val editor = sharedPreferences.edit()
        editor.putString("logged_in_user_id", userId)
        editor.apply()
    }

    fun getLoggedInUserId(): String? {
        return sharedPreferences.getString("logged_in_user_id", null)
    }

    fun getUserLocal(): UserDto? {
        val userId = getLoggedInUserId()

        if (userId != null){
            return userService.fetchOne(userId)
        }

        return null
    }

    private fun clearUserSession() {
        sharedPreferences.edit().clear().apply()
    }

    fun logout(navController: NavController) {
        val userId = getLoggedInUserId()
        if (userId != null) {
            clearUserSession()
            navController.navigate(IntroNav) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
}