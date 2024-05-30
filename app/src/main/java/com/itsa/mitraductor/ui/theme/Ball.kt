package com.itsa.mitraductor.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.random.Random

class Bolita {
    var posicion = Offset(Random.nextFloat() * 800, 600f)
    val radio = 10f

    fun actualizar() {
        posicion += Offset(-2f, 0f)
    }

    fun dibujar(drawScope: DrawScope) {
        drawScope.drawCircle(color = Color.Blue, center = posicion, radius = radio)
    }

    fun colisionaCon(muneco: Muneco): Boolean {
        val distancia = (posicion - muneco.posicion).getDistance()
        return distancia < radio + 50f
    }
}