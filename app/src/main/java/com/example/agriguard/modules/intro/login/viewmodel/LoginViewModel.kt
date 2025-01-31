package com.example.agriguard.modules.intro.login.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agriguard.modules.intro.IntroNav
import com.example.agriguard.modules.intro.login.model.dto.LoginDto
import com.example.agriguard.modules.intro.login.service.AuthService
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    }

    suspend fun loadSpecificDataByUser() {
        val userId = getLoggedInUserId()!!
        authService.loadSpecificDataByUser(userId)
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
        clearUserSession()
        navController.navigate(IntroNav) {
            popUpTo<IntroNav.Login> { inclusive = true }
            launchSingleTop = true
        }
    }

    fun request(email: String, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val success = authService.requestOTP(email)
                if (success) {
                    callback(true, "OTP successfully generated and saved to the database.")
                } else {
                    callback(false, "Failed to generate OTP.")
                }
            } catch (e: Exception) {
                callback(false, e.message)
            }
        }
    }

    fun verifyToken(email: String, token: String, callback: (Boolean, String?) -> Unit) {
        if (email.isBlank() || token.isBlank()) {
            callback(false, "Email and token cannot be blank.")
            return
        }

        viewModelScope.launch {
            try {
                val isTokenValid = authService.verifyResetToken(email, token)
                if (isTokenValid) {
                    callback(true, null)
                }
            } catch (e: Exception) {
                callback(false, "Invalid token. Please try again.")
            }
        }
    }

    fun resetPassword(email: String, token: String, newPassword: String, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val success = userService.saveNewPassword(email, token, newPassword)
                if (success) {
                    callback(true, null)
                } else {
                    callback(false, "Failed to reset password. Invalid token or email.")
                }
            } catch (e: Exception) {
                callback(false, "Error resetting password: ${e.localizedMessage}")
            }
        }
    }
}