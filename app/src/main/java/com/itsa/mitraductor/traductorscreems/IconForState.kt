package com.itsa.mitraductor.traductorscreems

import androidx.annotation.DrawableRes
import com.itsa.mitraductor.R

// Función para obtener el ID de la imagen asociada al estado
@DrawableRes
fun getIconResourceId(estado: String): Int {
    return when (estado) {
        "Puebla(Nahualt)" -> R.drawable.puebla_icon
        "Oaxaca(Mixe)" -> R.drawable.oaxaca_icon
        "Veracruz(Popoluca)" -> R.drawable.veracruz_icon
        // Agrega más casos según sea necesario para otros estados
        else -> R.drawable.regiones// Imagen predeterminada en caso de que no haya ninguna definida
    }
}