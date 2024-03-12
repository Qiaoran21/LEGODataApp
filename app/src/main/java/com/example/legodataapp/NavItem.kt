package com.example.legodataapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.DarkYellow

sealed class NavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val titleColor: Color
) {
    object Home : NavItem(
        route = "Home",
        title = "HOME",
        icon = Icons.Rounded.Home,
        titleColor = Brown
    )

    object WishList : NavItem(
        route = "Wish List",
        title = "WISH LIST",
        icon = Icons.Rounded.Savings,
        titleColor = Brown
    )

    object MyLEGO : NavItem(
        route = "My LEGO",
        title = "MY LEGO",
        icon = Icons.Default.Star,
        titleColor = Brown
    )
}