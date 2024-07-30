package com.itsa.mitraductor.memorama

val subcategoriesByRegion = mapOf(
    "Soteapan" to listOf("Familia", "Animales"),
    "Sayula" to listOf("Animales","Frutas"),
    "Oluta" to listOf("Frutas","Animales"),
    "Texistepec" to listOf("Animales"),
    "San Gabriel Chilac" to listOf("Animales"),
    "Ocotepec" to listOf("Animales")
    // Añade más regiones y sus respectivas subcategorías
)

val lenguaMaterna = mapOf(
    "Soteapan" to "Popoluca",
    "Oluta" to "Popoluca",
    "Texistepec" to "Popoluca",
    "Sayula" to "Popoluca",
    "Ocotepec" to "Mixe",
    "San Gabriel Chilac" to "Náhuatl",
    "default" to "DefaultPalabra"
)

fun getWordForRegion(region: String): String {
    return lenguaMaterna[region] ?: lenguaMaterna["default"] ?: "Memorama"
}