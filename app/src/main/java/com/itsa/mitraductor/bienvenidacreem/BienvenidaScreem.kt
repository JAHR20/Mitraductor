package com.itsa.mitraductor.bienvenidacreem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.wear.compose.material.ButtonColors
import com.itsa.mitraductor.R
import com.itsa.mitraductor.app.debounce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BienvenidaScreen(navController: NavController) {
    val nombrejuego = "MICHUY IANNA"
    val descripcionjuego = "Juego de apredizaje de lengua materna"
    var isButtonEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center,


        ) {
        // Cambia esto por el nombre de tu video en los recursos
        VideoPlayerWithLoop(
            videoResId = R.raw.videobienvenida,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
            contentScale = ContentScale.FillWidth,
            lifecycle = LocalLifecycleOwner.current.lifecycle
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = nombrejuego,
                fontSize = 45.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(R.font.bonanovascbold)),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF092885) // Cambiar el color del texto si es necesario
            )
            //Spacer(modifier = Modifier.height(2.dp)) // Espacio entre el nombre del juego y la descripción
            Text(
                text = descripcionjuego,
                fontSize = 32.sp, // Cambiar el tamaño de la fuente según sea necesario
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(R.font.lxgwwenkaitcbold)),
                textAlign = TextAlign.Center,
                lineHeight = 1.em,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF092885) // Cambiar el color del texto si es necesario
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(Color(0xFF092885)),
                onClick = {
                    if (isButtonEnabled) {
                        isButtonEnabled = false
                        navController.navigate("minijuegos")
                        // Rehabilitar el botón después de un retraso
                        coroutineScope.launch {
                            delay(700) // 1 segundo, ajusta según sea necesario
                            isButtonEnabled = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Continuar(Nɨkɨ)", fontSize = 20.sp, color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_continuar),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}