package com.itsa.mitraductor.crucigrama

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

