package com.itsa.mitraductor.traductorscreems

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.itsa.mitraductor.app.CustomKeyboard
import com.itsa.mitraductor.app.KeyboardVisibilityObserver

@Composable
fun ViewContainerContent(
    isTextArea1Visible: Boolean,
    textoOriginal: String,
    textoTraducido: String,
    onTextArea1ValueChanged: (String) -> Unit,
    onTextArea2ValueChanged: (String) -> Unit,
    onBotonCambiarClick: () -> Unit,
    onBotonTraducirClick: (String) -> Unit,
    onTraduccionReady: (String) -> Unit,
    traducciones: Map<String, String>,
    estado: String,
    regionSeleccionada: String,
    context: Context
) {
    var textoBotonTraducir by remember { mutableStateOf("Traducir") }
    var isEditing by remember { mutableStateOf(false) }
    var currentText by remember { mutableStateOf(textoOriginal) }
    var activeTextField by remember { mutableStateOf(1) }
    val focusManager = LocalFocusManager.current // Obtén el FocusManager

    var keyboardHeight by remember { mutableStateOf(0) } // Estado para la altura del teclado

    // Observer para la visibilidad del teclado y la altura
    KeyboardVisibilityObserver { isVisible, height ->
        keyboardHeight = if (isVisible) height else 0
    }

    val onSpaceClick = {
        currentText += " "
        if (activeTextField == 1) {
            onTextArea1ValueChanged(currentText)
        } else {
            onTextArea2ValueChanged(currentText)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = keyboardHeight.dp) // Ajustar el padding inferior según la altura del teclado
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f).padding(top = 5.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(65.dp))
                if (isTextArea1Visible) {
                    Row {
                        TextArea(
                            textoOriginal = textoOriginal,
                            onValueChanged = { newText ->
                                onTextArea1ValueChanged(newText)
                                currentText = newText
                            },
                            onEditingChanged = { isFocused ->
                                isEditing = isFocused
                                if (isFocused) {
                                    activeTextField = 1
                                    currentText = textoOriginal
                                }
                            }
                        )
                    }
                } else {
                    Row {
                        TextAreaExample2(
                            textoTraducido = textoTraducido,
                            onValueChanged = { newText ->
                                onBotonTraducirClick(newText)
                            },
                            onEditingChanged = { isFocused ->
                                isEditing = isFocused
                                if (isFocused) {
                                    activeTextField = 2
                                    currentText = textoTraducido
                                }
                            }
                        )
                    }
                }

                Row {
                    BotonCambiar {
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
                            },
                            onEditingChanged = { isFocused ->
                                isEditing = isFocused
                                if (isFocused) {
                                    activeTextField = 2
                                    currentText = textoTraducido
                                }
                            }
                        )
                    } else {
                        TextArea(
                            textoOriginal = textoOriginal,
                            onValueChanged = { newText -> onTextArea1ValueChanged(newText) },
                            onEditingChanged = { isFocused ->
                                isEditing = isFocused
                                if (isFocused) {
                                    activeTextField = 1
                                    currentText = textoOriginal
                                }
                            }
                        )
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
                            onBotonTraducirClick(traduccion)
                            onTraduccionReady(traduccion)
                        },
                        onTextArea1ValueChanged = { claveTraduccion ->
                            onTextArea1ValueChanged(claveTraduccion)
                        },
                        isTextArea1Visible = isTextArea1Visible,
                        textoTraducido = textoTraducido
                    )
                }

                Row {
                    Button(
                        onClick = {
                            reproducirAudio(estado, textoOriginal, regionSeleccionada, context)
                        }
                    ) {
                        Text("Reproducir Audio")
                    }
                }

            }
        }

        if (isEditing) {
            CustomKeyboard(
                onCharClick = { char ->
                    currentText += char
                    if (activeTextField == 1) {
                        onTextArea1ValueChanged(currentText)
                    } else {
                        onTextArea2ValueChanged(currentText)
                    }
                },
                onDeleteClick = {
                    if (currentText.isNotEmpty()) {
                        currentText = currentText.dropLast(1)
                        if (activeTextField == 1) {
                            onTextArea1ValueChanged(currentText)
                        } else {
                            onTextArea2ValueChanged(currentText)
                        }
                    }
                },
                onSpaceClick = onSpaceClick,
                onHideKeyboard = {
                    isEditing = false
                    focusManager.clearFocus() // Limpia el foco del TextField
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp) // Ajusta la altura del teclado según el diseño
            )
        }
    }
}