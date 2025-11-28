package com.itsa.mitraductor.memorama

val subcategoriesByRegion = mapOf(
    "Soteapan" to listOf("Nivel 1", "Nivel 2", "Nivel 3"),
    "Sayula" to listOf("Nivel 1", "Nivel 2"),
    "Oluta" to listOf("Nivel 1","Nivel 2"),
    "Texistepec" to listOf("Nivel 1"),
    "San Gabriel Chilac" to listOf("Nivel 1", "Nivel 2"),
    "Ocotepec" to listOf("Nivel 1"),
    "Manzanillo" to listOf("Nivel 1", "Nivel 2")
    // Añade más regiones y sus respectivas subcategorías
)

val lenguaMaterna = mapOf(
    "Soteapan" to "Popoluca",
    "Oluta" to "Popoluca",
    "Texistepec" to "Popoluca",
    "Sayula" to "Popoluca",
    "Ocotepec" to "Mixe",
    "San Gabriel Chilac" to "Náhuatl",
    "Manzanillo" to "Náhuatl",
    "default" to "DefaultPalabra"
)

fun getWordForRegion(region: String): String {
    return lenguaMaterna[region] ?: lenguaMaterna["default"] ?: "Memorama"
}