package com.example.legodataapp

import androidx.compose.ui.graphics.Color
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.R

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

    object Account : NavItem(
        route = "Account",
        title = "ACCOUNT",
        icon = R.drawable.user_icon2,
        titleColor = Brown,
        contentDescription = "Account"
    )

    object Help : NavItem(
        route = "Help",
        title = "HELP",
        icon = R.drawable.help_icon,
        titleColor = Brown,
        contentDescription = "Help"
    )

    object Product : NavItem(
        route = "Product",
        title = "PRODUCT",
        icon = R.drawable.help_icon,
        titleColor = Brown,
        contentDescription = "Product"
    )

    object Rating : NavItem(
        route = "Rating",
        title = "RATING",
        icon = R.drawable.help_icon,
        titleColor = Brown,
        contentDescription = "Rating"
    )

    object QrCode : NavItem(
        route = "QRCode",
        title = "QRCode Results",
        icon = R.drawable.help_icon,
        titleColor = Brown,
        contentDescription = "QRCode result"
    )
}