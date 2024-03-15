package com.example.legodataapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legodataapp.ui.theme.Brown
import com.example.legodataapp.ui.theme.fontFamily

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .padding(vertical = 60.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(text = "", color = Brown)
    }
}

@Composable
fun DrawerBody(
    items: List<NavItem>,
    modifier: Modifier = Modifier,
    onItemClick: (NavItem) -> Unit
) {
    LazyColumn(modifier) {
        items(items) {item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(25.dp)
            ) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.contentDescription,
                    tint = Brown
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(text = item.title, color = Brown, fontSize = 20.sp)
            }
        }
    }
}