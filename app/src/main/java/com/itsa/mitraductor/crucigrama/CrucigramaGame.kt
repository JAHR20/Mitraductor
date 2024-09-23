package com.itsa.mitraductor.crucigrama

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.enableLiveLiterals
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsa.mitraductor.app.CustomKeyboard
import com.itsa.mitraductor.app.KeyboardVisibilityObserver
import com.itsa.mitraductor.app.ToolbarWithBackButton
import java.util.Locale

// Estructura de datos para una palabra en el crucigrama
data class CrosswordWord(val word: String, val clue: String, val row: Int, val column: Int, val directionPalabra: DirectionPalabra)
enum class DirectionPalabra { HORIZONTAL, VERTICAL }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CrosswordGame(navController: NavController, region: String) {
    val crosswordBoard = remember { CrosswordBoard(size = 12) }
    val focusManager = LocalFocusManager.current

    when (region) {
        "Soteapan" -> loadWordsForSoteapan(crosswordBoard)
        "Sayula" -> loadWordsForSayula(crosswordBoard)
        "Oluta" -> loadWordsForOluta(crosswordBoard)
        "Texistepec" -> loadWordsForTexistepec(crosswordBoard)
        "San Gabriel Chilac" -> loadWordsForNorte(crosswordBoard)
        "Ocotepec" -> loadWordsForIstmo(crosswordBoard)
        // Agrega más casos para otras regiones aquí si es necesario
    }

    var specialLetter by remember { mutableStateOf<Char?>(null) }
    val clues = remember { calculateClues(crosswordBoard.words) } // Calcular las pistas una vez
    var enteredWord by remember { mutableStateOf("") }
    enteredWord = enteredWord.lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    var message by remember { mutableStateOf("") }

    // Comprobar si todas las palabras están completadas
    val allWordsCompleted = crosswordBoard.words.all { word ->
        word.word.all { char ->
            crosswordBoard.correctWordPositions.value.contains(word.row to word.column) || char == ' '
        }
    }

    if (allWordsCompleted) {
        message = "¡Felicidades, ganaste!"
    }

    var isEditing by remember { mutableStateOf(false) }
    var keyboardHeight by remember { mutableStateOf(0) }

    // Observer para la visibilidad del teclado y la altura
    KeyboardVisibilityObserver { isVisible, height ->
        keyboardHeight = if (isVisible) height else 0
    }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Crucigrama",
                navController = navController // Pasa el NavController al composable del botón de retroceso
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, bottom = keyboardHeight.dp) // Ajustar el padding inferior según la altura del teclado
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        CrosswordBoardView(
                            crosswordBoard = crosswordBoard,
                            clues = clues, // Pasar las pistas como parámetro
                            enteredWord = enteredWord,
                            onCheck = {
                                if (enteredWord.isNotBlank()) {
                                    if (crosswordBoard.checkWord(enteredWord.trim())) {
                                        message = "Correcto!"
                                        enteredWord = "" // Limpiar la palabra ingresada después de verificarla
                                    } else {
                                        message = "Incorrecto! Inténtalo de nuevo."
                                    }
                                }
                            },
                            onSpecialLetterClick = { specialLetter = it }
                        )

                        // Actualizar TextField con el valor de enteredWord y specialLetter
                        LaunchedEffect(enteredWord, specialLetter) {
                            specialLetter?.let {
                                enteredWord += it
                                specialLetter = null // Restablecer la letra especial después de agregarla al TextField
                            }
                        }

                        val isGameWon = message == "¡Felicidades, ganaste!"
                        TextField(
                            value = enteredWord,
                            onValueChange = { enteredWord = it },
                            label = { Text("Ingresa una palabra", fontSize = 18.sp) },
                            //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            modifier = Modifier
                                .onFocusChanged { focusState ->
                                    isEditing = focusState.isFocused
                                },
                            readOnly = true,
                            // Se usa un teclado personalizado
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                            keyboardActions = KeyboardActions.Default,
                            enabled = !isGameWon,
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        MessageWithFormat(message = message)

                        Spacer(modifier = Modifier.height(30.dp))

                        // Mostrar el botón de reinicio cuando el jugador gane
                        if (isGameWon) {
                            Button(
                                onClick = {
                                    crosswordBoard.resetBoardColors()
                                    message = "" // Reinicia el mensaje
                                },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Reiniciar Juego")
                            }
                        }
                    }
                }

                // Mostrar el teclado personalizado si el TextField está en foco
                if (isEditing) {
                    CustomKeyboard(
                        onCharClick = { char ->
                            enteredWord += char
                        },
                        onDeleteClick = {
                            if (enteredWord.isNotEmpty()) {
                                enteredWord = enteredWord.dropLast(1)
                            }
                        },
                        onSpaceClick = {
                            enteredWord += " "
                        },
                        onHideKeyboard = {
                            isEditing = false
                            focusManager.clearFocus() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(310.dp) // Ajusta la altura del teclado según el diseño
                    )
                }
            }
        }
    )
}


