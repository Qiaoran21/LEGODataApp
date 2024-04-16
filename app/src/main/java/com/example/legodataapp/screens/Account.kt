package com.example.legodataapp.screens

// Imports for Preferences
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.auth0.android.result.Credentials
import com.example.legodataapp.Login
import com.example.legodataapp.Logout
import com.example.legodataapp.model.AuthViewModel
import com.example.legodataapp.model.SetViewModel
import com.example.legodataapp.model.User
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkText
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.DarkerYellow
import com.example.legodataapp.ui.theme.LEGODataAppTheme
import com.example.legodataapp.ui.theme.LightBrown
import com.example.legodataapp.ui.theme.LightText
import com.example.legodataapp.ui.theme.fontFamily

@Composable
fun AccountScreen(navController: NavController, viewModel: AuthViewModel, setViewModel: SetViewModel, updateContainerColor: (Boolean) -> Unit) {
    var buttonText: String
    var onClickAction: () -> Unit
    val context = LocalContext.current

    // Preference options using simple remember with mutableStateOf
    var isDarkMode by rememberSaveable { mutableStateOf(loadDarkModeState(context)) }
    var isSoundEffects by rememberSaveable { mutableStateOf(loadSoundEffectsState(context)) }
    fun toggleDarkMode() {
        isDarkMode = !isDarkMode
        saveDarkModeState(isDarkMode, context)
    }
    fun toggleSoundEffects() {
        isSoundEffects = !isSoundEffects
        saveSoundEffectsState(isSoundEffects, context)
    }
    LEGODataAppTheme(darkMode = isDarkMode) {
        Column(

            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Cream)
                .padding(20.dp)
        ) {
            Text(
                text = "Account",
                color = LightBrown,
                fontSize = 50.sp
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Text(
                text = if (viewModel.userIsAuthenticated) "${viewModel.user.value?.name} is logged in"
                else "Log in to use all features",
                color = Brown,
                fontSize = 20.sp
            )

            if (viewModel.userIsAuthenticated) {
                onClickAction = {
                    Logout(onLogoutSuccess = {
                        viewModel.logout()
                        deleteUserState(context)
                        viewModel.userIsAuthenticated = false
                        setViewModel.updateUserId("")
                    }, context)
                }
                buttonText = "Logout"
            } else {
                onClickAction = {
                    Login(onLoginSuccess = {Credentials ->
                        viewModel.login(User(Credentials.idToken))
                        saveUserState(context, Credentials)
                        setViewModel.updateUserId(viewModel.user.value?.id.toString())
                    }, context)
                }
                buttonText = "Login"
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = onClickAction, colors = ButtonDefaults.buttonColors(if(isDarkMode) DarkerYellow else DarkYellow)) {
                Text(
                    text = buttonText,
                    fontSize = 15.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = if(isDarkMode) LightText else DarkText)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            // Preference added for configuring Dark Mode
            ToggleButton(
                checked = isDarkMode,
                onCheckedChange = {
                    toggleDarkMode()
                    updateContainerColor(isDarkMode)
                },
                icon = if (isDarkMode) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                text = if (isDarkMode) "Turn Off Dark Mode" else "Turn On Dark Mode",
                color = Brown
            )
            Spacer(modifier = Modifier.padding(10.dp))
            // Preference added for configuring Sound Effects
            ToggleButton(
                checked = isSoundEffects,
                onCheckedChange = {
                    toggleSoundEffects()
                },
                icon = if (isSoundEffects) Icons.Filled.Speaker else Icons.Filled.DoNotDisturb,
                text = if (isSoundEffects) "Turn Off Sound Effects" else "Turn On Sound Effects",
                color = Brown
            )
        }
    }


}

@Composable
fun ToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: ImageVector,
    text: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onCheckedChange(!checked) },
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = if (checked) Color.Gray else Color.White,
                    shape = CircleShape
                ),
            content = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Toggle",
                    tint = if (checked) Color.White else Color.Gray
                )
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = color)
    }
}

private const val DARK_MODE_PREF_KEY = "dark_mode_preference"
private const val SOUND_EFFECTS_PREF_KEY = "sound_effects_preference"
private const val USER_ID_PREF_KEY = "user_id_preference"

// Dark Mode
private fun loadDarkModeState(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(DARK_MODE_PREF_KEY, false)
}

private fun saveDarkModeState(isDarkMode: Boolean, context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean(DARK_MODE_PREF_KEY, isDarkMode).apply()
}

// Sound Effects
private fun loadSoundEffectsState(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(SOUND_EFFECTS_PREF_KEY, false)
}

private fun saveSoundEffectsState(isSoundEffects: Boolean, context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean(SOUND_EFFECTS_PREF_KEY, isSoundEffects).apply()
}

private fun saveUserState(context: Context, credentials: Credentials) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString(USER_ID_PREF_KEY, credentials.idToken).apply()
}

private fun deleteUserState(context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString(USER_ID_PREF_KEY, "").apply()
    sharedPreferences.edit().remove(USER_ID_PREF_KEY).apply()
}

/**
@SuppressLint("CommitPrefEdits")
private fun saveUserState(context: Context, creds: Credentials){
    var masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "EncryptedPrefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    val editor = sharedPreferences.edit()
    editor.putString("User_ID_Token", creds.idToken).apply()
}

@SuppressLint("CommitPrefEdits")
private fun deleteUserState(context: Context){
    var masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "EncryptedPrefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    val editor = sharedPreferences.edit()
    editor.clear().apply()
}
**/