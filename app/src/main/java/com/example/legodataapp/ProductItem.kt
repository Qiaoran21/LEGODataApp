package com.example.legodataapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.movieapp.R

@Composable
fun Product() {
    Column {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            border = BorderStroke(1.dp, DarkYellow),
            colors = CardDefaults.cardColors(containerColor = Cream)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                ) {
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "LEGO Data logo",
                    modifier = Modifier.size(120.dp))
                Column {
                    Text(text = "Name ", color = Brown, fontSize = 25.sp)
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = "Pieces:", color = Brown)
                    Text(text = "Item Number:", color = Brown)
                    Text(text = "Theme:", color = Brown)
                }
            }
        }
    }
}