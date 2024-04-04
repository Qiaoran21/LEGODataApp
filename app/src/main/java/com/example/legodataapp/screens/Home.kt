package com.example.legodataapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.legodataapp.NavItem
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.fontFamily
import com.example.legodataapp.data.LegoSet
import com.example.legodataapp.model.SetViewModel
import com.example.legodataapp.ui.theme.LightBrown

@Composable
fun HomeScreen(setViewModel: SetViewModel, navController: NavController) {
    val sets by setViewModel.sets.observeAsState()
    var searchInput by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        setViewModel.fetchSets()
    }

    val filteredSets = sets?.legoSetList?.filter {
        it.name.contains(searchInput, ignoreCase = true) ||
        it.year.toString() == searchInput
    }

    Column(modifier = Modifier.background(Cream)) {
        AppSearchBar(
            searchInput = searchInput,
            onSearchInputChanged = { searchInput = it }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Cream)
        ) {
            item {
                Text(
                    text = "Browse",
                    color = LightBrown,
                    fontSize = 30.sp,
                    fontFamily = fontFamily
                )
            }
            items(filteredSets ?: emptyList()) { set ->
                SetCard(set, navController)
            }
        }
    }
}

@Composable
fun SetCard(legoSet: LegoSet, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        border = BorderStroke(2.dp, DarkYellow),
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Column(
            modifier = Modifier.padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = legoSet.set_img_url,
                contentDescription = legoSet.name,
                modifier = Modifier.size(300.dp),
                alignment = Alignment.Center
            )
            Text(
                text = legoSet.name,
                color = Brown,
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(NavItem.Product.route)
                }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text("Pieces: ${legoSet.num_parts}")
            Text("Set Number: ${legoSet.set_num}")
            Text("Release Year: ${legoSet.year}")
            Text("Theme ID: ${legoSet.theme_id}")
        }
    }
}

@Composable
fun AppSearchBar(
    searchInput: String,
    onSearchInputChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(70.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Cream,
            unfocusedContainerColor = Cream,
            cursorColor = DarkYellow,
            focusedBorderColor = DarkYellow
        ),
        value = searchInput,
        onValueChange = { onSearchInputChanged(it) },
        placeholder = { Text(text = "Search...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            if (searchInput.isNotEmpty()) {
                IconButton(onClick = { onSearchInputChanged("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon"
                    )
                }
            }
        },
        singleLine = true,
        textStyle = TextStyle(color = Brown)
    )
}



//@Composable
//fun legoTheme() {
//    Row (modifier = Modifier.background(Cream)) {
//        Card (
//            modifier = Modifier.padding(5.dp),
//            border = BorderStroke(2.dp, DarkYellow),
//            colors = CardDefaults.cardColors(containerColor = Cream)
//        ) {
//            Column(
//                modifier = Modifier.padding(10.dp),
//            ) {
//                Image(painter = painterResource(id = R.drawable.logo),
//                    contentDescription = "LEGO Data logo",
//                    modifier = Modifier.size(100.dp))
//                Column {
//                    Text(
//                        text = "LEGO Theme",
//                        color = Brown,
//                        fontSize = 15.sp,
//                        fontFamily = fontFamily,
//                        fontWeight = FontWeight.Bold)
//                }
//            }
//        }
//    }
//}