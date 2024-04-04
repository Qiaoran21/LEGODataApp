package com.example.legodataapp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.DarkYellow
import kotlinx.coroutines.launch
import androidx.compose.material.rememberDrawerState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.legodataapp.model.AuthViewModel
import com.example.legodataapp.model.SetViewModel
import com.example.legodataapp.ui.theme.fontFamily


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: AuthViewModel,
    setViewModel: SetViewModel
) {
    val currentRoute = getCurrentRoute(navController)

    val pageTitle = when (currentRoute) {
        NavItem.Home.route -> NavItem.Home.title
        NavItem.WishList.route -> NavItem.WishList.title
        NavItem.MyLEGO.route -> NavItem.MyLEGO.title
        NavItem.Account.route -> NavItem.Account.title
        NavItem.Help.route -> NavItem.Help.title
        NavItem.Product.route -> NavItem.Product.title
        NavItem.Rating.route -> NavItem.Rating.title
        else -> "Error!"
    }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(
        initialValue = androidx.compose.material.DrawerValue.Closed
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkYellow,
                    titleContentColor = Brown,
                ),
                title = {
                    Text(
                        pageTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (drawerState.isOpen) {
                                    drawerState.close() }
                                else {
                                    drawerState.open() }
                            } }) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
//                actions = {
//                    IconButton(onClick = { /* TODO */ }) {
//                        Icon(
//                            imageVector = Icons.Rounded.Search,
//                            contentDescription = "Search"
//                        )
//                    }
//                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = DarkYellow) {
                BottomNavBar(
                    navController = navController,
                    modifier = modifier
                )
            }
        }
    ) {
        ModalDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(
                    modifier = Modifier.background(DarkYellow)
                ) {
                    DrawerHeader(viewModel = viewModel)
                    Spacer(modifier = Modifier.padding(10.dp))
                    DrawerBody(
                        items = listOf(
                            NavItem.Home,
                            NavItem.WishList,
                            NavItem.MyLEGO,
                            NavItem.Account,
                            NavItem.Help
                        ),
                        modifier = Modifier.fillMaxSize()
                    ) { item ->
                        scope.launch {
                            drawerState.close()
                            navController.navigate(item.route)
                        }
                    }
                }
            }
        ) {
            NavigationScreens(navController = navController, viewModel, setViewModel)
        }
    }
}

