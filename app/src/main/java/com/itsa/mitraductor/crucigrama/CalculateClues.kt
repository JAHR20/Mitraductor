package com.itsa.mitraductor.crucigrama

// Función para calcular las pistas del crucigrama
fun calculateClues(words: List<CrosswordWord>): Pair<List<String>, List<String>> {
    val horizontalClues = words.filter { it.directionPalabra == DirectionPalabra.HORIZONTAL }.map { it.clue }
    val verticalClues = words.filter { it.directionPalabra == DirectionPalabra.VERTICAL }.map { it.clue }
    return Pair(horizontalClues, verticalClues)
}