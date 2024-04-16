package com.example.legodataapp.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.legodataapp.data.LegoSet
import com.example.legodataapp.ui.theme.Cream
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.legodataapp.NavItem
import com.example.legodataapp.model.AuthViewModel
import com.example.legodataapp.model.SetViewModel
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.fontFamily
import com.google.firebase.firestore.FirebaseFirestore
import java.net.URLEncoder

@Composable
fun WishListScreen(
    navController: NavController,
    hasRating: Boolean,
    wishlistItems: LiveData<List<LegoSet>>,
    setViewModel: SetViewModel,
    authViewModel: AuthViewModel
) {
    val items by wishlistItems.observeAsState(initial = emptyList())

    var isAuthenticated by remember { mutableStateOf(authViewModel.userIsAuthenticated) }

    Column (modifier = Modifier.background(Cream)
                .fillMaxSize()
                .padding(top = 60.dp, bottom = 75.dp)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Cream)
        ) {
            if (items.isNotEmpty()) {
                items(items) { item ->
                    WishListItem(navController, hasRating, item, setViewModel, authViewModel){ selectedLegoSet ->
                        //navController.navigate(NavItem.Product.route)
                        var encodedImgUrl = URLEncoder.encode(selectedLegoSet.set_img_url, "UTF-8")
                        var encodedSetUrl = URLEncoder.encode(selectedLegoSet.set_url, "UTF-8")
                        if(encodedSetUrl==""){
                            encodedSetUrl = "None"
                        }
                        if(encodedImgUrl==""){
                            encodedImgUrl = "None"
                        }
                        navController.navigate(
                            NavItem.Product.route +
                                "/${"2022-01-01"}" +
                                "/${selectedLegoSet.name}"+
                                "/${selectedLegoSet.num_parts}"+
                                "/${encodedImgUrl}"+
                                "/${selectedLegoSet.set_num}"+
                                "/${encodedSetUrl}"+
                                "/${selectedLegoSet.theme_id}"+
                                "/${selectedLegoSet.year}"+
                                "/${isAuthenticated}"
                        )
                    }
                }
            } else {
                item {
                    Text("Your wishlist is empty!")
                }
            }
        }
    }
}

@Composable
fun WishListItem(
    navController: NavController,
    hasRating: Boolean,
    legoSet: LegoSet,
    setViewModel: SetViewModel,
    authViewModel: AuthViewModel,
    onLegoSetClicked: (LegoSet) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        border = BorderStroke(2.dp, DarkYellow),
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp).clickable {
                    onLegoSetClicked(legoSet)
                }
        ) {
            Row {
                AsyncImage(
                    model = legoSet.set_img_url,
                    contentDescription = legoSet.name,
                    modifier = Modifier.size(100.dp),
                    alignment = Alignment.Center
                )
                Column {
                    Text(
                        text = legoSet.name,
                        color = Brown,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text("Set Number: ${legoSet.set_num}", color = Brown)
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
            val context = LocalContext.current
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        setViewModel.removeFromWishlist(legoSet, authViewModel.user.value?.id.toString())
                        showToast(context, "Item removed from Wishlist!")
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "Remove",
                        color = Brown,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
