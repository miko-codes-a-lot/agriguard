package com.example.agriguard.modules.intro.login.service

import android.util.Log
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.shared.emailTwilio.RetrofitClient
import javax.inject.Inject

class EmailService @Inject constructor() {

    suspend fun requestPasswordResetToken(email: String): Boolean {
        return try {
            val userDto = UserDto(email = email.trim())
            val response = RetrofitClient.apiService.requestToken(userDto)

            if (response.isSuccessful) {
                Log.d("EmailService", "Password reset token sent successfully for mobile: $email")
                true
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("EmailService", "Failed to send token. Error: $errorBody")
                false
            }        } catch (exception: Exception) {
            exception.printStackTrace()
            false
        }
    }

    suspend fun verifyResetToken(email: String, token: String): Boolean {
        return try {
            val requestBody = mapOf(
                "email" to email,
                "token" to token
            )
            val response = RetrofitClient.apiService.verifyToken(requestBody)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}