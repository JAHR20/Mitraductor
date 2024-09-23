package com.itsa.mitraductor.traductorscreems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsa.mitraductor.app.CustomKeyboard

@Composable
fun TextArea(
    textoOriginal: String,
    onValueChanged: (String) -> Unit,
    onEditingChanged: (Boolean) -> Unit
) {
    var description by remember(textoOriginal) {
        mutableStateOf(textoOriginal)
    }

    TextField(
        value = description,
        onValueChange = {
            description = it
            onValueChanged(it)
        },
        label = { Text("Ingresar Texto en Español", fontSize = 20.sp) },
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .onFocusChanged { focusState ->
                onEditingChanged(focusState.isFocused)
            },
        readOnly = true,
        // Se usa un teclado personalizado
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions.Default
    )
}

@Composable
fun TextAreaExample2(
    textoTraducido: String,
    onValueChanged: (String) -> Unit,
    onEditingChanged: (Boolean) -> Unit
) {
    var description by remember(textoTraducido) {
        mutableStateOf(textoTraducido)
    }

    TextField(
        value = description,
        onValueChange = { newDescription ->
            description = newDescription
            onValueChanged(newDescription)
        },
        label = { Text("Ingresar Texto en Lengua Materna", fontSize = 20.sp) },
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .onFocusChanged { focusState ->
                onEditingChanged(focusState.isFocused)
            },
        readOnly = true,
        // Se usa un teclado personalizado
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions.Default
    )
}
