package com.example.legodataapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Home: NavItem(
        route = "Home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object WishList: NavItem(
        route = "Wish List",
        title = "Wish List",
        icon = Icons.Default.Favorite
    )

    object MyLEGO: NavItem(
        route = "My LEGO",
        title = "My LEGO",
        icon = Icons.Default.Star
    )
}