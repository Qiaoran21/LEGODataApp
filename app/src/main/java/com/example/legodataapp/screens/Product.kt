package com.example.legodataapp.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.legodataapp.R
import com.example.legodataapp.data.LegoSet
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkText
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.fontFamily

@Composable
fun ProductScreen(
    legoSet: LegoSet,
    isAuthenticated: Boolean,
    onAddWishlist: () -> Unit,
    onAddMyLegoList: () -> Unit,
    context: Context
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream),
        contentAlignment = Alignment.Center
    ) {
        Card (
            modifier = Modifier.padding(25.dp),
            border = BorderStroke(2.dp, DarkYellow),
            colors = CardDefaults.cardColors(containerColor = Cream)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                AsyncImage(
                    model = legoSet.set_img_url,
                    contentDescription = legoSet.name,
                    modifier = Modifier.size(300.dp),
                    alignment = Alignment.Center
                )
                Text(
                    text = legoSet.name,
                    color = Brown,
                    fontSize = 25.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "Set Number: ${legoSet.set_num}", color = Brown, fontSize = 20.sp)
                Text("Pieces: ${legoSet.num_parts}", color = Brown, fontSize = 20.sp)
                Text(text = "Theme: ${legoSet.theme_id}", color = Brown, fontSize = 20.sp)
                Text(text = "Year: ${legoSet.year}", color = Brown, fontSize = 20.sp)
                if(isAuthenticated) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        // Add to Wishlist Button
                        Button(
                            onClick = {
                                onAddWishlist()
                                showToast(context, "Added to Wishlist!")
                            },
                            Modifier
                                .height(55.dp)
                                .padding(5.dp)) {
                            Image(painter = painterResource(
                                id = R.drawable.heart_icon),
                                contentDescription = "Wishlist icon",
                                colorFilter = ColorFilter.tint(color = DarkText)
                            )
//                            Text(
//                                text = "Add to Wishlist",
//                                fontSize = 15.sp,
//                                textAlign = TextAlign.Center
//                            )
                        }
                        // Add to My LEGO Button
                        Button(
                            onClick = {
                                onAddMyLegoList()
                                showToast(context, "Added to My LEGO!")
                            },
                            Modifier
                                .height(55.dp)
                                .padding(5.dp)) {
                            Image(painter = painterResource(
                                id = R.drawable.favoritefolder_icon),
                                contentDescription = "My Lego icon",
                                colorFilter = ColorFilter.tint(color = DarkText)
                            )
//                            Text(
//                                text = "Add to My LEGO",
//                                fontSize = 15.sp,
//                                textAlign = TextAlign.Center
//                            )
                        }
                    }
                }
            }
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}