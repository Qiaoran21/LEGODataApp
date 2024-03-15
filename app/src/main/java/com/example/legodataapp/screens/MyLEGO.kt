package com.example.legodataapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.legodataapp.Product
import com.example.legodataapp.ui.theme.Cream

@Composable
fun MyLEGOScreen(navController: NavController, hasRating: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        Product(navController = navController, hasRating = false)
        Product(navController = navController, hasRating = false)
        Product(navController = navController, hasRating = false)
    }
}
