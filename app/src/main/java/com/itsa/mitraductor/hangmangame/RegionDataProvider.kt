package com.itsa.mitraductor.hangmangame

interface WordImageProvider {
    val wordToImageMap: Map<String, Int>
    val gameWords: List<String>
}

