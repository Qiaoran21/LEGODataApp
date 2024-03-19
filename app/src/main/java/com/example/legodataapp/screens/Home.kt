package com.example.legodataapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.fontFamily
import com.example.legodataapp.R
import com.example.legodataapp.ui.theme.LightBrown

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        Image(
            painter = painterResource(id = R.drawable.city),
            contentDescription = "LEGO city",
            modifier = Modifier.size(420.dp)
        )
        Text(
            text = "Themes",
            color = LightBrown,
            fontSize = 40.sp,
            fontFamily = fontFamily
        )
        Row {
            legoTheme()
            legoTheme()
            legoTheme()
        }
    }
}


@Composable
fun legoTheme() {
    Row (modifier = Modifier.background(Cream)) {
        Card (
            modifier = Modifier.padding(5.dp),
            border = BorderStroke(2.dp, DarkYellow),
            colors = CardDefaults.cardColors(containerColor = Cream)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
            ) {
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "LEGO Data logo",
                    modifier = Modifier.size(100.dp))
                Column {
                    Text(
                        text = "LEGO Theme",
                        color = Brown,
                        fontSize = 15.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}