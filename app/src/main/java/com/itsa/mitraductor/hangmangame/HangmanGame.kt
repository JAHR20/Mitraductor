package com.itsa.mitraductor.hangmangame

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsa.mitraductor.R
import com.itsa.mitraductor.app.ToolbarWithBackButton
import kotlin.random.Random

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HangmanScreen(
    navController: NavController,
    region: String
) {
    val regionDataProvider = getRegionDataProvider(region)
    val gameManager = remember { GameManager(regionDataProvider) }
    val gameState = remember { mutableStateOf(gameManager.startNewGame()) }
    val lettersUsed = remember { mutableStateOf(setOf<Char>()) }
    val currentWord = remember { mutableStateOf(gameManager.getWordToGuess()) }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Juego del ahorcado",
                navController = navController // Pass NavController to the back button composable
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), // Set maximum size for LazyColumn
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    val drawableResource = when (val state = gameState.value) {
                        is GameState.Running -> state.drawable
                        is GameState.Lost -> R.drawable.game7 // or the image representing the lost state
                        is GameState.Won -> R.drawable.game0 // or the image representing the won state
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Image(
                        painter = painterResource(id = drawableResource),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )

                    val currentImage = regionDataProvider.wordToImageMap[currentWord.value]
                    if (currentImage != null) {
                        Image(
                            painter = painterResource(id = currentImage),
                            contentDescription = "Hint for the word ${currentWord.value}",
                            modifier = Modifier.fillMaxWidth().height(80.dp)
                        )
                    }
                    when (gameState.value) {
                        is GameState.Lost -> Text(
                            text = "\uD83D\uDE14¡Perdiste!\uD83D\uDE14",
                            fontSize = 36.sp,
                            color = Color(0xFFBB4444),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .rotate(-45f)
                                .padding(top = 16.dp)
                        )

                        is GameState.Running -> {
                            Text(
                                text = (gameState.value as GameState.Running).underscoreWord,
                                fontSize = 26.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 16.dp)
                            )

                            Text(
                                text = "Letras usadas: ${lettersUsed.value.joinToString()}",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 16.dp)
                            )

                        }

                        is GameState.Won -> Text(
                            text = "\uD83C\uDF89¡Ganaste!\uD83C\uDF89",
                            fontSize = 36.sp,
                            color = Color(0xFF44BB44),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .rotate(-45f)
                                .padding(top = 16.dp)
                        )
                    }

                    AllLettersLayout(lettersUsed = lettersUsed.value, onLetterClick = { letter ->
                        if (letter.single() !in lettersUsed.value) {
                            lettersUsed.value = lettersUsed.value + letter.single()
                            gameState.value = gameManager.play(letter.single())
                        }
                    }, gameState = gameState.value)

                    when (gameState.value) {
                        is GameState.Lost, is GameState.Won -> {
                            Button(onClick = {
                                gameState.value = gameManager.startNewGame()
                                currentWord.value = gameManager.getWordToGuess()
                                lettersUsed.value = setOf<Char>()
                            }) {
                                Text(text = "Juego nuevo")
                            }
                        }

                        else -> { /* Do nothing */ }
                    }
                }
            }
        }
    )
}

@Composable
fun LetterButton(letter: String, onClick: (String) -> Unit, enabled: Boolean) {
    Button(
        onClick = { onClick(letter) },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = if (enabled) Color(0xFF6200EE) else Color.LightGray),
        modifier = Modifier.padding(4.dp).width(50.dp).height(40.dp)
    ) {
        Text(
            text = letter,
            fontFamily = FontFamily.Monospace,
            color = if (enabled) Color.White else Color.LightGray,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AllLettersLayout(lettersUsed: Set<Char>, onLetterClick: (String) -> Unit, gameState: GameState) {
    val letters = remember { mutableStateOf(listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "ɨ", "’", "ŋ", ":", "č", "¢", "š", "?")) }
    val gameIsRunning = gameState is GameState.Running
    val chunkedLetters = letters.value.chunked(5) // Divide the list into sublists of 5 elements each

    Log.e("En el boton", "que pasa $onLetterClick")

    BoxWithConstraints {
        val cellSize = maxOf(60.dp, with(LocalDensity.current) { (maxWidth.value / 5f).toDp() })

        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center
        ) {
            chunkedLetters.forEach { rowLetters ->
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    rowLetters.forEach { letter ->
                        Box(modifier = Modifier.size(cellSize)) {
                            LetterButton(letter = letter, onClick = { if (gameIsRunning && letter.single() !in lettersUsed) onLetterClick(letter) }, enabled = gameIsRunning && letter.single() !in lettersUsed)
                        }
                    }
                }
            }
        }
    }
}

class GameManager(private val provider: WordImageProvider) {
    private var lettersUsed: String = ""
    private lateinit var underscoreWord: String
    private lateinit var wordToGuess: String
    private val maxTries = 7
    private var currentTries = 0
    private var drawable: Int = R.drawable.game0

    init {
        startNewGame()
    }

    fun getWordToGuess(): String {
        return wordToGuess
    }

    fun startNewGame(): GameState {
        lettersUsed = ""
        currentTries = 0
        drawable = R.drawable.game7
        val randomIndex = Random.nextInt(0, provider.gameWords.size)
        wordToGuess = provider.gameWords[randomIndex]
        generateUnderscores(wordToGuess)
        return getGameState()
    }

    fun generateUnderscores(word: String) {
        val sb = StringBuilder()
        word.forEach { char ->
            if (char == '/') {
                sb.append('/')
            } else {
                sb.append("_ ")
            }
        }
        underscoreWord = sb.toString()
    }

    fun play(letter: Char): GameState {
        if (lettersUsed.contains(letter)) {
            return GameState.Running(lettersUsed, underscoreWord, drawable)
        }

        lettersUsed += letter
        val indexes = mutableListOf<Int>()

        wordToGuess.forEachIndexed { index, char ->
            if (char.equals(letter, true)) {
                indexes.add(index)
            }
        }

        var finalUnderscoreWord = "" + underscoreWord // _ _ _ _ _ _ _ -> E _ _ _ _ _ _
        indexes.forEach { index ->
            val sb = StringBuilder(finalUnderscoreWord).also { it.setCharAt(index * 2, letter) }
            finalUnderscoreWord = sb.toString()
        }

        if (indexes.isEmpty()) {
            currentTries++
            drawable = getHangmanDrawable() // Update the hangman image here
        }

        underscoreWord = finalUnderscoreWord
        return getGameState()
    }

    private fun getHangmanDrawable(): Int {
        return when (currentTries) {
            0 -> R.drawable.game0
            1 -> R.drawable.game1
            2 -> R.drawable.game2
            3 -> R.drawable.game3
            4 -> R.drawable.game4
            5 -> R.drawable.game5
            6 -> R.drawable.game6
            7 -> R.drawable.game7
            else -> R.drawable.game7
        }
    }

    private fun getGameState(): GameState {
        if (underscoreWord.replace(" ", "").equals(wordToGuess, true)) {
            return GameState.Won(wordToGuess)
        }

        if (currentTries == maxTries) {
            return GameState.Lost(wordToGuess)
        }

        drawable = getHangmanDrawable()
        return GameState.Running(lettersUsed, underscoreWord, drawable)
    }
}

sealed class GameState {
    data class Running(val lettersUsed: String, val underscoreWord: String, val drawable: Int) : GameState()
    data class Won(val wordToGuess: String) : GameState()
    data class Lost(val wordToGuess: String) : GameState()
}


