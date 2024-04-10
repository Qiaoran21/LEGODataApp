package com.example.legodataapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.legodataapp.model.AuthViewModel
import com.example.legodataapp.ui.theme.LEGODataAppTheme
import androidx.activity.viewModels

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.auth0.android.result.Credentials

//import com.example.legodataapp.model.SearchViewModel
import com.example.legodataapp.model.SetViewModel
import com.example.legodataapp.model.User


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val setViewModel: SetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val loadSettings = LoadSettings()
            val isDarkMode = loadSettings.loadDarkModeState(this@MainActivity)

            LEGODataAppTheme (darkMode = isDarkMode) {
                val navController = rememberNavController()
                MainScreen(
                  navController = navController,
                  modifier = Modifier,
                  authViewModel,
                  setViewModel,
                  context = applicationContext,
                  isDarkMode)
            }
        }
    }
}

