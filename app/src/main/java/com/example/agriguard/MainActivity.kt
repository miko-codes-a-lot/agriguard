package com.example.agriguard

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.modules.intro.IntroNav
import com.example.agriguard.modules.intro.introGraph
import com.example.agriguard.modules.intro.login.viewmodel.LoginViewModel
import com.example.agriguard.modules.intro.login.viewmodel.UserState
import com.example.agriguard.modules.intro.login.viewmodel.UserStateViewModel
import com.example.agriguard.modules.main.MainNav
import com.example.agriguard.modules.main.mainGraph
import com.example.agriguard.ui.theme.AgriguardTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userState by viewModels<UserStateViewModel>()
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val loginViewModel: LoginViewModel = hiltViewModel()

            AgriguardTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(UserState provides userState) {
                    val startingDestination = getStartDestination(loginViewModel)
                    NavHost(navController = navController, startDestination = startingDestination) {
                        introGraph(navController)
                        mainGraph(navController)
                    }
                }
            }
        }
    }

    private fun getStartDestination(loginViewModel: LoginViewModel): Any {
        if (loginViewModel.getLoggedInUserId() != null){
            return MainNav
        }
        return IntroNav
    }

    fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            Toast.makeText(this, "No storage permission required for Android 10+", Toast.LENGTH_SHORT).show()
        }
    }
}