package com.example.legodataapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.Cream
import com.example.legodataapp.ui.theme.DarkYellow
import com.example.legodataapp.ui.theme.fontFamily
import com.example.legodataapp.R
import com.example.legodataapp.data.AllSet
import com.example.legodataapp.data.Set
import com.example.legodataapp.model.SetViewModel
import com.example.legodataapp.ui.theme.LightBrown

@Composable
fun HomeScreen(setViewModel: SetViewModel) {
    val sets by setViewModel.sets.observeAsState()

    LaunchedEffect(Unit) {
        setViewModel.fetchSets()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.city),
                contentDescription = "LEGO city",
                modifier = Modifier.size(420.dp)
            )
            Text(
                text = "Browse",
                color = LightBrown,
                fontSize = 30.sp,
                fontFamily = fontFamily
            )
        }
        items(sets?.setList ?: emptyList()) { set ->
            SetCard(set)
        }
    }
}

@Composable
fun SetCard(set: Set) {
        Card (
            modifier = Modifier.padding(20.dp),
            border = BorderStroke(2.dp, DarkYellow),
            colors = CardDefaults.cardColors(containerColor = Cream)
        ) {
            Column(modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = set.set_img_url,
                    contentDescription = set.name,
                    modifier = Modifier.size(200.dp),
                    alignment = Alignment.Center
                )
                Text(text = set.name,
                    color = Brown,
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(5.dp))
                Text("Pieces: ${set.num_parts}")
                Text("Set Number: ${set.set_num}")
//                    Text("Theme: ${set.theme_id}")
//                    Text("Year: ${set.year}")
            }
        }
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