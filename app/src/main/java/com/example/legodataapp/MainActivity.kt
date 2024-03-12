package com.example.legodataapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.navigation.compose.rememberNavController
import com.example.legodataapp.ui.theme.LEGODataAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LEGODataAppTheme {
                val navController = rememberNavController()
                Surface() {
                    MainScreen(navController = navController)
                }
            }
        }
    }
}

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
                onClick = { navController.navigate(item.route) {
                    popUpTo((NavItem.MyLEGO.route))
                } })
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavBar(navController = navController)
            }
        })
    { NavigationScreens(navController = navController) }
}
