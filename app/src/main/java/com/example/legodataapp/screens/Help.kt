package com.example.legodataapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.LightBrown

@Composable
fun HelpScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(20.dp)
    ) {
        Text(
            text = "Contact Us",
            color = LightBrown,
            fontSize = 50.sp
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Email: ",
            color = Brown,
            fontSize = 23.sp
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Phone Number: ",
            color = Brown,
            fontSize = 23.sp
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Address: ",
            color = Brown,
            fontSize = 23.sp
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Social media:",
            color = Brown,
            fontSize = 23.sp
        )
    }
}
