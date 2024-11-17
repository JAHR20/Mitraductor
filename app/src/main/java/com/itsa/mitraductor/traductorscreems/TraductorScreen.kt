package com.itsa.mitraductor.traductorscreems

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.itsa.mitraductor.R
import com.itsa.mitraductor.app.ToolbarWithBackButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TraductorScreen(
    estado : String,
    region: String,
    navController: NavController,
    traducciones: Map<String, String>, // Agregar traducciones como parámetro
    context: Context
) {
    var isTextArea1Visible by remember { mutableStateOf(true) }
    var textoOriginal by remember { mutableStateOf("") }
    var textoTraducido by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Traductor para la $region",
                navController = navController,
            )
        },
    ) {paddingValues ->
        ViewContainerContent(
            isTextArea1Visible = isTextArea1Visible,
            textoOriginal = textoOriginal,
            textoTraducido = textoTraducido,
            onTextArea1ValueChanged = { newText -> textoOriginal = newText },
            onTextArea2ValueChanged = { newText -> textoTraducido = newText },
            onBotonCambiarClick = { isTextArea1Visible = !isTextArea1Visible },
            onBotonTraducirClick = { traduccion -> textoTraducido = traduccion },
            onTraduccionReady = {},
            traducciones = traducciones, // Pasar traducciones como argumento
            regionSeleccionada = region, // Pasar la región seleccionada como argumento
            estado = estado,
            context = context, // Pasar context como argumento
            paddingValues = paddingValues
        )
    }
}