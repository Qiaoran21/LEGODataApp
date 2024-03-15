package com.example.legodataapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.fontFamily

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
            color = DarkYellow,
            fontSize = 50.sp
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Email: service@legodata.com",
            color = Brown,
            fontSize = 23.sp
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Phone Number: 123-456-7890",
            color = Brown,
            fontSize = 23.sp
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Address: 123 Street, City, Country 1A2 3C4",
            color = Brown,
            fontSize = 23.sp
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Social media...",
            color = Brown,
            fontSize = 23.sp
        )
    }
}
