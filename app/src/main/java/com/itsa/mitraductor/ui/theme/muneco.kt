package com.itsa.mitraductor.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

class Muneco {
    var posicion = Offset(50f, 300f) // Ajusta la posición inicial según sea necesario
    var velocidad = Offset(0f, 0f) // Ajusta la velocidad inicial según sea necesario

    fun actualizar() {
        // El muñeco se mueve automáticamente en el eje x
        posicion += velocidad

        // Si el muñeco está en el suelo, puede saltar
        if (posicion.y >= 300f) {
            velocidad = Offset(0f, -10f)
        }

        // Aplicar gravedad
        velocidad += Offset(0f, 0.5f)
    }


    fun dibujar(drawScope: DrawScope) {
        drawScope.drawCircle(color = Color.Red, center = posicion, radius = 50f)
    }

    fun colisionaCon(bolita: Bolita): Boolean {
        val distancia = (posicion - bolita.posicion).getDistance()
        return distancia < 50f + bolita.radio
    }
}
