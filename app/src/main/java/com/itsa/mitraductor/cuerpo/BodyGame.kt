package com.itsa.mitraductor.cuerpo

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.itsa.mitraductor.R
import com.itsa.mitraductor.ToolbarWithBackButton
import com.itsa.mitraductor.ui.theme.MitraductorTheme
import java.io.IOException

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Cuerpo(navController: NavController, region: String) {
    val context = LocalContext.current
    val region=region
    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Colorear",
                navController = navController
            )
        },
        content = {

            BoxWithConstraints(
                modifier = Modifier.fillMaxSize().padding(top = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                val imageWidth = constraints.maxWidth
                val imageHeight = constraints.maxHeight

                Box(
                    modifier = Modifier
                        .size(imageWidth.toDp(), imageHeight.toDp())
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.body),
                        contentDescription = "Cuerpo Humano",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize()
                    )

                    // Lista de botones con sus proporciones
                    val buttons = listOf(
                        Triple(0.51f, 0.08f, "palabra_Cabeza_regionsoteapan.mp3"),
                        Triple(0.31f, 0.31f, "palabra_Brazo_regionsoteapan.mp3"),
                        Triple(0.72f, 0.31f, "palabra_Brazo_regionsoteapan.mp3"),
                        Triple(0.18f, 0.48f, "palabra_Mano_regionsayula.mp3"),
                        Triple(0.83f, 0.48f, "palabra_Mano_regionsayula.mp3"),
                        Triple(0.33f, 0.22f, "palabra_Hombro_regionsoteapan.mp3"),
                        Triple(0.68f, 0.22f, "palabra_Hombro_regionsoteapan.mp3"),
                        Triple(0.43f, 0.9f, "palabra_Pie_regionsoteapan.mp3"),
                        Triple(0.6f, 0.9f, "palabra_Pie_regionsoteapan.mp3"),
                        Triple(0.41f, 0.57f, "palabra_Pierna_regionsoteapan.mp3"),
                        Triple(0.62f, 0.57f, "palabra_Pierna_regionsoteapan.mp3"),
                        Triple(0.41f, 0.7f, "palabra_Rodilla_regionsoteapan.mp3"),
                        Triple(0.61f, 0.7f, "palabra_Rodilla_regionsoteapan.mp3")
                    )

                    buttons.forEach { (xProportion, yProportion, audioFileName) ->
                        ProportionalButton(
                            modifier = Modifier.size(40.dp),
                            xProportion = xProportion,
                            yProportion = yProportion,
                            audioFileName = audioFileName,
                            context = context,
                            imageWidth = imageWidth,
                            imageHeight = imageHeight
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
    imageHeight: Int
) {
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .offset{
                val offsetX = (xProportion * imageWidth).toInt()
                val offsetY = (yProportion * imageHeight).toInt()
                IntOffset(
                    x = with(density) { (offsetX.toDp() - 25.dp).toPx() }.toInt(),
                    y = with(density) { (offsetY.toDp() - 25.dp).toPx() }.toInt()
                )
            }
    ) {
        Button(
            onClick = { playAudio(audioFileName, context) },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            shape = CircleShape,
            modifier = modifier
        ) {}
    }
}

fun playAudio(audioFileName: String, context: Context) {
    var mediaPlayer: MediaPlayer? = null
    try {
        mediaPlayer?.release()
        val assetFileDescriptor = context.assets.openFd("audios/$audioFileName")
        MediaPlayer().apply {
            setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
            prepare()
            start()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MitraductorTheme {
        //Cuerpo(navController, region)
    }
}

fun Int.toDp(): Dp {
    return (this / Resources.getSystem().displayMetrics.density).dp
}
