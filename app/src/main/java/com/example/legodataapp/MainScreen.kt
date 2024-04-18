package com.example.legodataapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import kotlinx.coroutines.launch
import androidx.compose.material.rememberDrawerState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.legodataapp.model.AuthViewModel

import com.example.legodataapp.model.SetViewModel

import com.example.legodataapp.ui.theme.fontFamily
import android.media.MediaPlayer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import com.example.legodataapp.model.User
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions


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
    var textResult by remember { mutableStateOf("") }
    val context = LocalContext.current

    val qrCodeLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            textResult = "ERROR"
        } else {
            textResult = result.contents
            if (textResult.startsWith("http")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result.contents))
                context.startActivity(intent)
            } else {
                val intent = context.packageManager.getLaunchIntentForPackage(result.contents)
                if (intent != null) {
                    context.startActivity(intent)
                } else {
                    navController.navigate("${NavItem.QrCode.route}/${result.contents}")
                }
            }
        }
    }

    fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR Code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)

        qrCodeLauncher.launch(options)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        isGranted ->
        if (isGranted) {
            showCamera()
        } else {
            textResult = "Camera permission denied."
        }
    }

    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ){
            showCamera()
        }
        else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }



    /**
    var masterKey = MasterKey.Builder(this@MainActivity)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()
    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
    this@MainActivity,
    "EncryptedPrefs",
    masterKey,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    val idToken = sharedPreferences.getString("User_ID_Token", "")
     **/
    val loadSettings = LoadSettings()
    //val userToken = loadSettings.loadUserState((context)).toString()

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
        else -> ""
    }
    var mediaPlayer: MediaPlayer? = null
    val isSoundEffects = loadSettings.loadSoundEffectsState(context)
    var legoSound by rememberSaveable { mutableIntStateOf(R.raw.lego_swoosh) }
    var appJustStarted by rememberSaveable { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(
        initialValue = androidx.compose.material.DrawerValue.Closed
    )

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

    LaunchedEffect(Unit) {
        appJustStarted = false
        val userToken = loadSettings.loadUserState((context)).toString()
        if (userToken != "") {
            viewModel.login(User(userToken))
        }
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
                                if (drawerState.isOpen)
                                {
                                    drawerState.close()
                                } else {
                                    drawerState.open()
                                }
                            }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Menu",
                            tint = colorResource(id = textColor),
                            modifier = Modifier.size(36.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { checkCameraPermission() },
                        modifier = Modifier.size(48.dp)
                    )
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.qr_scan),
                            contentDescription = "QR scan",
                            tint = colorResource(id = textColor),
                            modifier = Modifier.size(36.dp)
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
            NavigationScreens(
                navController = navController,
                viewModel = viewModel,
                setViewModel = setViewModel,
                updateContainerColor = { isDarkMode ->
                    updateContainerColor(isDarkMode)
                    updateTextColor(isDarkMode)
                },
                context = context
            )


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

