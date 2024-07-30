package com.itsa.mitraductor.crucigrama

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsa.mitraductor.app.CustomKeyboard
import com.itsa.mitraductor.app.KeyboardVisibilityObserver
import com.itsa.mitraductor.app.ToolbarWithBackButton
import java.util.Locale

// Estructura de datos para una palabra en el crucigrama
data class CrosswordWord(val word: String, val clue: String, val row: Int, val column: Int, val directionPalabra: DirectionPalabra)
enum class DirectionPalabra { HORIZONTAL, VERTICAL }

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
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CrosswordGame(navController: NavController, region: String) {
    val crosswordBoard = remember { CrosswordBoard(size = 12) }
    val focusManager = LocalFocusManager.current

    when (region) {
        "Soteapan" -> loadWordsForSoteapan(crosswordBoard)
        "Sayula" -> loadWordsForSayula(crosswordBoard)
        "Oluta" -> loadWordsForOluta(crosswordBoard)
        "Texistepec" -> loadWordsForTexistepec(crosswordBoard)
        "San Gabriel Chilac" -> loadWordsForNorte(crosswordBoard)
        "Ocotepec" -> loadWordsForIstmo(crosswordBoard)
        // Agrega más casos para otras regiones aquí si es necesario
    }

    var specialLetter by remember { mutableStateOf<Char?>(null) }
    val clues = remember { calculateClues(crosswordBoard.words) } // Calcular las pistas una vez
    var enteredWord by remember { mutableStateOf("") }
    enteredWord = enteredWord.lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    var message by remember { mutableStateOf("") }

    // Comprobar si todas las palabras están completadas
    val allWordsCompleted = crosswordBoard.words.all { word ->
        word.word.all { char ->
            crosswordBoard.correctWordPositions.value.contains(word.row to word.column) || char == ' '
        }
    }

    if (allWordsCompleted) {
        message = "¡Felicidades, ganaste!"
    }

    var isEditing by remember { mutableStateOf(false) }
    var keyboardHeight by remember { mutableStateOf(0) }

    // Observer para la visibilidad del teclado y la altura
    KeyboardVisibilityObserver { isVisible, height ->
        keyboardHeight = if (isVisible) height else 0
    }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Crucigrama",
                navController = navController // Pasa el NavController al composable del botón de retroceso
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = keyboardHeight.dp) // Ajustar el padding inferior según la altura del teclado
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        CrosswordBoardView(
                            crosswordBoard = crosswordBoard,
                            clues = clues, // Pasar las pistas como parámetro
                            enteredWord = enteredWord,
                            onCheck = {
                                if (enteredWord.isNotBlank()) {
                                    if (crosswordBoard.checkWord(enteredWord.trim())) {
                                        message = "Correcto!"
                                        enteredWord = "" // Limpiar la palabra ingresada después de verificarla
                                    } else {
                                        message = "Incorrecto! Inténtalo de nuevo."
                                    }
                                }
                            },
                            onSpecialLetterClick = { specialLetter = it }
                        )

                        // Actualizar TextField con el valor de enteredWord y specialLetter
                        LaunchedEffect(enteredWord, specialLetter) {
                            specialLetter?.let {
                                enteredWord += it
                                specialLetter = null // Restablecer la letra especial después de agregarla al TextField
                            }
                        }

                        TextField(
                            value = enteredWord,
                            onValueChange = { enteredWord = it },
                            label = { Text("Ingresa una palabra", fontSize = 18.sp) },
                            //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            modifier = Modifier
                                .onFocusChanged { focusState ->
                                    isEditing = focusState.isFocused
                                },
                            readOnly = true,
                            // Se usa un teclado personalizado
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                            keyboardActions = KeyboardActions.Default
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        MessageWithFormat(message = message)

                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }

                // Mostrar el teclado personalizado si el TextField está en foco
                if (isEditing) {
                    CustomKeyboard(
                        onCharClick = { char ->
                            enteredWord += char
                        },
                        onDeleteClick = {
                            if (enteredWord.isNotEmpty()) {
                                enteredWord = enteredWord.dropLast(1)
                            }
                        },
                        onSpaceClick = {
                            enteredWord += " "
                        },
                        onHideKeyboard = {
                            isEditing = false
                            focusManager.clearFocus() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(310.dp) // Ajusta la altura del teclado según el diseño
                    )
                }
            }
        }
    )
}


// Función para cargar las palabras para la región de Soteapan
fun loadWordsForSoteapan(crosswordBoard: CrosswordBoard) {
    crosswordBoard.addWord("Chiipiñ", "Vegetal de piel roja o amarilla y pulpa jugosa, utilizado tanto en platos salados como en ensaladas.\n", 1, 0,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Pichkuy", "Fruta cítrica de pulpa ácida y jugosa, ampliamente utilizada en la cocina y en la elaboración de bebidas.\n", 7, 4,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Uuju’", "Fruta tropical de sabor dulce y jugoso, con una corona de hojas puntiagudas en la parte superior. \n", 9, 0,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Xikma", "Raíz comestible de sabor dulce y textura crujiente, se consume comúnmente cruda en ensaladas o como snack. \n", 4, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Ñiiwi", "Fruto picante ampliamente utilizado en la cocina de muchas culturas. \n", 0, 2,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Axux", "Vegetal que se presenta en forma de bulbo compuesto por varios dientes. \n", 7, 1,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Samñi", "Rico en potasio y fibra, es una opción popular para añadir a los batidos y postres. \n", 3, 5,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Kuytyɨm", "Ingrediente principal en la preparación de guacamole. \n", 3, 10,
        DirectionPalabra.VERTICAL
    )

}

// Función para cargar las palabras para la región de Sayula
fun loadWordsForSayula(crosswordBoard: CrosswordBoard) {
    crosswordBoard.addWord("Ma’akx", "Prenda de vestir que cubre el cuerpo desde los hombros hasta la parte inferior.\n", 1, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Akx", "Animal acuático vertebrado que se captura para consumo humano.\n", 10, 7,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Jojn", "Muchas especies construyen nidos para poner huevos y criar a sus crías.\n", 4, 7,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Ajch", "En términos familiares, se refiere al hermano del padre o de la madre de una persona.\n", 8, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Uux", "Conocido por su zumbido característico y por su picadura molesta.\n", 8, 9,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Maajts", "Objeto celestial que emite luz propia debido a la fusión nuclear en su núcleo.\n", 0, 2,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Ajw", "Parte del cuerpo humano donde se encuentran los labios y los dientes, utilizada para comer, hablar y respirar.\n", 8, 1,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Ajy", "En términos legales y familiares, se refiere a la descendencia femenina de uno o ambos padres.\n", 3, 9,
        DirectionPalabra.VERTICAL
    )
    // Agrega más palabras para la región de Sayula aquí si es necesario
}

fun loadWordsForOluta(crosswordBoard: CrosswordBoard) {
    crosswordBoard.addWord("Piseru", "Cría de la vaca, generalmente menor de un año, conocida por su piel suave y su rápido crecimiento..\n", 1, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Eshi", "Crustáceo de cuerpo ancho y plano, con un caparazón duro, diez patas y un par de pinzas delanteras.\n", 9, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Tutsu", " Árbol tropical, de tronco alto y esbelto, con hojas grandes, en forma de abanico o plumas.\n", 10, 6,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Tuju", "Precipitación de gotas de agua que caen de las nubes debido a la condensación del vapor de agua en la atmósfera. \n", 7, 5,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Piscu", "Fruta cítrica redonda y jugosa, de piel anaranjada y pulpa segmentada, conocida por su sabor dulce y ligeramente ácido.\n", 1, 1,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Acsi", "Ave de plumaje negro brillante, conocida también como tordo, perteneciente a la familia de los ictéridos. \n", 7, 2,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Poju", "Mamífero de tamaño mediano, con hocico puntiagudo, orejas erectas y una cola larga, es conocido por su astucia .\n", 4, 8,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Jaytsu", "Mamífero herbívoro de tamaño mediano, con patas largas y delgadas, y una cornamenta ramificada en los machos.\n", 5, 10,
        DirectionPalabra.VERTICAL
    )
    // Agrega más palabras para la región de Sayula aquí si es necesario
}

fun loadWordsForTexistepec(crosswordBoard: CrosswordBoard) {
    crosswordBoard.addWord("Kučim", "Fruto verde y cremoso con una semilla grande en su interior.\n", 1, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Ki:p", "Trozos de madera utilizados como combustible para hacer fuego.\n", 3, 6,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Puh’", " Unidad reproductiva de una planta que puede germinar y dar lugar a una nueva planta.\n", 8, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Kalbas", " Vegetal de forma redonda, de color naranja o verde, con una pulpa comestible y semillas en su interior. \n", 10, 5,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Koweñ", "Arte que consiste en el movimiento rítmico del cuerpo, a menudo realizado al ritmo de la música.\n", 1, 1,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Pu:k", "Fibra natural cosechada una planta, utilizada ampliamente en la industria textil \n", 7, 2,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Ka:n", "Majestuoso felino de gran tamaño, rayado y de hábitos solitarios, nativo de Asia y conocido por su fuerza.\n", 1, 8,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Ra?y", "Ave zancuda de cuello largo y plumaje blanco, comúnmente encontrada cerca de cuerpos de agua, como lagos y ríos.\n", 5, 6,
        DirectionPalabra.VERTICAL
    )
    // Agrega más palabras para la región de Sayula aquí si es necesario
}

fun loadWordsForNorte(crosswordBoard: CrosswordBoard) {
    crosswordBoard.addWord("Nochtli", "Fruta ovalada de la planta nopal, con una piel espinosa y pulpa dulce, común en regiones desérticas.\n", 1, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Tescatl", "Superficie que refleja la luz, permitiendo ver en ella imágenes de objetos y personas situados frente a ella.\n", 5, 4,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Setl", "Estado sólido del agua producido por temperaturas bajo cero, comúnmente utilizado para enfriar.\n", 10, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Chichi", "Animal doméstico de cuatro patas, conocido por su lealtad, considerado el mejor amigo del hombre. \n", 10, 6,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Coscatl", "Accesorio que se lleva alrededor del cuello, usado tanto como adorno en joyería como para identificar animales domésticos.\n", 0, 2,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Nacatl", "Tejido muscular de animales, utilizado como alimento en la dieta humana. \n", 0, 10,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Yetl", "Semilla comestible de la planta leguminosa, de forma ovalada o redonda, con variedades que incluyen negro, rojo, blanco y pinto.\n", 7, 4,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Tomin", "Medio de intercambio y unidad de cuenta aceptado por una sociedad para el pago de bienes, servicios y deudas.\n", 7, 8,
        DirectionPalabra.VERTICAL
    )
    // Agrega más palabras para la región de Sayula aquí si es necesario
}

fun loadWordsForIstmo(crosswordBoard: CrosswordBoard) {
    crosswordBoard.addWord("Nëëts", "Mamífero de pequeño tamaño con que se caracteriza por su caparazón protector y su capacidad de enrollarse en una bola .\n", 1, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Meexë", "Mueble con una superficie plana y elevada, generalmente sostenida por patas, utilizado para colocar comer o trabajar. \n", 7, 1,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Konejë", "Mamífero pequeño y peludo de orejas largas y cola corta, conocido por su agilidad y su habilidad para saltar.\n", 10, 5,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Chiit", "Felino doméstico de tamaño pequeño a mediano, de pelaje suave y ojos brillantes, conocido por su agilidad de caza. \n", 3, 6,
        DirectionPalabra.HORIZONTAL
    )
    crosswordBoard.addWord("Nëëmk", "Ave acuática de plumaje variado, caracterizada por su pico aplanado y patas palmeadas, adaptada para nadar.\n", 0, 2,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Mok", "Planta de la familia de las gramíneas, de tallo alto y grandes mazorcas cubiertas de granos amarillos, blancos o de otros colores. \n", 7, 1,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Wiin", "Órgano sensorial que detecta la luz y permite la visión en los seres humanos y muchos animales. \n", 2, 8,
        DirectionPalabra.VERTICAL
    )
    crosswordBoard.addWord("Wiistë", "Ave rapaz de gran tamaño, con alas anchas y afiladas garras, conocida por su aguda visión y poderoso vuelo.\n", 5, 10,
        DirectionPalabra.VERTICAL
    )
    // Agrega más palabras para la región de Sayula aquí si es necesario
}


// Función para calcular las pistas del crucigrama
fun calculateClues(words: List<CrosswordWord>): Pair<List<String>, List<String>> {
    val horizontalClues = words.filter { it.directionPalabra == DirectionPalabra.HORIZONTAL }.map { it.clue }
    val verticalClues = words.filter { it.directionPalabra == DirectionPalabra.VERTICAL }.map { it.clue }
    return Pair(horizontalClues, verticalClues)
}


@Composable
fun CrosswordBoardView(
    crosswordBoard: CrosswordBoard,
    clues: Pair<List<String>, List<String>>, // Agregar pistas como parámetro
    enteredWord: String,
    onCheck: () -> Unit,
    onSpecialLetterClick: (Char) -> Unit
) {
    Column {
        // Mostrar pistas en un cuadro en la parte superior del tablero
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(color = Color.LightGray)
                .padding(8.dp)
        ) {
            Row {
                // Columna para pistas horizontales
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Horizontales",
                        modifier = Modifier.padding(bottom = 4.dp),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    clues.first.forEachIndexed { index, clue ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${index + 1}. ",
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            Text(
                                text = clue,
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
                // Espacio entre las dos columnas
                Spacer(modifier = Modifier.width(16.dp))
                // Columna para pistas verticales
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Verticales",
                        modifier = Modifier.padding(bottom = 4.dp),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    clues.second.forEachIndexed { index, clue ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${index + 1 + clues.first.size}. ",
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            Text(
                                text = clue,
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Mostrar el tablero del crucigrama
        for (i in crosswordBoard.displayBoard.value.indices) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center)
            {
                for (j in crosswordBoard.displayBoard.value[i].indices) {
                    val cell = crosswordBoard.displayBoard.value[i][j]
                    val isCellEmpty = cell == ' '

                    // Obtener el número de pista para la celda actual (si es el inicio de una palabra)
                    val clueNumber = getClueNumber(i, j, crosswordBoard.words)

                    Box(
                        modifier = Modifier
                            .padding(1.dp)
                            .size(24.5.dp)
                            .background(color = if (isCellEmpty) Color.Black else Color.White)
                    ) {
                        val textColor = if (isCellEmpty) Color.Black else if (crosswordBoard.isCorrectCell(i, j)) Color.Black else Color.White
                        Text(
                            text = if (isCellEmpty) "#" else cell.toString(),
                            modifier = Modifier.align(Alignment.Center),
                            color = textColor
                        )

                        // Mostrar el número de pista si es el inicio de una palabra
                        if (clueNumber != null) {
                            Text(
                                text = clueNumber.toString(),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(4.dp),
                                color = Color.Gray,
                                fontSize = 12.sp // Tamaño de fuente más pequeño para los números de pista
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onCheck,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Verificar")
        }
    }
}


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


@Composable
fun MessageWithFormat(message: String) {
    Text(
        fontSize = 20.sp,
        text = message,
        style = MaterialTheme.typography.titleSmall,
        color = Color(0xFF1F54AF)
    )
}


