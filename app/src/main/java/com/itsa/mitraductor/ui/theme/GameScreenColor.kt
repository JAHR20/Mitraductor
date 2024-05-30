package com.itsa.mitraductor.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun Juego() {
    val muneco = remember { Muneco() }
    val bolitas = remember { mutableStateListOf<Bolita>() }
    val puntuacion = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            bolitas.add(Bolita())
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        muneco.dibujar(this)
        bolitas.forEach { it.dibujar(this) }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            muneco.actualizar()
            bolitas.forEach { it.actualizar() }
            bolitas.removeIf { it.posicion.y < 0 }
            if (bolitas.any { it.colisionaCon(muneco) }) {
                puntuacion.value++
                bolitas.removeIf { it.colisionaCon(muneco) }
            }
        }
    }

    Text(text = "Puntuación: ${puntuacion.value}", modifier = Modifier.padding(16.dp))
}