package com.example.legodataapp

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.Pink40
import com.example.legodataapp.ui.theme.fontFamily

@Composable
fun BottomNavBar(navController: NavController, modifier: Modifier) {
    val navItems = listOf(NavItem.Home, NavItem.WishList, NavItem.MyLEGO)
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    NavigationBar(containerColor = DarkYellow) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = false,
//                label = { Text(
//                    item.title,
//                    color = item.titleColor,
//                    fontSize = 10.sp,
//                    fontFamily = fontFamily) },
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                onClick = { navController.navigate(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Brown
                )
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
        composable(NavItem.Help.route) { HelpScreen() }
    }
}

@Composable
fun getCurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}