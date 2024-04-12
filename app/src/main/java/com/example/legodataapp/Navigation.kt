package com.example.legodataapp

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.legodataapp.model.AuthViewModel
import com.example.legodataapp.model.SetViewModel
import com.example.legodataapp.screens.AccountScreen
import com.example.legodataapp.screens.HelpScreen
import com.example.legodataapp.screens.HomeScreen
import com.example.legodataapp.screens.MyLEGOScreen
import com.example.legodataapp.screens.ProductScreen
import com.example.legodataapp.screens.RatingScreen
import com.example.legodataapp.screens.WishListScreen
import com.example.legodataapp.screens.qrResultScreen

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier,
    containerColor: Color,
    contentColor: Color
) {
    val navItems = listOf(NavItem.Home, NavItem.WishList, NavItem.MyLEGO, NavItem.Account)

    NavigationBar(containerColor = containerColor) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = false,
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                onClick = { navController.navigate(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = contentColor
                )
            )
        }
    }
}

@Composable
fun NavigationScreens(
    navController: NavHostController,
    viewModel: AuthViewModel,
    setViewModel: SetViewModel,
    updateContainerColor: (Boolean) -> Unit
) {
    NavHost(navController, startDestination = NavItem.Home.route) {
        composable(NavItem.WishList.route) { WishListScreen(navController = navController, hasRating = true) }
        composable(NavItem.MyLEGO.route) { MyLEGOScreen(navController = navController, hasRating = false) }
        composable(NavItem.Home.route) {
            HomeScreen(setViewModel = setViewModel, authViewModel = viewModel, navController = navController)
        }
        composable(NavItem.Account.route) { AccountScreen(navController = navController, viewModel = viewModel, updateContainerColor = updateContainerColor) }
        composable(NavItem.Help.route) { HelpScreen() }
        composable(NavItem.Rating.route) { RatingScreen() }
        composable(route = "${NavItem.QrCode.route}/{result}") { backStackEntry ->
            val result = backStackEntry.arguments?.getString("result") ?: ""
            qrResultScreen(result)
        }
        composable(NavItem.Product.route) { ProductScreen() }
        }
    }




@Composable
fun getCurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}