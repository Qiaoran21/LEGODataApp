package com.example.legodataapp

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavBar(navController: NavController) {
    val navItems = listOf(NavItem.Home, NavItem.WishList, NavItem.MyLEGO)
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    NavigationBar {
        navItems.forEachIndexed{index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                label = { Text(item.title) },
                icon = { Icon(item.icon, contentDescription = item.title) },
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

@Composable
fun NavigationScreens(navController: NavHostController) {
    NavHost(navController, startDestination = NavItem.Home.route) {
        composable(NavItem.WishList.route) { WishListScreen() }
        composable(NavItem.MyLEGO.route) { MyLEGOScreen() }
        composable(NavItem.Home.route) { HomeScreen() }
    }
}