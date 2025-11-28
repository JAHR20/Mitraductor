package com.itsa.mitraductor.gamesscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.itsa.mitraductor.app.ToolbarWithBackButton
import com.itsa.mitraductor.ui.theme.colorColima
import com.itsa.mitraductor.ui.theme.colorOaxaca
import com.itsa.mitraductor.ui.theme.colorPuebla
import com.itsa.mitraductor.ui.theme.colorVeracruz
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CategorySelectionScreen(navController: NavController, state: String, region: String) {
    val context = LocalContext.current
    val categories = listOf("Básico", "Medio", "Avanzado")
    var isButtonEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "$region - Categorías",
                navController = navController,
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentPadding = PaddingValues(0.dp), // Quitamos margenes internos
                modifier = Modifier.height(80.dp) // LE DAMOS ALTURA FIJA para que no tape la pantalla
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                }
            }
        },
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(categories) { category ->
                    CategoryCard(category = category, state = state){
                        if (isButtonEnabled) {
                            isButtonEnabled = false
                            navController.navigate("Juegos/$state/$region/$category")
                            // Rehabilitar el botón después de un retraso
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
fun CategoryCard(category : String, state: String, onClick:()-> Unit) {
    val colorestado= when (state) {
        "Veracruz" -> colorVeracruz
        "Puebla" -> colorPuebla
        "Oaxaca" -> colorOaxaca
        "Colima" -> colorColima
        else -> Color.Gray // Color predeterminado si no se encuentra
    }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick()},
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(color = colorestado)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = category,
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
