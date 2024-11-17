package com.itsa.mitraductor.fillntheblanks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
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
fun FillInTheBlanksGame(navController: NavController,region: String) {

    val (sentences, options) = when (region) {
        "Soteapan" -> getRegionSoteapanData()
        "Sayula" -> getRegionSayulaData()
        // Agrega más casos según sea necesario para otras regiones
        else -> getRegionSoteapanData() // Por defecto, carga los datos de la región 1
    }

    val remainingSentenceIndices = remember { mutableStateListOf(*sentences.indices.toList().toTypedArray()) }
    val currentSentenceIndex = remember { mutableStateOf(remainingSentenceIndices.removeAt(Random.nextInt(remainingSentenceIndices.size))) }
    val userAnswer = remember { mutableStateOf(Pair("", "")) }
    val showDialog = remember { mutableStateOf(false) }
    val dialogText = remember { mutableStateOf("") }
    val gameFinished = remember { mutableStateOf(false) }
    val showGameFinishedText = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "A completa la oración",
                navController = navController, // Pasa el NavController al composable del botón de retroceso
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (!gameFinished.value) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.weight(0.7f))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Instrucción: Acompleta la oración con la opción correcta",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                    color = Color(0xFF081883)
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.7f))
                        Text(text = sentences[currentSentenceIndex.value].first.first,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.robotomonobold)),
                                fontWeight = FontWeight.Bold,
                                fontSize = 23.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.weight(0.3f))
                        Text(text = sentences[currentSentenceIndex.value].second.first,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.robotomonobold)),
                                fontWeight = FontWeight.Bold,
                                fontSize = 23.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.weight(0.4f))
                        options[currentSentenceIndex.value].forEach { option ->
                            Button(onClick = {
                                userAnswer.value = option
                                if (userAnswer.value.first == sentences[currentSentenceIndex.value].first.second &&
                                    userAnswer.value.second == sentences[currentSentenceIndex.value].second.second) {
                                    // Ambas respuestas son correctas
                                    dialogText.value = "¡Exelente, Respuesta correcta!"
                                    if (remainingSentenceIndices.isEmpty()) {
                                        // No hay más oraciones, el juego ha terminado
                                        gameFinished.value = true
                                    } else {
                                        // Pasa a la siguiente oración
                                        currentSentenceIndex.value = remainingSentenceIndices.removeAt(Random.nextInt(remainingSentenceIndices.size))
                                    }
                                } else {
                                    // Al menos una respuesta es incorrecta, muestra un mensaje de error
                                    dialogText.value = "Respuesta incorrecta, por favor intenta de nuevo."
                                }
                                // Muestra el AlertDialog
                                showDialog.value = true
                                // Limpia la respuesta del usuario
                                userAnswer.value = Pair("", "")
                            }) {
                                Text("${option.first} / ${option.second}")
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                } else if (showGameFinishedText.value) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Juego terminado", fontSize = 30.sp)
                        IconButton(
                            onClick = { // Reinicia el juego
                                remainingSentenceIndices.addAll(sentences.indices.toList())
                                currentSentenceIndex.value = remainingSentenceIndices.removeAt(Random.nextInt(remainingSentenceIndices.size))
                                userAnswer.value = Pair("", "")
                                gameFinished.value = false
                                showGameFinishedText.value = false },
                            modifier = Modifier
                                .padding(16.dp)
                                .size(56.dp)
                        ) {
                            Icon(
                                Icons.Filled.Refresh,
                                contentDescription = "Reload Game",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                }
                if (showDialog.value) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog.value = false
                            if (gameFinished.value && !showDialog.value) {
                                showGameFinishedText.value = true
                            }
                        },
                        title = { Text("Resultado") },
                        text = { Text(dialogText.value) },
                        confirmButton = {
                            Button(onClick = {
                                showDialog.value = false
                                if (gameFinished.value && !showDialog.value) {
                                    showGameFinishedText.value = true
                                }
                            }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    )
}

// Función para obtener datos de la región 1
private fun getRegionSoteapanData(): Pair<List<Pair<Pair<String, String>, Pair<String, String>>>, List<List<Pair<String, String>>>> {
    val sentences = listOf(
        Pair(Pair("El _____ caza ratones.", "gato"), Pair("Jem _____ i matspa tsuk.", "missi")),
        Pair(Pair("El _____ corre mucho.", "caballo"), Pair("Jem _____ tsam poymichpa.", "kawaj")),
        Pair(Pair("La _____ esta hecha de madera.", "casa"), Pair("Jem _____ watnita kuymɨ.", "tɨk")),
        Pair(Pair("La mujer cocina la _____.", "comida"), Pair("Jem yoomo _____ watpa’.", "wikkuy")),
        Pair(Pair("El hombre trabaja en la _____.", "milpa"), Pair("Jem pɨɨxɨñ joxap i _____.", "kamjoom")),
        Pair(Pair("El sol es muy _____.", "brillante"), Pair("Jem jamma tsam _____’.", "tsotpa’")),
        Pair(Pair("El _____ ilumina la noche.", "relámpago"), Pair("Jem _____ i kitiwiŋwatpa tsuu’.", "majiywiñ")),
        Pair(Pair("El arcoiris aparece en el _____.", "cielo"), Pair("Jem juŋichi kejpa _____.", "siŋyuku")),
        Pair(Pair("La _____ cae sobre el tejado.", "lluvia"), Pair("Jem _____ aktiŋba tɨk akkobakmɨ.", "tuu’")),
        Pair(Pair("El _____ ladra a la gente.", "perro"), Pair("Jem _____ i waspa tuŋwityi.", "chimpa’")),
        Pair(Pair("El _____ canta en los árboles.", "pajaro"), Pair("Jem _____ waŋba kuyyuku.", "jon")),
        Pair(Pair("El _____ juega con sus juguetes.", "niño"), Pair("Jem _____ michpa i michkuymɨ.", "tsɨɨxi")),
        Pair(Pair("Las flores florecen en _____.", "primavera"), Pair("Jem mooyayaj tojyajpa _____.", "kujamsaŋ")),
        Pair(Pair("Las estrellas brillan en la _____.", "noche"), Pair("Jem matsa tsokpa _____.", "pichkɨm")),


        // Agrega más oraciones aquí
    )

    val options = listOf(
        listOf(Pair("gato", "missi"), Pair("perro", "chimpa’"), Pair("árbol", "kuy"), Pair("tortuga", "tyuki"), Pair("pollo", "piyu’")),
        listOf(Pair("espejo", "teskat"), Pair("jarro", "suŋ"), Pair("caballo", "kawaj"), Pair("árbol", "kuy"), Pair("pescado", "tɨɨpɨ")),
        listOf(Pair("cobija", "tsujmity"), Pair("casa", "tɨk"), Pair("almohada", "kutsɨyi"), Pair("niña", "wooñi’"), Pair("paloma", "ku’uku")),
        listOf(Pair("leña", "kɨpi"), Pair("piedra", "tsa’"), Pair("ropa", "puktuku’"), Pair("comida", "wikkuy"), Pair("sandalia", "kɨak")),
        listOf(Pair("milpa", "kamjoom"), Pair("tortilla", "añi"), Pair("miel", "chiñu"), Pair("cobija", "tsujmity"), Pair("hoja", "ay")),
        listOf(Pair("gordo", "pɨɨne’"), Pair("delgado", "wayay"), Pair("verde", "tsus"), Pair("frio", "pagak"), Pair("brillante", "tsotpa’")),
        listOf(Pair("Perico", "katsa’"), Pair("relámpago", "majiywiñ"), Pair("murciélago", "tɨɨxi"), Pair("grillo", "tsuñi"), Pair("sombrero", "kubaktɨk")),
        listOf(Pair("cielo", "siŋyuku"), Pair("cedro", "akuy"), Pair("árbol", "kuy"), Pair("viento", "saawa"), Pair("fuego", "juktɨ")),
        listOf(Pair("sal", "kaana"), Pair("servilleta", "maŋtyelax"), Pair("lluvia", "tuu’"), Pair("carne", "maayi"), Pair("hormiga", "jajtsuk")),
        listOf(Pair("venado", "mɨa"), Pair("pantalón", "nokkoy"), Pair("pescado", "tɨɨpɨ"), Pair("perro", "chimpa’"), Pair("plátano", "samñi")),
        listOf(Pair("pajaro", "jon"), Pair("cocodrilo", "uxpiñ"), Pair("zancudo", "jeeje’"), Pair("jabón", "xapun"), Pair("espejo", "teskat")),
        listOf(Pair("collar", "naŋtsaŋ"), Pair("agua", "nɨ"), Pair("niño", "tsɨɨxi"), Pair("maiz", "ɨkxi"), Pair("costal", "kustyat")),
        listOf(Pair("sueño", "mawiñ"), Pair("primavera", "kujamsaŋ"), Pair("tabla", "tɨkaŋtana"), Pair("piedra", "tsa’"), Pair("telaraña", "pe’eñi")),
        listOf(Pair("botella", "pok"), Pair("escoba", "petkuy"), Pair("basura", "puchi"), Pair("escalera", "kitɨk"), Pair("noche", "pichkɨm")),

        // Agrega más opciones aquí
    )

    return Pair(sentences, options)
}

private fun getRegionSayulaData(): Pair<List<Pair<Pair<String, String>, Pair<String, String>>>, List<List<Pair<String, String>>>> {
    val sentences = listOf(
        Pair(Pair("El ____ tiene alas.", "pájaro"), Pair("Jem _____ kuyyuku.", "komo")),
        Pair(Pair("La _____ es una fruta.", "manzana"), Pair("Jem _____ mikus.", "tsɨtsy")),
        Pair(Pair("El ____ es un animal doméstico.", "perro"), Pair("Jem _____ tuŋwityi.", "tɨtɨy")),
        Pair(Pair("El ____ canta por la noche.", "búho"), Pair("Jem _____ jompa.", "komom")),
        Pair(Pair("La _____ es una bebida caliente.", "café"), Pair("Jem _____ tukkɨm.", "kum")),
        Pair(Pair("El ____ vive en el agua.", "pez"), Pair("Jem _____ joom.", "jim")),
        Pair(Pair("El ____ es una fruta roja.", "fresa"), Pair("Jem _____ kamyat.", "pɨɨm")),
        Pair(Pair("La _____ es un instrumento musical.", "guitarra"), Pair("Jem _____ wiwiñ.", "ma")),

        // Agrega más oraciones aquí
    )

    val options = listOf(
        listOf(Pair("pájaro", "komo"), Pair("perro", "tɨtɨy"), Pair("gato", "missi"), Pair("tortuga", "tyuki"), Pair("pescado", "tɨɨpɨ")),
        listOf(Pair("manzana", "tsɨtsy"), Pair("plátano", "samñi"), Pair("naranja", "tsujij"), Pair("limón", "mɨchim"), Pair("fresa", "pɨɨm")),
        listOf(Pair("perro", "tɨtɨy"), Pair("gato", "missi"), Pair("conejo", "mook"), Pair("ratón", "tsa’"), Pair("búho", "komom")),
        listOf(Pair("búho", "komom"), Pair("perico", "katsa’"), Pair("cotorro", "pu’y"), Pair("loro", "pik"), Pair("paloma", "ku’uku")),
        listOf(Pair("café", "kum"), Pair("té", "komɨn"), Pair("chocolate", "kaaka’"), Pair("agua", "nɨ"), Pair("refresco", "kukum")),
        listOf(Pair("pez", "jim"), Pair("ballena", "pɨɨñu’"), Pair("tiburón", "tujiñ"), Pair("delfín", "jaja’"), Pair("langosta", "jaa")),
        listOf(Pair("fresa", "pɨɨm"), Pair("uva", "peej"), Pair("cereza", "pɨɨm"), Pair("manzana", "tsɨtsy"), Pair("pera", "pɨɨm")),
        listOf(Pair("guitarra", "ma"), Pair("flauta", "pii"), Pair("piano", "tsutu"), Pair("trompeta", "mɨntɨ"), Pair("violín", "ma’")),
        // Agrega más opciones aquí
    )

    return Pair(sentences, options)
}
