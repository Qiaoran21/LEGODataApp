package com.example.legodataapp.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.legodataapp.Login
import com.example.legodataapp.Logout
import com.example.legodataapp.Product
import com.example.legodataapp.model.AuthViewModel
import com.example.legodataapp.model.User
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.LightBrown
import com.example.legodataapp.ui.theme.fontFamily

@Composable
fun AccountScreen(navController: NavController, viewModel: AuthViewModel) {
    var buttonText: String
    var onClickAction: () -> Unit
    val context = LocalContext.current
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
            text = if (viewModel.userIsAuthenticated) "${viewModel.user.name} is logged in"
                   else "Log in to use all features",
            color = Brown,
            fontSize = 20.sp
        )

        if (viewModel.userIsAuthenticated) {
            onClickAction = {
                Logout(onLogoutSuccess = {
                    viewModel.logout()
                }, context)
            }
            buttonText = "Logout"
        } else {
            onClickAction = {
                Login(onLoginSuccess = {Credentials ->
                        viewModel.login(User(Credentials.idToken))
                }, context)
            }
            buttonText = "Login"
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = onClickAction, colors = ButtonDefaults.buttonColors(DarkYellow)) {
            Text(
                text = buttonText,
                fontSize = 15.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                color = Brown)
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Other Settings",
            color = Brown,
            fontSize = 30.sp
        )
    }
}
