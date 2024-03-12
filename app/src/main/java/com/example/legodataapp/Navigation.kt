package com.example.legodataapp

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.legodataapp.ui.theme.Cream

@Composable
fun BottomNavBar(navController: NavController, modifier: Modifier) {
    val navItems = listOf(NavItem.Home, NavItem.WishList, NavItem.MyLEGO)
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    NavigationBar(modifier = modifier) {
        navItems.forEachIndexed { index, item ->
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

@Composable
fun getCurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}