package com.example.legodataapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.ui.graphics.Color
import com.example.legodataapp.ui.theme.Brown
import com.example.movieapp.R

sealed class NavItem(
    val route: String,
    val title: String,
    val icon: Int,
    val titleColor: Color,
    val contentDescription: String
) {
    object Home : NavItem(
        route = "Home",
        title = "HOME",
        icon = R.drawable.home_icon,
        titleColor = Brown,
        contentDescription = "Home"
    )

    object WishList : NavItem(
        route = "Wish List",
        title = "WISH LIST",
        icon = R.drawable.heart_icon,
        titleColor = Brown,
        contentDescription = "Wish List"
    )

    object MyLEGO : NavItem(
        route = "My LEGO",
        title = "MY LEGO",
        icon = R.drawable.favoritefolder_icon,
        titleColor = Brown,
        contentDescription = "My LEGO"
    )

    object Help : NavItem(
        route = "Help",
        title = "HELP",
        icon = R.drawable.help_icon,
        titleColor = Brown,
        contentDescription = "Help"
    )
}