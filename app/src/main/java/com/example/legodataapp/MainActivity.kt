package com.example.legodataapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.legodataapp.model.AuthViewModel
import com.example.legodataapp.ui.theme.LEGODataAppTheme
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.legodataapp.model.SetViewModel


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val setViewModel: SetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        authViewModel.user.observe(this){ user ->
            user?.let{
                setViewModel.updateUserId(user.id)
            }
        }

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
                    isDarkMode = isDarkMode
                )
            }
        }
    }
}



