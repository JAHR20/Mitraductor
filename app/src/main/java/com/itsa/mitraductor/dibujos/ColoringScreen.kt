package com.itsa.mitraductor.dibujos

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.itsa.mitraductor.R
import com.itsa.mitraductor.app.ToolbarWithBackButton
import java.io.IOException

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ColoringScreen(navController: NavController, region: String) {
    val lines = remember { mutableStateListOf<Line>() }
    var currentColor by remember { mutableStateOf(Color.Black) }
    var isEraserMode by remember { mutableStateOf(false) }
    val GoldColor = Color(0xFFFFD600)
    var selectedButton by remember { mutableStateOf(Color.Black) }
    val context = LocalContext.current
    val region = region.lowercase()
    val Cafe = Color(0xF3663529)
    val Rosa = Color(0xF3E245A9)
    val Morado = Color(0xF37F24B8)

    // Lista de identificadores de recursos de tus vectores drawable
    val vectorList = listOf(
        Pair(R.drawable.florcolorear, "Flor"),
        Pair(R.drawable.bananas_lineart, "Platanos"),
        Pair(R.drawable.gerald_g_simple_fruit__ff_menu__12, "Naranja"),
        Pair(R.drawable.gerald_g_simple_fruit__ff_menu__13, "Manzana"),
        Pair(R.drawable.gerald_g_simple_fruit__ff_menu__9, "Fresa"),
        Pair(R.drawable.gerald_g_soft_ice_cream_cones__ff_menu__1, "Nieve"),
        Pair(R.drawable.house_line_art_barretr_house, "Casa"),
        Pair(R.drawable.warszawianka_jumping_horse_outline, "Caballo"),
        Pair(R.drawable.johnny_automatic_young_bear, "Oso"),
        Pair(R.drawable.mujer_dibujo, "Mujer")
    )

    var showDialog by remember { mutableStateOf(false) }
    var showDialoglimpiar by remember { mutableStateOf(false) }
    var selectedDrawing by remember { mutableStateOf(vectorList[0]) }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Colorear",
                navController = navController
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(58.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .wrapContentHeight()
                ) {
                    Column {
                        Text(
                            text = "Colores",
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ColorButton(Color.Black, GoldColor, selectedButton) {
                                currentColor = Color.Black
                                isEraserMode = false
                                selectedButton = Color.Black
                                playAudio("palabra_Negro_region${region}.mp3", context)
                            }
                            ColorButton(Color.Red, GoldColor, selectedButton) {
                                currentColor = Color.Red
                                isEraserMode = false
                                selectedButton = Color.Red
                                playAudio("palabra_Rojo_region${region}.mp3", context)
                            }
                            ColorButton(Color.Green, GoldColor, selectedButton) {
                                currentColor = Color.Green
                                isEraserMode = false
                                selectedButton = Color.Green
                                playAudio("palabra_Verde_region${region}.mp3", context)
                            }
                            ColorButton(Color.Blue, GoldColor, selectedButton) {
                                currentColor = Color.Blue
                                isEraserMode = false
                                selectedButton = Color.Blue
                                playAudio("palabra_Azul_region${region}.mp3", context)
                            }
                            Button(
                                colors = ButtonDefaults.buttonColors(Color.Gray),
                                onClick = {
                                    isEraserMode = true
                                    selectedButton = Color.Gray
                                },
                                modifier = Modifier
                                    .padding(3.dp)
                                    .width(80.dp)
                                    .border(
                                        width = if (isEraserMode && selectedButton == Color.Gray) 2.dp else 0.dp,
                                        color = GoldColor,
                                        shape = CircleShape
                                    )
                            ) {
                                Text(
                                    text = "Borrar",
                                    fontSize = 9.sp,
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ColorButton(Color.Yellow, GoldColor, selectedButton) {
                                currentColor = Color.Yellow
                                isEraserMode = false
                                selectedButton = Color.Yellow
                                playAudio("palabra_Amarillo_region${region}.mp3", context)
                            }
                            ColorButton(Cafe, GoldColor, selectedButton) {
                                currentColor = Cafe
                                isEraserMode = false
                                selectedButton = Cafe
                                playAudio("palabra_Cafe_region${region}.mp3", context)
                            }
                            ColorButton(Rosa, GoldColor, selectedButton) {
                                currentColor = Rosa
                                isEraserMode = false
                                selectedButton = Rosa
                                playAudio("palabra_Rosa_region${region}.mp3", context)
                            }
                            ColorButton(Color.LightGray, GoldColor, selectedButton) {
                                currentColor = Color.LightGray
                                isEraserMode = false
                                selectedButton = Color.LightGray
                                playAudio("palabra_Gris_region${region}.mp3", context)
                            }
                            ColorButton(Morado, GoldColor, selectedButton) {
                                currentColor = Morado
                                isEraserMode = false
                                selectedButton = Morado
                                playAudio("palabra_Morado_region${region}.mp3", context)
                            }
                            Button(
                                colors = ButtonDefaults.buttonColors(Color.DarkGray),
                                onClick = {
                                    showDialoglimpiar = true
                                },
                                modifier = Modifier
                                    .padding(3.dp)
                                    .width(80.dp)
                                    .border(
                                        width = if (selectedButton == Color.DarkGray) 2.dp else 0.dp,
                                        color = GoldColor,
                                        shape = CircleShape
                                    )
                            ) {
                                Text(
                                    text = "Limpiar",
                                    fontSize = 9.sp,
                                )
                            }
                        }

                        // DropdownMenu para seleccionar dibujos
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)) {
                            Button(onClick = { showDialog = true }) {
                                Text(text = "Seleccionar Dibujo")
                            }

                            if (showDialoglimpiar) {
                                AlertDialog(
                                    onDismissRequest = { showDialoglimpiar = false },
                                    title = { Text(text = "Confirmación", color=Color.Black, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold) },
                                    text = {
                                        Text(text = "¿Desea Borrar lo coloreado?", color=Color.Black, fontSize = 17.sp)
                                    },
                                    confirmButton = {
                                        Button(
                                            onClick = {
                                                lines.clear()
                                                showDialoglimpiar = false }
                                        ) {
                                            Text("Si")
                                        }
                                    },
                                    dismissButton = {
                                        Button(
                                            onClick = { showDialoglimpiar = false }
                                        ){
                                            Text("No")
                                        }
                                    }
                                )
                            }

                            // Código para AlertDialog
                            if (showDialog) {
                                AlertDialog(
                                    onDismissRequest = { showDialog = false },
                                    title = { Text(text = "Seleccionar Dibujo", color=Color.Black) },
                                    text = {
                                        Column {
                                            vectorList.forEach { vector ->
                                                TextButton(onClick = {
                                                    selectedDrawing = vector
                                                    showDialog = false
                                                }) {
                                                    Text(text = vector.second, color=Color.Black)
                                                }
                                            }
                                        }
                                    },
                                    confirmButton = {
                                        Button(
                                            onClick = { showDialog = false }
                                        ) {
                                            Text("Cerrar")
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                // Canvas para dibujar
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(4.dp)
                        .background(Color.White)
                ) {
                    // SVG Image
                    val image = painterResource(id = selectedDrawing.first)
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()

                                    val start = change.position - dragAmount
                                    val end = change.position

                                    if (isEraserMode) {
                                        val linesToRemove = lines.filter { line ->
                                            line.isNear(start) || line.isNear(end)
                                        }
                                        lines.removeAll(linesToRemove)
                                    } else {
                                        val line = Line(
                                            start = start,
                                            end = end,
                                            color = currentColor
                                        )
                                        lines.add(line)
                                    }
                                }
                            }
                    ) {
                        lines.forEach { line ->
                            drawLine(
                                color = line.color,
                                start = line.start,
                                end = line.end,
                                strokeWidth = line.strokeWidth,
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ColorButton(color: Color, borderColor: Color, selectedButton: Color, onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(color),
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .width(40.dp)
            .border(
                width = if (selectedButton == color) 2.dp else 0.dp,
                color = borderColor,
                shape = CircleShape
            )
    ) {}
}

private fun Line.isNear(position: Offset, threshold: Float = 20f): Boolean {
    return (start - position).getDistance() < threshold || (end - position).getDistance() < threshold
}

fun playAudio(audioFileName: String, context: Context) {
    val _audioToPlay = MutableLiveData<String>()
    _audioToPlay

    var mediaPlayer: MediaPlayer? = null
    try {
        mediaPlayer?.release()
        val assetFileDescriptor = context.assets.openFd("audios/$audioFileName")
        MediaPlayer().apply {
            setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
            prepare()
            start()
        }
        _audioToPlay.value = audioFileName
    } catch (e: IOException) {
        e.printStackTrace()
    }
}





