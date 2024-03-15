package com.example.legodataapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.Pink40
import com.example.legodataapp.ui.theme.fontFamily
import com.example.movieapp.R

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
            color = DarkYellow,
            fontSize = 40.sp,
            fontFamily = fontFamily,
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
    Row {
        Card (
            modifier = Modifier.padding(5.dp),
            border = BorderStroke(1.dp, DarkYellow),
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
                        fontSize = 15.sp)
                }
            }
        }
    }
}