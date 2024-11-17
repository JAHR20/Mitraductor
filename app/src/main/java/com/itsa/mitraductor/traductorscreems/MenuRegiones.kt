package com.itsa.mitraductor.traductorscreems

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.itsa.mitraductor.app.ToolbarWithBackButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuRegiones(
    estado: String,
    regiones: List<String>,
    navController: NavController
) {
    var isButtonEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Regiones de $estado",
                navController = navController,
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
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(regiones) { region ->
                ListItemRow(item = region) {
                    // Aquí es donde navegamos a la pantalla del traductor cuando se selecciona una región
                    if (isButtonEnabled) {
                        isButtonEnabled = false
                        navController.navigate("traductor/$estado/$region")
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

@Composable
fun ListItemRow(item: String, onButtonClick: () -> Unit) {
    val madera: Painter = painterResource(id = R.drawable.madera)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = MaterialTheme.shapes.small)
            .background(color = Color.White)
            //.padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable(onClick = onButtonClick) // Hace que el Box sea "clickeable"
            //.border(2.dp, Color.Green, shape = MaterialTheme.shapes.small)
    ) {
        Image(
            painter = madera,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 5.dp)
        ) {
            // Aquí agregamos la imagen asociada al estado
            Image(
                painter = getIconPainter(item),
                contentDescription = "Icono de $item",
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = item,
                style = MaterialTheme.typography.headlineSmall.copy(
                    //fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(3f,3f),
                        blurRadius = 5f
                    )
                ),
                //textAlign = TextAlign.Center,
                color = Color(0xFFD7CCC8).copy(alpha = 0.7f), // Beige claro con transparencia para la luz
                modifier = Modifier
                    .offset(x = -2.dp, y = -2.dp)
                    .padding(start = 4.dp)
            )
        }
    }
}