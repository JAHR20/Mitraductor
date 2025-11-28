package com.itsa.mitraductor.cuerpo

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.itsa.mitraductor.R
import com.itsa.mitraductor.app.ToolbarWithBackButton
import com.itsa.mitraductor.app.playAudio
import com.itsa.mitraductor.traductorscreems.quitarAcentos
import com.itsa.mitraductor.ui.theme.MitraductorTheme
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedBoxWithConstraintsScope")
@Composable
fun Cuerpo(navController: NavController, estado : String, region : String) {
    val context = LocalContext.current
    val regionlowercase= region.lowercase(Locale.getDefault())
    val (dialogMessage, setDialogMessage) = remember { mutableStateOf<String?>(null) }
    val state = when(estado){
        "Puebla" -> "pueblanahuatl"
        "Oaxaca"-> "oaxacamixe"
        "Veracruz" -> "veracruzpopoluca"
        else -> {""}
    }
    val regionformat = "region_" + quitarAcentos(region.lowercase().replace(" ", "_"))

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Partes del cuerpo",
                navController = navController,
            )
        },
        content = {paddingValues ->

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                val imageWidth = constraints.maxWidth
                val imageHeight = constraints.maxHeight

                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(top = 30.dp, bottom = 5.dp)
                        .size(imageWidth.toDp(), imageHeight.toDp())
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cuerpo),
                        contentDescription = "Cuerpo Humano",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize()
                    )

                    // Lista de botones con sus proporciones
                    val buttons = listOf(
                        Triple(0.24f, 0.09f, "${state}/${regionformat}/Cabeza.mp3" to "Cabeza"),
                        Triple(0.72f, 0.09f, "${state}/${regionformat}/Ojo.mp3" to "Ojo"),
                        Triple(0.84f, 0.25f, "${state}/${regionformat}/Nariz.mp3" to "Nariz"),
                        Triple(0.12f, 0.25f, "${state}/${regionformat}/Oreja.mp3" to "Oreja"),
                        Triple(0.87f, 0.47f, "${state}/${regionformat}/Mano.mp3" to "Mano"),
                        Triple(0.09f, 0.47f, "${state}/${regionformat}/Boca.mp3" to "Boca"),
                        Triple(0.24f, 0.87f, "${state}/${regionformat}/Rodilla.mp3" to "Rodilla"),
                        Triple(0.72f, 0.87f, "${state}/${regionformat}/Pie.mp3" to "Pie"),
                        Triple(0.84f, 0.68f, "${state}/${regionformat}/Pierna.mp3" to "Pierna"),
                        Triple(0.12f, 0.68f, "${state}/${regionformat}/Brazo.mp3" to "Brazo")
                    )

                    // Lista de textos con sus proporciones
                    val texts = listOf(
                        Triple(0.08f, 0.05f,"Cabeza"),
                        Triple(0.86f, 0.05f, "Ojo"),
                        Triple(0.87f, 0.17f, "Nariz"),
                        Triple(0.1f, 0.165f,"Oreja"),
                        Triple(0.87f, 0.385f, "Mano"),
                        Triple(0.09f, 0.38f, "Boca"),
                        Triple(0.24f, 0.78f, "Rodilla"),
                        Triple(0.74f, 0.78f, "Pie"),
                        Triple(0.84f, 0.595f, "Pierna"),
                        Triple(0.12f, 0.59f, "Brazo")
                    )

                    buttons.forEach { (xProportion, yProportion, audioAndText) ->
                        val (audioFileName, messageKey) = audioAndText
                        ProportionalButton(
                            modifier = Modifier.size(60.dp),
                            xProportion = xProportion,
                            yProportion = yProportion,
                            audioFileName = audioFileName,
                            context = context,
                            imageWidth = imageWidth,
                            imageHeight = imageHeight,
                            setDialogMessage = setDialogMessage,
                            messageKey = messageKey,
                            region = regionlowercase
                        )
                    }
                    // Añadir textos
                    texts.forEach { (xProportion, yProportion, text) ->
                        ProportionalText(
                            modifier = Modifier.size(60.dp),
                            xProportion = xProportion,
                            yProportion = yProportion,
                            text = text,
                            imageWidth = imageWidth,
                            imageHeight = imageHeight
                        )
                    }

                    // Mostrar el diálogo si hay un mensaje
                    dialogMessage?.let { message ->
                        AlertDialog(
                            onDismissRequest = { setDialogMessage(null) },
                            title = { Text(text = "Traducción") },
                            text = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = message,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 25.sp,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = { setDialogMessage(null) }
                                ) {
                                    Text("OK", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ProportionalButton(
    modifier: Modifier,
    xProportion: Float,
    yProportion: Float,
    audioFileName: String,
    context: Context,
    imageWidth: Int,
    imageHeight: Int,
    setDialogMessage: (String?) -> Unit,
    messageKey: String,
    region: String
) {
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .offset {
                val offsetX = (xProportion * imageWidth).toInt()
                val offsetY = (yProportion * imageHeight).toInt()
                IntOffset(
                    x = with(density) { (offsetX.toDp() - 25.dp).toPx() }.toInt(),
                    y = with(density) { (offsetY.toDp() - 25.dp).toPx() }.toInt()
                )
            }
    ) {
        Button(
            onClick = {
                playAudio(audioFileName, context)
                val message = regionMessages[region.lowercase(Locale.getDefault())]?.get(messageKey)
                setDialogMessage(message)
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            shape = CircleShape,
            modifier = modifier
        ) {}
    }
}

@Composable
fun ProportionalText(
    modifier: Modifier,
    xProportion: Float,
    yProportion: Float,
    text: String,
    imageWidth: Int,
    imageHeight: Int
) {
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .offset {
                val offsetX = (xProportion * imageWidth).toInt()
                val offsetY = (yProportion * imageHeight).toInt()
                IntOffset(
                    x = with(density) { (offsetX.toDp() - 25.dp).toPx() }.toInt(),
                    y = with(density) { (offsetY.toDp() - 25.dp).toPx() }.toInt()
                )
            }
    ) {
        Text(text = text, modifier = modifier, fontWeight = FontWeight.Bold)
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MitraductorTheme {
        // Simular un NavController
        val navController = rememberNavController()
        // Proporcionar un valor de región para la vista previa
        Cuerpo(navController = navController, estado = "Veracruz", region = "Soteapan")
    }
}

fun Int.toDp(): Dp {
    return (this / Resources.getSystem().displayMetrics.density).dp
}
