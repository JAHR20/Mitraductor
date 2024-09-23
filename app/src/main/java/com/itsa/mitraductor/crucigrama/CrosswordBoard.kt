package com.itsa.mitraductor.crucigrama

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// Clase para representar el tablero del crucigrama
class CrosswordBoard(private val size: Int) {
    val board: MutableState<Array<Array<Char>>> = mutableStateOf(Array(size) { Array(size) { ' ' } })
    val displayBoard: MutableState<Array<Array<Char>>> = mutableStateOf(Array(size) { Array(size) { ' ' } })
    val words: MutableList<CrosswordWord> = mutableListOf()
    val correctWordPositions: MutableState<Set<Pair<Int, Int>>> = mutableStateOf(emptySet()) // Posiciones de las letras de palabras verificadas correctamente

    // Método para agregar una palabra al tablero
    fun addWord(word: String, clue: String, row: Int, column: Int, directionPalabra: DirectionPalabra) {
        val wordChars = word.toCharArray()
        var currentRow = row
        var currentColumn = column
        for (char in wordChars) {
            if (board.value[currentRow][currentColumn] == ' ' || board.value[currentRow][currentColumn] == char) {
                board.value[currentRow][currentColumn] = char
            } else {
                throw IllegalArgumentException("Word placement conflict")
            }
            if (directionPalabra == DirectionPalabra.HORIZONTAL) {
                currentColumn++
            } else {
                currentRow++
            }
        }
        words.add(CrosswordWord(word, clue, row, column, directionPalabra))

        // Actualizar displayBoard con la palabra agregada
        for ((index, char) in word.withIndex()) {
            val newRow = if (directionPalabra == DirectionPalabra.HORIZONTAL) row else row + index
            val newColumn = if (directionPalabra == DirectionPalabra.HORIZONTAL) column + index else column
            displayBoard.value[newRow][newColumn] = char
        }
    }

    // Método para verificar si una palabra ingresada es correcta
    fun checkWord(word: String): Boolean {
        val correctWord = words.find { it.word.equals(word, ignoreCase = true) }
        if (correctWord != null) {
            var currentRow = correctWord.row
            var currentColumn = correctWord.column
            val wordPositions = correctWordPositions.value.toMutableSet()
            for (char in correctWord.word) {
                displayBoard.value[currentRow][currentColumn] = char
                wordPositions.add(currentRow to currentColumn)
                if (correctWord.directionPalabra == DirectionPalabra.HORIZONTAL) {
                    currentColumn++
                } else {
                    currentRow++
                }
            }
            correctWordPositions.value = wordPositions
            return true
        }
        return false
    }

    // Método para verificar si una celda contiene una letra de una palabra verificada correctamente
    fun isCorrectCell(row: Int, column: Int): Boolean {
        return correctWordPositions.value.contains(row to column)
    }

    // Método para reiniciar el tablero, cambiando todas las letras a blanco (invisible)
    fun resetBoardColors() {
        correctWordPositions.value = emptySet() // Limpiar las posiciones correctas
        displayBoard.value = Array(size) { Array(size) { ' ' } } // Reiniciar el displayBoard
    }
}