package com.itsa.mitraductor.soupgame
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsa.mitraductor.R
import com.itsa.mitraductor.app.ToolbarWithBackButton
import kotlin.math.abs
import kotlin.random.Random
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SopaDeLetras(navController: NavController, region: String) {
    // Mapa de palabras por región
    val palabrasPorRegion = mapOf(
        "Soteapan" to mapOf(
            "CABALLO" to "KAWAJ", "TORTUGA" to "TYUKI", "POLLO" to "PIYU’", "PAJARO" to "JON", "TUCÁN" to "KATSKATS",
            "CERDO" to "YOOYA", "ARDILLA" to "KUŊKI", "PERRO" to "CHIMPA’",
            "RATON" to "TSUK", "TIGRE" to "KAAŊ"
        ),
        "Sayula" to mapOf(
            "PESCADO" to "AKX",
            "Bailar" to "ECH",
            "PUERCO" to "ICHIM",
            "TORTILLA" to "NɨN",
            "CAMARÓN" to "OOY",
            "PÁJARO" to "JOJN",
            "ESTRELLA" to "MAAJTS",
            "MOLE" to "UP",
            "AGUA" to "Nɨ’ɨ",
            "HIJA" to "AJY"
        ),
        "Oluta" to mapOf(
            "AGUA" to "NUJU",
            "BIGOTE" to "AVAYU",
            "CANGREJO" to "ESHI",
            "DESAYUNÓ" to "UQUI",
            "ENFERMO" to "VONI",
            "FRUTA" to "TUMU",
            "GRILLO" to "POCHI'C",
            "HOJA" to "AYU",
            "JAULA" to "NACA'N",
            "MOSCA" to "TSACHI"
        ),
        "Texistepec" to mapOf(
            "HUESO" to "PAK",
            "CARACOL" to "SO:KKE?",
            "ROBALO" to "?AKSA?",
            "AlACRÁN" to "KAKWE?Ñ",
            "CALABAZA" to "kALBAS",
            "TRUENO" to "BIYWAY",
            "ARBOL" to "BEMSA?",
            "BOCA" to "HIP",
            "GUAYABA" to "PATAŊ",
            "CASA" to "TƗK"
        ),
        "San Gabriel Chilac" to mapOf(
            "AGUA" to "ATL",
            "CASA" to "CALI",
            "PESCADO" to "MICHI",
            "ELOTE" to "ELOTL",
            "CAÑA" to "OHUATL",
            "PUERCO" to "PITZOTL",
            "ALGODÓN" to "ICHCATL",
            "CERRO" to "TEPETL",
            "ARENA" to "XALI",
            "PIEDRA" to "TETL"
        ),
        "Ocotepec" to mapOf(
            "PELOTA" to "OOMY",
            "PLATO" to "TEXY",
            "LLUVIA" to "TUU",
            "MAMÁ" to "MAAM",
            "PIÑA" to "TSIKTY",
            "ARBOL" to "KOPK",
            "OVEJA" to "MEEK",
            "PALOMA" to "MUUXY",
            "ARROZ" to "AROOSK",
            "ELOTE" to "YAAW"
        )
        // Agrega más regiones y sus respectivos mapas de palabras aquí
    )

    // Obtener las palabras de la región actual
    val palabrasABuscar = palabrasPorRegion[region] ?: emptyMap()

    // Aquí puedes almacenar las letras seleccionadas por el usuario
    var letrasSeleccionadas by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }

    // Aquí puedes almacenar las palabras encontradas por el usuario
    var palabrasEncontradas by remember { mutableStateOf(mapOf<String, List<Pair<Int, Int>>>()) }

    // Aquí puedes generar tu sopa de letras
    var sopaDeLetras by remember { mutableStateOf(generarSopaDeLetras(palabrasABuscar.values.toList())) }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Sopa de Letras",
                navController = navController // Pasa el NavController al composable del botón de retroceso
            )
        },

        content = {
            Column(
                modifier = Modifier.padding(5.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(60.dp))
                // Cuadro con las palabras a buscar
                Text(
                    text = "Palabras a buscar:",
                    modifier = Modifier.padding(4.dp),
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF6A1B9A)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Cuadro con las claves y las palabras a buscar
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                ) {
                    val palabrasPorFila = 3 // Ajusta este número según tus necesidades
                    val filasDePalabras = palabrasABuscar.toList().chunked(palabrasPorFila)
                    Column(modifier = Modifier.padding(4.dp)) {
                        filasDePalabras.forEach { filaDePalabras ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                filaDePalabras.forEach { (palabra, traduccion) ->
                                    Text(
                                        style = TextStyle(fontSize = 13.sp),
                                        text = "  $palabra=",
                                        color = Color.Black
                                    )
                                    Text(
                                        style = TextStyle(fontSize = 13.sp),
                                        text = traduccion,
                                        color = if (palabrasEncontradas.keys.contains(traduccion)) Color(
                                            0xFF32F110
                                        ) else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                // Sopa de letras
                LazyColumn {
                    items(sopaDeLetras.size) { filaIndice ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            sopaDeLetras[filaIndice].forEachIndexed { columnaIndice, letra ->
                                val estaSeleccionada =
                                    letrasSeleccionadas.contains(
                                        Pair(
                                            filaIndice,
                                            columnaIndice
                                        )
                                    )
                                val esParteDePalabraEncontrada =
                                    palabrasEncontradas.values.flatten()
                                        .contains(Pair(filaIndice, columnaIndice))
                                Text(
                                    text = letra.toString(),
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .clickable(
                                            enabled = !palabrasEncontradas.keys.containsAll(
                                                palabrasABuscar.values
                                            )
                                        ) {
                                            if (estaSeleccionada && letrasSeleccionadas.last() == Pair(
                                                    filaIndice,
                                                    columnaIndice
                                                )
                                            ) {
                                                letrasSeleccionadas =
                                                    letrasSeleccionadas.dropLast(1)
                                            } else if (!estaSeleccionada && (letrasSeleccionadas.isEmpty() || esAdyacente(
                                                    letrasSeleccionadas.last(),
                                                    Pair(filaIndice, columnaIndice)
                                                ))
                                            ) {
                                                if (letrasSeleccionadas.size < 2 || esMismaDireccion(
                                                        letrasSeleccionadas,
                                                        Pair(filaIndice, columnaIndice)
                                                    )
                                                ) {
                                                    letrasSeleccionadas =
                                                        letrasSeleccionadas + Pair(
                                                            filaIndice,
                                                            columnaIndice
                                                        )
                                                }
                                            }
                                            val palabraFormada =
                                                letrasSeleccionadas.map { (fila, columna) -> sopaDeLetras[fila][columna] }
                                                    .joinToString("")
                                            if (palabraFormada in palabrasABuscar.values && palabraFormada !in palabrasEncontradas.keys) {
                                                println("¡Has encontrado la palabra $palabraFormada!")
                                                palabrasEncontradas =
                                                    palabrasEncontradas + (palabraFormada to letrasSeleccionadas)
                                                letrasSeleccionadas = listOf()
                                            }
                                        },
                                    color = when {
                                        esParteDePalabraEncontrada && !estaSeleccionada -> Color.Green
                                        estaSeleccionada -> Color.Red
                                        else -> Color.Black
                                    },
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.robotomonobold)),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = if (letra in listOf('Ɨ')) {
                                            23.sp
                                        } else if (letra in listOf('Ŋ')) {
                                            19.sp
                                        } else {
                                            20.sp
                                        },
                                        textAlign = TextAlign.Center,
                                        letterSpacing = if (letra in listOf('Ɨ')) {
                                            0.06.em
                                        } else {
                                            0.em // No se aplica espaciado adicional para otras letras
                                        }
                                    )
                                )
                            }
                        }
                    }

                // Si todas las palabras han sido encontradas, muestra un mensaje de felicitaciones y un botón para reiniciar el juego
                if (palabrasEncontradas.keys.containsAll(palabrasABuscar.values)) {
                    item {
                        Text(
                            "¡Felicidades, has encontrado todas las palabras!",
                            modifier = Modifier.padding(4.dp),
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF00008B)
                            )
                        )
                    }
                    item {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Button(onClick = {
                                letrasSeleccionadas = listOf()
                                palabrasEncontradas = mapOf()
                                sopaDeLetras = generarSopaDeLetras(palabrasABuscar.values.toList())
                            }) {
                                Text("Reiniciar juego")
                            }
                        }
                    }
                }
                }
            }
        }
    )
}

fun generarSopaDeLetras(palabrasABuscar: List<String>): List<List<Char>> {
    val numFilas = 11 // Número de filas
    val numColumnas = 13 // Número de columnas
    val sopaDeLetras = MutableList(numFilas) { MutableList(numColumnas) { ' ' } }
    palabrasABuscar.forEach { palabra ->
        var colocada = false
        while (!colocada) {
            val filaInicio = Random.nextInt(sopaDeLetras.size - palabra.length)
            val columnaInicio = Random.nextInt(sopaDeLetras[0].size - palabra.length)
            val direccion = Random.nextInt(4)
            if (puedeColocarPalabra(
                    sopaDeLetras,
                    palabra,
                    filaInicio,
                    columnaInicio,
                    direccion
                )
            ) {
                palabra.forEachIndexed { indice, letra ->
                    when (direccion) {
                        0 -> sopaDeLetras[filaInicio][columnaInicio + indice] =
                            letra // Horizontal
                        1 -> sopaDeLetras[filaInicio + indice][columnaInicio] =
                            letra // Vertical
                        2 -> sopaDeLetras[filaInicio + indice][columnaInicio + indice] =
                            letra // Diagonal
                        3 -> sopaDeLetras[filaInicio + indice][columnaInicio - indice] =
                            letra // Diagonal izquierda
                    }
                }
                colocada = true
            }
        }
    }
    sopaDeLetras.forEachIndexed { filaIndice, fila ->
        fila.forEachIndexed { columnaIndice, letra ->
            if (letra == ' ') {
                sopaDeLetras[filaIndice][columnaIndice] = ('A'..'Z').plus(listOf('Ɨ', '’','Ñ', 'Ŋ')).random()
            }
        }
    }
    return sopaDeLetras
}

fun puedeColocarPalabra(sopaDeLetras: List<List<Char>>, palabra: String, filaInicio: Int, columnaInicio: Int, direccion: Int): Boolean {
    return palabra.indices.all { indice ->
        when (direccion) {
            0 -> columnaInicio + indice in sopaDeLetras[filaInicio].indices && sopaDeLetras[filaInicio][columnaInicio + indice] == ' ' // Horizontal
            1 -> filaInicio + indice in sopaDeLetras.indices && sopaDeLetras[filaInicio + indice][columnaInicio] == ' ' // Vertical
            2 -> filaInicio + indice in sopaDeLetras.indices && columnaInicio + indice in sopaDeLetras[filaInicio].indices && sopaDeLetras[filaInicio + indice][columnaInicio + indice] == ' ' // Diagonal
            3 -> filaInicio + indice in sopaDeLetras.indices && columnaInicio - indice in sopaDeLetras[filaInicio].indices && sopaDeLetras[filaInicio + indice][columnaInicio - indice] == ' ' // Diagonal izquierda
            else -> false
        }
    }
}

fun esAdyacente(a: Pair<Int, Int>, b: Pair<Int, Int>): Boolean {
    return abs(a.first - b.first) <= 1 && abs(a.second - b.second) <= 1
}

fun esMismaDireccion(letrasSeleccionadas: List<Pair<Int, Int>>, nuevaLetra: Pair<Int, Int>): Boolean {
    val dx = letrasSeleccionadas[1].first - letrasSeleccionadas[0].first
    val dy = letrasSeleccionadas[1].second - letrasSeleccionadas[0].second
    return nuevaLetra.first - letrasSeleccionadas.last().first == dx && nuevaLetra.second - letrasSeleccionadas.last().second == dy
}
