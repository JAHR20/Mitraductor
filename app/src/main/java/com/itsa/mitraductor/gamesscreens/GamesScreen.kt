package com.itsa.mitraductor.gamesscreens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsa.mitraductor.R
import com.itsa.mitraductor.app.BottomMenuItem
import com.itsa.mitraductor.app.MenuButton
import com.itsa.mitraductor.app.ToolbarWithBackButton
import com.itsa.mitraductor.app.debounce

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun minigamesScreen(navController: NavController) {
    var selectedButton by rememberSaveable { mutableStateOf(MenuButton.minijuegos) }
    var isButtonEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        //modifier = Modifier.padding(WindowInsets.safeDrawing.asPaddingValues()),
        topBar = {
            ToolbarWithBackButton(
                title = "Juegos",
                navController = navController,
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(80.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomMenuItem(
                        iconRes = R.drawable.translate_icon,
                        // TEXTO LARGO RESTAURADO
                        text = "Traductor - Ikakpa'ap aŋmatyi",
                        isSelected = selectedButton == MenuButton.traductor,
                        onClick = {
                            if (selectedButton != MenuButton.traductor) {
                                selectedButton = MenuButton.traductor
                                navController.popBackStack()
                                navController.navigate("estados")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                    BottomMenuItem(
                        iconRes = R.drawable.games_icon,
                        // TEXTO LARGO RESTAURADO
                        text = "Juegos - Michkuyyaj",
                        isSelected = selectedButton == MenuButton.minijuegos,
                        onClick = {
                            if (selectedButton != MenuButton.minijuegos) {
                                selectedButton = MenuButton.minijuegos
                                navController.popBackStack()
                                if (navController.currentDestination?.route != "minijuegos") {
                                    navController.navigate("minijuegos")
                                }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                    BottomMenuItem(
                        iconRes = R.drawable.acercade_icon,
                        // TEXTO LARGO RESTAURADO
                        text = "Acerca de - Tyi iniitypa'ap",
                        isSelected = selectedButton == MenuButton.acercade,
                        onClick = {
                            if (selectedButton != MenuButton.acercade) {
                                selectedButton = MenuButton.acercade
                                navController.popBackStack()
                                navController.navigate("acercade")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.logodejuego),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    items(games.keys.toList()) { state ->
                        StateCard(state = state) {
                            if (isButtonEnabled) {
                                isButtonEnabled = false
                                navController.navigate("juegos_regiones/$state")
                                coroutineScope.launch {
                                    delay(700) // 1 segundo, ajusta según sea necesario
                                    isButtonEnabled = true
                                }
                            }
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun StateCard(state: String, onClick: () -> Unit) {
    val madera: Painter = painterResource(id = R.drawable.madera)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp),

        //border = BorderStroke(4.dp, color=verde)
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF092885))
                    //.background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                    .padding(16.dp)
            ) {
                val imageResource = getIconResourceIdgame(state)
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = "Imagen del estado",
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state,
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