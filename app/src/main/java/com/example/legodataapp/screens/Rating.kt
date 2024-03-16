package com.example.legodataapp.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.fontFamily
import com.example.legodataapp.R

@Composable
fun RatingScreen() {
    var isButtonClicked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(25.dp),
            border = BorderStroke(2.dp, DarkYellow),
            colors = CardDefaults.cardColors(containerColor = Cream)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "LEGO Data logo",
                    modifier = Modifier.size(200.dp)
                )

                Text(
                    text = "Did You Enjoy It?",
                    color = Brown,
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )

                var rating by remember { mutableIntStateOf(0) }

                RatingBar(modifier = Modifier.size(50.dp), rating = rating) {
                    rating = it
                }

                if (rating != 0) {
                    val emoji = when {
                        rating == 1 -> "🥺"
                        rating == 2 -> "😐"
                        rating == 3 -> "😊"
                        rating == 4 -> "😃"
                        else -> "😍"
                    }
                    Text(
                        text = emoji,
                        fontSize = 100.sp,
                        modifier = Modifier.padding(20.dp)
                    )

                    CommentField(isButtonClicked) { isButtonClicked = it }
                    SubmitButton(isButtonClicked) { isButtonClicked = it }
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0,
    starNumber: Int = 5,
    starColour: Color = DarkYellow,
    onRatingChange: (Int) -> Unit
) {
    Row(modifier = Modifier) {
        for (index in 1 .. starNumber) {
            Icon(
                modifier = modifier.clickable { onRatingChange(index) },
                contentDescription = "",
                tint = starColour,
                imageVector = if (rating >= index) {
                    Icons.Rounded.Star
                } else {
                    Icons.Rounded.StarOutline
                }
            )
        }
    }
}

@Composable
fun CommentField(isButtonClicked: Boolean, onButtonClicked:(Boolean) -> Unit) {
    var comment by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.padding(10.dp),
        value = comment,
        onValueChange = {comment = it},
        placeholder = {
            Text(
                text = "Write a review",
                fontFamily = fontFamily,
                color = Brown
            )},
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Cream,
            unfocusedContainerColor = Cream,
            cursorColor = DarkYellow,
            focusedBorderColor = DarkYellow
        ),
        enabled = !isButtonClicked
    )
    if (isButtonClicked) {
        onButtonClicked(true)
    }
}

@Composable
fun SubmitButton(isButtonClicked: Boolean, onButtonClicked:(Boolean) -> Unit) {
    var isEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current

    if (isButtonClicked) {
        isEnabled = false
    }

    Button(
        onClick = {
            Toast.makeText(
                context,
                "Thank you for the feedback!",
                Toast.LENGTH_LONG
            ).show()
            onButtonClicked(true)
        },
        enabled = isEnabled,
        modifier = Modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(DarkYellow)
    ) {
        Text(
            text = "Submit",
            color = Brown,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold
        )
    }
}