package com.itsa.mitraductor.app

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsa.mitraductor.R

@Composable
fun CustomKeyboard(
    onCharClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onSpaceClick: () -> Unit,
    onHideKeyboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val teclado = Color(0xFFFFFFFF)
    var isUpperCase by remember { mutableStateOf(false) }
    var isNumberMode by remember { mutableStateOf(false) }
    var useAccents by remember { mutableStateOf(false) }
    var usePunctuation by remember { mutableStateOf(false) }

    val letters = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")
    val numbers = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
    val punctuation = listOf(".", ",", "?", "!", ";", ":", "-", "_", "¿", "¡")
    val specialChars = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l", "ñ", "z", "x", "c", "v", "b", "n", "m", "ɨ", "ŋ", "’", "ʉ", "s̈", "a̲", "e̲", "i̲", "o̲", "u̲", "ë", "ꞌ")

    // Función para mapear las vocales a sus versiones con acento
    fun mapChars(chars: List<String>, useAccents: Boolean): List<String> {
        return chars.map {
            when (it) {
                "a" -> if (useAccents) "á" else "a"
                "e" -> if (useAccents) "é" else "e"
                "i" -> if (useAccents) "í" else "i"
                "o" -> if (useAccents) "ó" else "o"
                "u" -> if (useAccents) "ú" else "u"
                else -> it
            }
        }
    }

    val lettersRow = when {
        isNumberMode -> numbers
        usePunctuation -> punctuation
        else -> mapChars(letters, useAccents)
    }
    val specialCharsRow = mapChars(specialChars, useAccents)

    Column(modifier = modifier.background(teclado).padding(5.dp)) {
        val numRows = (specialCharsRow.size + 9) / 10
        val totalChars = specialCharsRow.size
        val maxCharsInRow = 10
        val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
        val keyWidth = (screenWidthDp / maxCharsInRow) - 2.dp // Ajuste para el padding
        val keyHeight = 60.dp // Altura fija para los botones

        // Primera fila
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            lettersRow.forEach { char ->
                Key(if (isUpperCase) char.uppercase() else char, keyWidth, keyHeight) { onCharClick(if (isUpperCase) char.uppercase() else char) }
            }
        }

        for (i in 0 until numRows) {
            val start = i * maxCharsInRow
            val end = minOf(start + maxCharsInRow, totalChars)
            val rowChars = specialCharsRow.subList(start, end)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowChars.forEach { char ->
                    Key(if (isUpperCase) char.uppercase() else char, keyWidth, keyHeight) { onCharClick(if (isUpperCase) char.uppercase() else char) }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Key(if (isUpperCase) "⇧" else "⇩", keyWidth, keyHeight) { isUpperCase = !isUpperCase }
            Key("123", keyWidth, keyHeight) { if (isNumberMode) {
                isNumberMode = false
                usePunctuation = false
            } else {
                isNumberMode = true
                usePunctuation = false
            } }
            Key("´", keyWidth, keyHeight) { useAccents = !useAccents }
            Key("␣", keyWidth * 3, keyHeight) { onSpaceClick() }
            Key(".,?!", keyWidth, keyHeight) { if (usePunctuation) {
                usePunctuation = false
                isNumberMode = false
            } else {
                usePunctuation = true
                isNumberMode = false
            } }
            Key("⌫", keyWidth, keyHeight, onClick = onDeleteClick)
            Key("⬇", keyWidth, keyHeight, onClick = onHideKeyboard)
        }
    }
}

@Composable
fun Key(char: String, width: Dp, height: Dp, onClick: () -> Unit) {
    val woodTexture: Painter = painterResource(id = R.drawable.madera)

    Card(
        modifier = Modifier
            .width(width)
            .height(height)
            .padding(2.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)  // Ajusta este valor según la elevación que desees
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .clip(shape = MaterialTheme.shapes.small),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = woodTexture,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Text(
                text = char,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}
@Composable
fun KeyboardVisibilityObserver(onKeyboardVisibilityChanged: (Boolean, Int) -> Unit) {
    val context = LocalContext.current
    val rootView = (context as Activity).window.decorView.findViewById<View>(android.R.id.content)

    DisposableEffect(context) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            val isKeyboardVisible = keypadHeight > 0
            onKeyboardVisibilityChanged(isKeyboardVisible, keypadHeight)
        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}

