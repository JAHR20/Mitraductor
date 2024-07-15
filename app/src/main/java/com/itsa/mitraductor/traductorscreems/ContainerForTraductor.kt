package com.itsa.mitraductor.traductorscreems

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ViewContainerContent(
    isTextArea1Visible: Boolean,
    textoOriginal: String,
    textoTraducido: String,
    onTextArea1ValueChanged: (String) -> Unit,
    onTextArea2ValueChanged: (String) -> Unit,
    onBotonCambiarClick: () -> Unit,
    onBotonTraducirClick: (String) -> Unit,
    onTraduccionReady: (String) -> Unit, // Añadir este argumento
    traducciones: Map<String, String>, // Agregar traducciones como parámetro
    regionSeleccionada: String, // Agregar regionSeleccionada como parámetro
    context: Context // Agregar context como parámetro
) {
    var textoBotonTraducir by remember { mutableStateOf("Traducir") }
    LazyColumn(
        modifier = Modifier.padding(top = 70.dp,),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            if (isTextArea1Visible) {
                Row {
                    TextArea(textoOriginal=textoOriginal, onValueChanged = { newText -> onTextArea1ValueChanged(newText) })
                }
            } else {
                Row {
                    TextAreaExample2(
                        textoTraducido = textoTraducido,
                        onValueChanged = { newText ->
                            onBotonTraducirClick(newText)
                        }
                    )
                }
            }

            Row {
                BotonCambiar {
                    // Cambia la visibilidad y ejecuta la animación
                    onBotonCambiarClick()
                    textoBotonTraducir = if (textoBotonTraducir == "Traducir") "Traducción Inversa" else "Traducir"
                }
            }

            Row {
                if (isTextArea1Visible) {
                    TextAreaExample2(
                        textoTraducido = textoTraducido,
                        onValueChanged = { newText ->
                            onBotonTraducirClick(newText)
                        }
                    )
                } else {
                    TextArea(textoOriginal=textoOriginal,onValueChanged = { newText -> onTextArea1ValueChanged(newText) })
                }
            }
            println("Valor de textoTraducido: $textoTraducido")
            Row {
                println("Valor de textoOriginal: \"$textoOriginal\"")
                BotonTraducir(
                    textoOriginal = textoOriginal,
                    traducciones = traducciones,
                    textoBoton = textoBotonTraducir,
                    onTraduccionReady = { traduccion ->
                        // Utiliza la traducción recibida
                        onBotonTraducirClick(traduccion)
                        onTraduccionReady(traduccion)
                    },
                    onTextArea1ValueChanged = { claveTraduccion  ->
                        // Actualizar texto en el primer TextArea
                        onTextArea1ValueChanged(claveTraduccion )
                    },
                    isTextArea1Visible = isTextArea1Visible,
                    textoTraducido = textoTraducido
                )
            }
            if (textoBotonTraducir=="Traducción Inversa") {
                Row {
                    SpecialCharacterButton(character = "ɨ", onCharacterClick = { character ->
                        // Agrega el caracter al texto en TextAreaExample2
                        onTextArea2ValueChanged(textoTraducido + character + "")
                    })
                    SpecialCharacterButton(character = "’", onCharacterClick = { character ->
                        // Agrega el caracter al texto en TextAreaExample2
                        onTextArea2ValueChanged(textoTraducido + character + "")
                    })
                    SpecialCharacterButton(character = "ŋ", onCharacterClick = { character ->
                        // Agrega el caracter al texto en TextAreaExample2
                        onTextArea2ValueChanged(textoTraducido + character + "")
                    })
                }
            }
            Row {
                Button(
                    onClick = {
                        // Llama a la función reproducirAudio pasando la palabra original y la región seleccionada
                        reproducirAudio(textoOriginal, regionSeleccionada, context)
                    }
                ) {
                    Text("Reproducir Audio")
                }
            }

        }
    }
}

@Composable
fun SpecialCharacterButton(character: String, onCharacterClick: (String) -> Unit) {
    Button(onClick = { onCharacterClick(character) }) {
        Text(character)
    }
}