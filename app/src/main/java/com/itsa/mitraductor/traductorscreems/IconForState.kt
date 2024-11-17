package com.itsa.mitraductor.traductorscreems

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SignLanguage
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.itsa.mitraductor.R

// Función para obtener el ID de la imagen asociada al estado
@Composable
@SuppressLint("SupportAnnotationUsage")
@DrawableRes
fun getIconPainter(estado: String): Painter {
    return when (estado) {
        "Puebla(Náhuatl)" -> painterResource(id = R.drawable.puebla_icon)
        "Oaxaca(Mixe)" -> painterResource(id = R.drawable.oaxaca_icon)
        "Veracruz(Popoluca)" -> painterResource(id = R.drawable.veracruz_icon)
        "Colima(Náhuatl)" -> painterResource(id = R.drawable.colima_icon)
        "Traductor de señas" -> rememberVectorPainter(image = Icons.Filled.SignLanguage) // Usa el icono de vector
        //"Traductor de señas2" -> rememberVectorPainter(image = Icons.Filled.QuestionMark)
        else -> painterResource(id = R.drawable.regiones) // Icono predeterminado
    }
}