package com.example.legodataapp.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.legodataapp.Login
import com.example.legodataapp.Logout
import com.example.legodataapp.Product
import com.example.legodataapp.model.AuthViewModel
import com.example.legodataapp.model.User
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow

@Composable
fun AccountScreen(navController: NavController, viewModel: AuthViewModel) {
    var buttonText: String
    var onClickAction: () -> Unit
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        Text(
            text = "Account",
            color = DarkYellow,
            fontSize = 50.sp
        )
        Text(
            text = if (viewModel.userIsAuthenticated) "${viewModel.user.name} is logged in"
                   else "Log in to use all features",
            color = DarkYellow,
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

        Button(onClick = onClickAction) {
            Text(text = buttonText)
        }

        Text(
            text = "Other Settings",
            color = DarkYellow,
            fontSize = 30.sp
        )
    }
}
