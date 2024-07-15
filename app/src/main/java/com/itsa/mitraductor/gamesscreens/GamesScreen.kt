package com.itsa.mitraductor.gamesscreens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun minigamesScreen(navController: NavController) {
    var selectedButton by rememberSaveable { mutableStateOf(MenuButton.minijuegos) }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Juegos",
                navController = navController
            )
        },
        bottomBar = {
            NavigationBar(
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primary),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomMenuItem(
                            iconRes = R.drawable.translate_icon,
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
                            text = "Juegos - Michkuyyaj",
                            isSelected = selectedButton == MenuButton.minijuegos,
                            onClick = { selectedButton = MenuButton.minijuegos },
                            modifier = Modifier.weight(1f)
                        )
                        BottomMenuItem(
                            iconRes = R.drawable.acercade_icon,
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
            )
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
                            navController.navigate("juegos_regiones/$state")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun StateCard(state: String, onClick: () -> Unit) {
    //val madera: Painter = painterResource(id = R.drawable.madera)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f) // Para hacer la tarjeta cuadrada
            .clip(CircleShape) // Para hacer la tarjeta redonda
            .clickable { onClick() },
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(8.dp),
        //border = BorderStroke(4.dp, color=verde)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    //.background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp) // Tamaño del contenedor del icono
                        .clip(CircleShape) // Forma circular del contenedor del icono
                        .border(2.dp, Color.Green, CircleShape), // Borde verde alrededor del contenedor del icono
                    contentAlignment = Alignment.Center
                ) {
                    val imageResource = getIconResourceIdgame(state)
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Imagen del estado",
                        modifier = Modifier.size(64.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
