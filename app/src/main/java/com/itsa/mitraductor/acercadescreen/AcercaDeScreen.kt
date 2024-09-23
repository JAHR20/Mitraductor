package com.itsa.mitraductor.acercadescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
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
fun AcercaDeScreen(navController: NavController) {
    var selectedButton by remember { mutableStateOf(MenuButton.acercade) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            topBar = {
                ToolbarWithBackButton(
                    title = "Acerca de",
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
                                    selectedButton = MenuButton.traductor
                                    navController.popBackStack()
                                    navController.navigate("estados")
                                },
                                modifier = Modifier.weight(1f)
                            )
                            BottomMenuItem(
                                iconRes = R.drawable.games_icon,
                                text = "Juegos - Michkuyyaj",
                                isSelected = selectedButton == MenuButton.minijuegos,
                                onClick = {
                                    selectedButton = MenuButton.minijuegos
                                    navController.popBackStack()
                                    navController.navigate("minijuegos")
                                },
                                modifier = Modifier.weight(1f)
                            )
                            BottomMenuItem(
                                iconRes = R.drawable.acercade_icon,
                                text = "Acerca de - Tyi iniitypa'ap",
                                isSelected = selectedButton == MenuButton.acercade,
                                onClick = { selectedButton = MenuButton.acercade },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                )
            },
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.logodejuego),
                        contentDescription = null,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .align(Alignment.Center)
                            .graphicsLayer(alpha = 0.3f),
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item {
                            Text(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                text = "MICHUY IANNA",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    shadow = Shadow(
                                        offset = Offset(10f, 10f),
                                        blurRadius = 10f
                                    )
                                ),
                                color = Color.Black
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            Text(
                                text = "Versión: \n 1.0",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            Text(
                                text = "MICHUY IANNA es una herramienta de aprendizaje lingüístico, y " +
                                        "un puente cultural que permita a los usuarios sumergirse en las " +
                                        "ricas tradiciones de las comunidades indígenas de Veracruz, Puebla " +
                                        "y Oaxaca, contribuyendo así a la preservación y revitalización de " +
                                        "lenguas indígenas en peligro de extinción.",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            Text(
                                text = "Desarrollado por: \n Equipo MICHUY IANNA",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            Text(
                                text = "Contacto: ",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                        item {
                            EmailLink(email = "jahr30062000@gmail.com")
                        }
                        item {
                            Text(
                                text = "Redes sociales: ",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                        item {
                            SocialMediaButtons()
                        }
                        item {
                            Spacer(modifier = Modifier.height(35.dp))
                        }
                        // Agrega más elementos según sea necesario
                    }
                }
            }
        )
    }
}