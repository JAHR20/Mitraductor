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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.itsa.mitraductor.app.ToolbarWithBackButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuRegiones(
    estado: String,
    regiones: List<String>,
    navController: NavController
) {
    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Regiones de $estado",
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
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 70.dp),
            contentPadding = PaddingValues(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(regiones) { region ->
                ListItemRow(item = region) {
                    // Aquí es donde navegamos a la pantalla del traductor cuando se selecciona una región
                    navController.navigate("traductor/$region")
                }
            }
        }
    }
}

@Composable
fun ListItemRow(item: String, onButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = MaterialTheme.shapes.small)
            .background(color = Color.White)
            //.padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable(onClick = onButtonClick) // Hace que el Box sea "clickeable"
            .border(2.dp, Color.Green, shape = MaterialTheme.shapes.small)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 5.dp)
        ) {
            // Aquí agregamos la imagen asociada al estado
            Image(
                painter = painterResource(id = getIconResourceId(item)),
                contentDescription = "Icono de $item",
                modifier = Modifier.size(48.dp)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                text = item,
                style = MaterialTheme.typography.headlineSmall,
                //fontSize = 16.sp
            )
        }
    }
}