package com.itsa.mitraductor.crucigrama

// Función para obtener el número de pista en la posición de la celda
fun getClueNumber(row: Int, column: Int, words: List<CrosswordWord>): Int? {
    val matchingWords = words.filter { it.row == row && it.column == column }
    return if (matchingWords.isNotEmpty()) {
        // Si hay varias palabras que comienzan en esta celda, devolver el número de pista de la primera palabra
        val firstWord = matchingWords.first()
        words.indexOf(firstWord) + 1
    } else {
        null
    }
}