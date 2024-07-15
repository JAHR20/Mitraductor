package com.itsa.mitraductor.gamesscreens

import androidx.annotation.DrawableRes
import com.itsa.mitraductor.R

@DrawableRes
fun getIconResourceIdgame(estado: String): Int {
    return when (estado) {
        "Puebla" -> R.drawable.puebla_icon
        "Oaxaca" -> R.drawable.oaxaca_icon
        "Veracruz" -> R.drawable.veracruz_icon
        // Agrega más casos según sea necesario para otros estados
        else -> R.drawable.regiones// Imagen predeterminada en caso de que no haya ninguna definida
    }
}