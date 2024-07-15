package com.itsa.mitraductor.traductorscreems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextArea(textoOriginal: String, onValueChanged: (String) -> Unit) {
    var description by remember(textoOriginal) {
        mutableStateOf(textoOriginal)
    }


    TextField(
        value = description,
        onValueChange = { description = it
            onValueChanged(it)},
        label = { Text("Ingresar Texto en Español",fontSize = 20.sp) },
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp) // Agrega un espacio interno para que los bordes redondeados sean visibles
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color.White)
    )
}

@Composable
fun TextAreaExample2(textoTraducido: String, onValueChanged: (String) -> Unit) {
    var description by remember(textoTraducido) {
        mutableStateOf(textoTraducido)
    }

    TextField(
        value = description,
        onValueChange = { newDescription ->
            description = newDescription
            onValueChanged(newDescription) // Agregar esta línea para actualizar textoTraducido
        },
        label = { Text("Ingresar Texto en Popoluca", fontSize = 20.sp) },
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color.White)
    )
}