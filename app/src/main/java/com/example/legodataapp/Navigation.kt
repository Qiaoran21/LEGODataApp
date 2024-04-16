package com.example.legodataapp

import android.content.Context
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
import com.example.legodataapp.data.LegoSet
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
import com.example.legodataapp.screens.showToast
import java.net.URLDecoder

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
    updateContainerColor: (Boolean) -> Unit,
    context: Context
) {
    NavHost(navController, startDestination = NavItem.Home.route) {
        composable(NavItem.WishList.route) {
            WishListScreen(
                navController = navController,
                hasRating = true,
                wishlistItems = setViewModel.wishlistSets,
                setViewModel = setViewModel,
                authViewModel = viewModel
            )
        }

        composable(NavItem.MyLEGO.route) {
            MyLEGOScreen(
                navController = navController,
                hasRating = false,
                myLegoListItems = setViewModel.myLegoListItems,
                setViewModel = setViewModel,
                authViewModel = viewModel
            )
        }

        composable(NavItem.Home.route) {
            HomeScreen(
                setViewModel = setViewModel,
                authViewModel = viewModel,
                navController = navController
            )
        }
        composable(NavItem.Account.route) {
            AccountScreen(
                navController = navController,
                viewModel = viewModel,
                setViewModel = setViewModel,
                updateContainerColor = updateContainerColor
            )
        }
        composable(NavItem.Help.route) { HelpScreen() }
        composable(NavItem.Rating.route) { RatingScreen() }
        composable(route = "${NavItem.QrCode.route}/{result}") { backStackEntry ->
            val result = backStackEntry.arguments?.getString("result") ?: ""
            qrResultScreen(result)
        }
        //composable(NavItem.Product.route) { ProductScreen() }
        composable(
            route = NavItem.Product.route + "/{last_modified_dt}/{name}/{num_parts}/{set_img_url}/{set_num}/{set_url}/{theme_id}/{year}/{isAuth}"
        ) { backStackEntry ->
            val lastMod = backStackEntry.arguments?.getString("last_modified_dt") ?: ""
            val setName = backStackEntry.arguments?.getString("name") ?: ""
            val numParts = backStackEntry.arguments?.getString("num_parts") ?: "0"
            val setImgUrl = URLDecoder.decode(backStackEntry.arguments?.getString("set_img_url") ?: "", "UTF-8")
            val setNum = backStackEntry.arguments?.getString("set_num") ?: "0"
            val setUrl = URLDecoder.decode(backStackEntry.arguments?.getString("set_url") ?: "", "UTF-8")
            val themeId = backStackEntry.arguments?.getString("theme_id") ?: "0"
            val year = backStackEntry.arguments?.getString("year") ?: "0"

            val legoSet = LegoSet(lastMod, setName, numParts.toInt(), setImgUrl, setNum, setUrl, themeId.toInt(), year.toInt())
            val isAuthenticated = backStackEntry.arguments?.getString("isAuth") ?: "false"

            ProductScreen(
                legoSet = legoSet,
                authViewModel = viewModel,
                setViewModel = setViewModel,
                isAuthenticated = isAuthenticated.toBoolean(),
                onAddWishlist = {
                    setViewModel.addToWishlist(legoSet, "1")
                },
                onAddMyLegoList = {
                    setViewModel.addToMyLegolist(legoSet)
                },
                context = context,
                navController = navController
            )
        }
    }
}

@Composable
fun getCurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}