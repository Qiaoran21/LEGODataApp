package com.example.legodataapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
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
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.legodataapp.model.AuthViewModel

import com.example.legodataapp.ui.theme.DarkerYellow

import com.example.legodataapp.model.SetViewModel

import com.example.legodataapp.ui.theme.fontFamily
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import com.example.legodataapp.ui.theme.Pink40


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: AuthViewModel,
    setViewModel: SetViewModel,
    context: Context,
    isDarkMode: Boolean

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
        "${NavItem.QrCode.route}/{result}" -> NavItem.QrCode.title
        else -> "Error!"
    }

    val context = LocalContext.current
    var mediaPlayer: MediaPlayer? = null
    val loadSettings = LoadSettings()
    val isSoundEffects = loadSettings.loadSoundEffectsState(context)
    var legoSound by rememberSaveable { mutableIntStateOf(R.raw.lego_swoosh) }
    var appJustStarted by rememberSaveable { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(
        initialValue = androidx.compose.material.DrawerValue.Closed
    )

    LaunchedEffect(Unit){
        appJustStarted = false
    }

    var containerColor by rememberSaveable { mutableStateOf(
        if(isDarkMode){R.color.DarkerYellow}else{R.color.DarkYellow})
    }
    var textColor by rememberSaveable { mutableStateOf(
        if(isDarkMode){R.color.LightText}else{R.color.DarkText})
    }

    fun updateContainerColor(isDarkMode: Boolean) {
        containerColor = if (isDarkMode) R.color.DarkerYellow else R.color.DarkYellow
    }
    fun updateTextColor(isDarkMode: Boolean) {
        textColor = if (isDarkMode) R.color.LightText else R.color.DarkText
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = containerColor),
                    titleContentColor = colorResource(id = textColor),
                ),
                title = {
                    Text(
                        pageTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = textColor)
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
                            contentDescription = "Menu",
                            tint = colorResource(id = textColor)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search",
                            tint = colorResource(id = textColor)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = colorResource(id = containerColor)) {
                BottomNavBar(
                    navController = navController,
                    modifier = modifier,
                    containerColor = colorResource(id = containerColor),
                    contentColor = colorResource(id = textColor)
                )
            }
        }
    ) {
        ModalDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(
                    modifier = Modifier.background(colorResource(id = containerColor))
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
                        modifier = Modifier.fillMaxSize(),
                        contentColor = colorResource(id = textColor)
                    ) { item ->
                        scope.launch {
                            drawerState.close()
                            navController.navigate(item.route)
                        }
                    }
                }
            }
        ) {
            NavigationScreens(navController = navController, viewModel, setViewModel, updateContainerColor = { isDarkMode ->
                updateContainerColor(isDarkMode)
                updateTextColor(isDarkMode)
            })

            LaunchedEffect(navController.currentBackStackEntry?.destination?.route) {
                if (!appJustStarted) {
                    if(mediaPlayer == null){
                        mediaPlayer = MediaPlayer.create(context, legoSound)
                    }
                    if (isSoundEffects) {
                        mediaPlayer?.start()
                    }
                    legoSound = R.raw.lego_sound_effect
                    mediaPlayer?.setOnCompletionListener {
                        it.release()
                    }
                }
            }
        }
    }
}

