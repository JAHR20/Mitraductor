package com.itsa.mitraductor.gamesscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsa.mitraductor.R
import com.itsa.mitraductor.app.ToolbarWithBackButton
import com.itsa.mitraductor.app.debounce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StateRegionsScreen(navController: NavController, state: String) {
    val regions = games[state] ?: emptyMap()
    var isButtonEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = state,
                navController = navController
            )
        },
        bottomBar = {
            NavigationBar(
                content = {
                    Row(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.primary)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                    }
                }
            )
        },
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(regions.keys.toList()) { region ->
                    RegionCard(region = region) {
                        if (isButtonEnabled) {
                            isButtonEnabled = false
                            navController.navigate("juegos_categorias/$state/$region")
                            coroutineScope.launch {
                                delay(700) // 1 segundo, ajusta según sea necesario
                                isButtonEnabled = true
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun RegionCard(region: String, onClick: () -> Unit) {
    val madera: Painter = painterResource(id = R.drawable.madera)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = madera,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = region,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f,3f),
                            blurRadius = 5f
                        )
                    ),
                    textAlign = TextAlign.Center,
                    color = Color(0xFFD7CCC8).copy(alpha = 0.7f), // Beige claro con transparencia para la luz
                    modifier = Modifier
                        .offset(x = -2.dp, y = -2.dp)
                )
            }
        }
    }
}



