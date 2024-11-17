package com.itsa.mitraductor.traductorscreems

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsa.mitraductor.R
import com.itsa.mitraductor.app.CustomKeyboard
import com.itsa.mitraductor.app.KeyboardVisibilityObserver
import com.itsa.mitraductor.app.ToolbarWithBackButton
import java.io.InputStream


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignTranslateContainer(navController: NavController) {
    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Traductor de señas",
                navController = navController,
            )
        },
    ) { paddingValues ->
        // Pasar el padding del scaffold al SignTranslateScreen
        SignTranslateScreen(
            modifier = Modifier.padding(paddingValues),
            onValueChanged = { newText -> println("Texto ingresado: $newText") },
            onEditingChanged = { isEditing -> println("¿Está editando? $isEditing") }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignTranslateScreen(
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    onEditingChanged: (Boolean) -> Unit
) {
    var keyboardHeight by remember { mutableStateOf(0) }
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    var isEditing by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val letterToImageMap = mapOf(
        "A" to listOf("imgsenias/a_senias.jpeg"),
        "B" to listOf("imgsenias/b_senias.jpeg"),
        "C" to listOf("imgsenias/c_senias.jpeg"),
        "D" to listOf("imgsenias/d_senias.jpeg"),
        "E" to listOf("imgsenias/e_senias.jpeg"),
        "F" to listOf("imgsenias/f_senias.jpeg"),
        "G" to listOf("imgsenias/g_senias.jpeg"),
        "H" to listOf("imgsenias/h_senias.jpeg"),
        "I" to listOf("imgsenias/i_senias.jpeg"),
        "J" to listOf("imgsenias/j_senias.jpeg"),
        "K" to listOf("imgsenias/k_senias.jpeg"),
        "L" to listOf("imgsenias/l_senias.jpeg"),
        "M" to listOf("imgsenias/m_senias.jpeg"),
        "N" to listOf("imgsenias/n_senias.jpeg"),
        "Ñ" to listOf("imgsenias/enie_senias.jpeg"), // Ejemplo con dos imágenes
        "O" to listOf("imgsenias/o_senias.jpeg"),
        "P" to listOf("imgsenias/p_senias.jpeg"),
        "Q" to listOf("imgsenias/q_senias.jpeg"),
        "R" to listOf("imgsenias/r_senias.jpeg"),
        "S" to listOf("imgsenias/s_senias.jpeg"),
        "T" to listOf("imgsenias/t_senias.jpeg"),
        "U" to listOf("imgsenias/u_senias.jpeg"),
        "V" to listOf("imgsenias/v_senias.jpeg"),
        "W" to listOf("imgsenias/w_senias.jpeg"),
        "X" to listOf("imgsenias/x_senias.jpeg"),
        "Y" to listOf("imgsenias/y_senias.jpeg"),
        "Z" to listOf("imgsenias/z_senias.jpeg"),
        "Ŋ" to listOf("imgsenias/n_senias.jpeg", "imgsenias/g_senias.jpeg"),
        "Ɨ" to listOf("imgsenias/i_senias.jpeg")
    )


    KeyboardVisibilityObserver { isVisible, height ->
        keyboardHeight = if (isVisible) height else 0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = keyboardHeight.dp)
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = textState,
                onValueChange = { newValue ->
                    textState = newValue
                    onValueChanged(newValue.text)
                },
                label = { Text("Ingresar un Texto", fontSize = 22.sp) },
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .onFocusChanged { focusState ->
                        isEditing = focusState.isFocused
                        onEditingChanged(focusState.isFocused)
                    },
                readOnly = true,  // Evita que el teclado del sistema se muestre
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions.Default
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(textState.text.uppercase().toList().flatMap { letter ->
                    letterToImageMap[letter.toString()] ?: listOf()
                }) { imageName ->
                    val imageBitmap = loadImageFromAssets(context, imageName)
                    if (imageBitmap != null) {
                        Image(
                            bitmap = imageBitmap,
                            contentDescription = "Imagen para $imageName",
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }
            }
        }

        if (isEditing) {
            //Spacer(modifier = Modifier.height(8.dp))
            CustomKeyboard(
                onCharClick = { char ->
                    val newText = textState.text + char
                    textState = TextFieldValue(newText)
                    onValueChanged(newText)
                },
                onDeleteClick = {
                    if (textState.text.isNotEmpty()) {
                        val newText = textState.text.dropLast(1)
                        textState = TextFieldValue(newText)
                        onValueChanged(newText)
                    }
                },
                onSpaceClick = {
                    val newText = textState.text + " "
                    textState = TextFieldValue(newText)
                    onValueChanged(newText)
                },
                onHideKeyboard = {
                    isEditing = false
                    focusManager.clearFocus()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(308.dp)
            )
        }
    }
}

// Función para cargar la imagen desde assets
fun loadImageFromAssets(context: android.content.Context, assetName: String): ImageBitmap? {
    return try {
        val inputStream: InputStream = context.assets.open(assetName)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

