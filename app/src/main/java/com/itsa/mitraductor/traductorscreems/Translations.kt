package com.itsa.mitraductor.traductorscreems

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import com.itsa.mitraductor.ui.theme.TraduccionesData
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.util.Locale

//funcion para acceder a json
fun cargarTraducciones(inputStream: InputStream): Map<String, String> {
    val jsonString = inputStream.bufferedReader().use { it.readText() }
    val json = Json { ignoreUnknownKeys = true }
    val traduccionesData = json.decodeFromString<TraduccionesData>(jsonString)
    return traduccionesData.traducciones
}

// Actualiza la función de traducción
fun traducir(textoOriginal: String, traducciones: Map<String, String>): String {
    println("Texto Original: $textoOriginal")
    println("Traducciones: $traducciones")

    val traduccion = traducciones[textoOriginal
        .trim().
        lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }] ?: "Traducción no disponible"

    println("Traducción: $traduccion")

    return traduccion
}

fun reproducirAudio(palabra: String, region: String, context: Context) {
    val nombreArchivo = "audios/palabra_${palabra
        .trim()
        .lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}" +
            "_${region.toLowerCase(Locale.ROOT).replace(" ","")}.mp3"
    println("Nombre del archivo de audio: $nombreArchivo")

    try {
        // Crear el reproductor de audio y reproducir el audio
        val assetFileDescriptor = context.assets.openFd(nombreArchivo)
        val mediaPlayer = MediaPlayer().apply {
            setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
            prepare()
            start()
        }
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release() // Liberar recursos después de reproducir el audio
        }
    } catch (e: IOException) {
        Toast.makeText(context, "No se encontró el audio para \"$palabra\" en la región \"$region\"", Toast.LENGTH_SHORT).show()
    }
}



// Función para buscar la clave basada en el valor en el JSON
fun traducirDesdeValor(textoTraducido: String, traducciones: Map<String, String>): String {
    for ((clave, valorTraduccion) in traducciones) {
        println("Este es el valorTradducción" + valorTraduccion)
        println("Este es el valor" + textoTraducido)
        if (valorTraduccion == textoTraducido
                .trim()
                .lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) {
            println("Este es la clave" + clave)
            return clave
        }

    }
    return "Clave no encontrada"
}