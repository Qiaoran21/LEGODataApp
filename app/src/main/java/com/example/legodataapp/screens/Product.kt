package com.example.legodataapp.screens

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.legodataapp.R
import com.example.legodataapp.data.LegoSet
import com.example.legodataapp.model.AuthViewModel
import com.example.legodataapp.model.SetViewModel
import com.example.legodataapp.model.User
import com.example.legodataapp.network.RetrofitInstanceLegoTheme
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkText
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.DarkerYellow
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun ProductScreen(
    legoSet: LegoSet,
    authViewModel: AuthViewModel,
    setViewModel: SetViewModel,
    isAuthenticated: Boolean,
    onAddWishlist: () -> Unit,
    onAddMyLegoList: () -> Unit,
    context: Context,
    navController: NavHostController
) {
    val legoDB = Firebase.firestore
    val (wishlistItems, setWishlistItems) = remember { mutableStateOf<List<LegoSet>>(emptyList()) }
    var themeName by rememberSaveable { mutableStateOf("") }

    val wishlist by rememberSaveable { mutableStateOf(setViewModel.wishlistSets.value ?: emptyList()) }
    val legolist by rememberSaveable { mutableStateOf(setViewModel.myLegoListItems.value ?: emptyList()) }

    var isInWishlist by remember { mutableStateOf(wishlist.any { it.set_num == legoSet.set_num }) }
    var isInLegolist by remember { mutableStateOf(legolist.any { it.set_num == legoSet.set_num }) }

    fun updateIsInLegolist(){
        isInLegolist = !isInLegolist
    }

    fun updateIsInWishlist(){
        isInWishlist = !isInWishlist
    }

    LaunchedEffect(Unit) {
        try {
            themeName = RetrofitInstanceLegoTheme.getThemeName(legoSet.theme_id)
        } catch (e: Exception) {
            themeName = legoSet.theme_id.toString()
        }
    }

    Column (modifier = Modifier
        .background(Cream)
        .fillMaxSize()
        .padding(top = 60.dp, bottom = 75.dp)
    ) {
        LazyColumn() {
            item{
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Brown
                    )
                }
            }
            item {
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
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text(
                                text = "Set Number: ${legoSet.set_num}",
                                color = Brown,
                                fontSize = 20.sp
                            )
                            Text("Pieces: ${legoSet.num_parts}", color = Brown, fontSize = 20.sp)
                            Text(
                                text = "Theme: ${themeName}",
                                color = Brown,
                                fontSize = 20.sp
                            )
                            Text(text = "Year: ${legoSet.year}", color = Brown, fontSize = 20.sp)
                            if (isAuthenticated) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    // Add to Wishlist Button
                                    if(!isInWishlist) {
                                        Button(
                                            onClick = {
                                                val set = hashMapOf(
                                                    "Name" to legoSet.name,
                                                    "Set Number" to legoSet.set_num,
                                                    "Set Image" to legoSet.set_img_url
                                                )
                                                val wishlistCollection = legoDB.collection("Users")
                                                    .document(authViewModel.user.value?.id.toString())
                                                    .collection("Wishlist")
                                                wishlistCollection.add(legoSet)
                                                    .addOnSuccessListener { documentReference ->
                                                        onAddWishlist()
                                                        updateIsInWishlist()
                                                        showToast(context, "Added to Wishlist!")
                                                    }
                                            },
                                            Modifier
                                                .height(55.dp)
                                                .padding(5.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.heart_icon),
                                                contentDescription = "Wishlist icon",
                                                colorFilter = ColorFilter.tint(color = DarkText)
                                            )
                                        }
                                    }

                                    // Add to My LEGO Button
                                    if(!isInLegolist) {
                                        Button(
                                            onClick = {
                                                val set = hashMapOf(
                                                    "Name" to legoSet.name,
                                                    "Set Number" to legoSet.set_num,
                                                    "Set Image" to legoSet.set_img_url
                                                )
                                                val myLegoCollection = legoDB.collection("Users")
                                                    .document(authViewModel.user.value?.id.toString())
                                                    .collection("My Lego")
                                                myLegoCollection.add(legoSet)
                                                    .addOnSuccessListener { documentReference ->
                                                        onAddMyLegoList()
                                                        updateIsInLegolist()
                                                        showToast(context, "Added to My LEGO!")
                                                    }
                                            },
                                            Modifier
                                                .height(55.dp)
                                                .padding(5.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.favoritefolder_icon),
                                                contentDescription = "My Lego icon",
                                                colorFilter = ColorFilter.tint(color = DarkText)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}