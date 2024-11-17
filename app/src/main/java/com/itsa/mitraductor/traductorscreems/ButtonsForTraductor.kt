package com.itsa.mitraductor.traductorscreems

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itsa.mitraductor.R
import com.itsa.mitraductor.ui.theme.colorVeracruz

@Composable
fun BotonTraducir(
    textoOriginal: String,
    traducciones: Map<String, String>,
    textoBoton: String,
    onTraduccionReady: (String) -> Unit,
    onTextArea1ValueChanged: (String) -> Unit,
    isTextArea1Visible: Boolean,
    textoTraducido: String
) {
    val textoTrimmed = textoOriginal.trim()
    println("Texto Original después de trim: \"$textoTrimmed\"")

    Button(
        onClick = {
            if (textoBoton == "Traducir") {
                val textoTraducido = traducir(textoOriginal, traducciones)
                onTraduccionReady(textoTraducido)
            } else if (textoBoton == "Traducción Inversa") {
                val textoSegundoTextArea = if (!isTextArea1Visible) textoTraducido else textoOriginal
                val claveTraduccion = traducirDesdeValor(textoSegundoTextArea, traducciones)
                // Actualiza el texto en el primer TextArea con la clave de la traducción
                onTextArea1ValueChanged(claveTraduccion)
            }
        },
        modifier = Modifier
            .width(150.dp)
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
        Text(
            textoBoton,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun BotonCambiar(onClick: () -> Unit) {
    val WIDTH1 = 150.dp


    var width by remember { mutableStateOf(WIDTH1) }

    Button(
        onClick = {
            width = when (width) {

                else -> WIDTH1
            }
            onClick()
        },
        modifier = Modifier.width(width),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)

    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_cambiar),
            contentDescription = "Cambiar",
            modifier = Modifier.size(ButtonDefaults.IconSize)

        )
        Text("Cambiar")
    }
}
