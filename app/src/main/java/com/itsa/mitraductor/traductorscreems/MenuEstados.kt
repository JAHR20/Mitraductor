package com.itsa.mitraductor.traductorscreems

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.itsa.mitraductor.R
import com.itsa.mitraductor.app.BottomMenuItem
import com.itsa.mitraductor.app.MenuButton
import com.itsa.mitraductor.app.ToolbarWithBackButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuEstados(
    estados: Map<String, List<String>>,
    navController: NavController
) {
    var selectedButton by remember { mutableStateOf(MenuButton.traductor) }
    var isButtonEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    fun navigateTo(destination: String) {
        navController.popBackStack()
        navController.navigate(destination)
    }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Estados",
                navController = navController,
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(80.dp) // 1. ESTO ARREGLA EL TAMAÑO
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomMenuItem(
                        iconRes = R.drawable.translate_icon,
                        // 2. USAMOS EL TEXTO LARGO PARA QUE SE MUEVA (MARQUEE)
                        text = "Traductor - Ikakpa'ap aŋmatyi",
                        isSelected = selectedButton == MenuButton.traductor,
                        onClick = { selectedButton = MenuButton.traductor },
                        modifier = Modifier.weight(1f)
                    )
                    BottomMenuItem(
                        iconRes = R.drawable.games_icon,
                        text = "Juegos - Michkuyyaj",
                        isSelected = selectedButton == MenuButton.minijuegos,
                        onClick = {
                            selectedButton = MenuButton.minijuegos
                            navigateTo("minijuegos")
                        },
                        modifier = Modifier.weight(1f)
                    )
                    BottomMenuItem(
                        iconRes = R.drawable.acercade_icon,
                        text = "Acerca de - Tyi iniitypa'ap",
                        isSelected = selectedButton == MenuButton.acercade,
                        onClick = {
                            selectedButton = MenuButton.acercade
                            navigateTo("acercade")
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        content = {paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.logodejuego),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(all = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(estados.keys.toList()) { estado ->
                        ListItemRow(item = estado) {
                            if (isButtonEnabled) {
                                isButtonEnabled = false
                                navController.navigate("traductor_regiones/${Uri.encode(estado)}")
                                coroutineScope.launch {
                                    delay(700) // 1 segundo, ajusta según sea necesario
                                    isButtonEnabled = true
                                }
                            }
                        }
                    }
                    item {
                        ListItemRow(item = "Traductor de señas") {
                            if (isButtonEnabled) {
                                isButtonEnabled = false
                                navController.navigate("TraductorDeSenas")
                                coroutineScope.launch {
                                    delay(700) // 1 segundo, ajusta según sea necesario
                                    isButtonEnabled = true
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}