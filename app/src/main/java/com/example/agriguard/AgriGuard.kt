package com.example.agriguard

import android.app.Application
import android.content.Context
import com.example.agriguard.modules.intro.login.service.AuthService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class AgriGuard : Application() {
    @Inject
    lateinit var authService: AuthService

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            val sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE)
            val currentUserId = sharedPreferences.getString("logged_in_user_id", null)
            if (currentUserId != null) {
                authService.loadSpecificDataByUser(currentUserId)
            }
        }
    }
}