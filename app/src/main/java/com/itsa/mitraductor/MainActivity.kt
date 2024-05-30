package com.itsa.mitraductor

import MemoramaGameComposable
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color.*
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.itsa.mitraductor.cuerpo.Cuerpo
import com.itsa.mitraductor.dibujos.ColoringScreen
import com.itsa.mitraductor.media.startGameLoop
import com.itsa.mitraductor.ui.theme.CrosswordGame
import com.itsa.mitraductor.ui.theme.FillInTheBlanksGame
import com.itsa.mitraductor.ui.theme.HangmanScreen
import com.itsa.mitraductor.ui.theme.MitraductorTheme
import com.itsa.mitraductor.ui.theme.SopaDeLetras
import com.itsa.mitraductor.ui.theme.TraduccionesData
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.util.Locale
import kotlin.properties.Delegates

var deviceWidthInPixel by Delegates.notNull<Float>()
var deviceDensity by Delegates.notNull<Float>()
class MainActivity : ComponentActivity() {

    val estadosRegionesMap: Map<String, List<String>> = mapOf(
        "Puebla(Nahualt)" to listOf("Region San Gabriel Chilac"),
        "Oaxaca(Mixe)" to listOf("Region Ocotepec"),
        "Veracruz(Popoluca)" to listOf("Region Soteapan", "Region Sayula", "Region Texistepec", "Region Oluta")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var deviceMatrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics( deviceMatrics)

        deviceWidthInPixel = deviceMatrics.widthPixels.toFloat()
        deviceDensity = deviceMatrics.density

        val inputStream: InputStream = resources.openRawResource(R.raw.traducciones)
        val traducciones = cargarTraducciones(inputStream)

        installSplashScreen()

        setContent {
                    MitraductorTheme {

                        Navigation(estadosRegionesMap, traducciones, applicationContext)
                                }

                    }
    }
}

@Composable
fun BienvenidaScreen(navController: NavController) {
    val nombrejuego = "MɨCHUY IANNA"
    val descripcionjuego = "Juego de apredizaje de lengua materna"
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        contentAlignment = Alignment.Center,


        ) {
        // Cambia esto por el nombre de tu video en los recursos
        VideoPlayerWithLoop(
            videoResId = R.raw.videobienvenida,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
            contentScale = ContentScale.FillWidth,
            lifecycle = LocalLifecycleOwner.current.lifecycle
        )

        Text(
            fontSize = 35.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif,
            text = nombrejuego + "\n" + descripcionjuego,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .zIndex(2f)
                .padding(top = 50.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF1F54AF) // Cambiar el color del texto si es necesario para que sea legible en la imagen de fondo
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Button(
                onClick = { navController.navigate("minijuegos") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .background(color = White, shape = RoundedCornerShape(8.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Continuar(Nɨkɨ)", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_continuar),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VideoPlayerWithLoop(
    videoResId: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds,
    lifecycle: Lifecycle // Agrega este parámetro
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/$videoResId")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ALL // Reproducir en ciclo continuo
        }
    }

    // Agrega este bloque de código
    DisposableEffect(lifecycle) {
        val lifecycleObserver = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                exoPlayer.playWhenReady = true
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                exoPlayer.playWhenReady = false
            }
        }

        lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { PlayerView(context).apply {
            player = exoPlayer
            useController = false } },
        modifier = modifier,
        update = { view ->
            view.onResume()
        }
    )
}

//funcion para acceder a json
fun cargarTraducciones(inputStream: InputStream): Map<String, String> {
    val jsonString = inputStream.bufferedReader().use { it.readText() }
    val json = Json { ignoreUnknownKeys = true }
    val traduccionesData = json.decodeFromString<TraduccionesData>(jsonString)
    return traduccionesData.traducciones
}

// Actualiza la función de traducción
fun traducir(textoOriginal: String, traducciones: Map<String, String>): String {
    println("Texto Original: $textoOriginal")
    println("Traducciones: $traducciones")

    val traduccion = traducciones[textoOriginal
        .trim().
        lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }] ?: "Traducción no disponible"

    println("Traducción: $traduccion")

    return traduccion
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuEstados(
    estados: Map<String, List<String>>,
    navController: NavController
) {
    var selectedButton by remember { mutableStateOf(MenuButton.traductor) }

    fun navigateTo(destination: String) {
        navController.popBackStack()
        navController.navigate(destination)
    }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Estados",
                navController = navController
            )
        },
        bottomBar = {
            NavigationBar(
                content = {
                    Row(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.primary)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomMenuItem(
                            iconRes = R.drawable.translate_icon,
                            text = "Traductor - Ikakpa'ap aŋmatyi",
                            isSelected = selectedButton == MenuButton.traductor,
                            onClick = { selectedButton = MenuButton.traductor },
                            modifier = Modifier.weight(1f)
                        )
                        BottomMenuItem(
                            iconRes = R.drawable.games_icon,
                            text = "Juegos - Michkuyyaj",
                            isSelected = selectedButton == MenuButton.minijuegos,
                            onClick = {
                                selectedButton = MenuButton.minijuegos
                                navigateTo("minijuegos")
                            },
                            modifier = Modifier.weight(1f)
                        )
                        BottomMenuItem(
                            iconRes = R.drawable.acercade_icon,
                            text = "Acerca de - Tyi iniitypa'ap",
                            isSelected = selectedButton == MenuButton.acercade,
                            onClick = {
                                selectedButton = MenuButton.acercade
                                navigateTo("acercade")
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.logodejuego),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 70.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(all = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(estados.keys.toList()) { estado ->
                        ListItemRow(item = estado) {
                            navController.navigate("traductor_regiones/${Uri.encode(estado)}")
                        }
                    }
                }
            }
        }
    )
}
@Composable
fun BottomMenuItem(
    @DrawableRes iconRes: Int,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.tertiary else Color.White
    val textColor = if (isSelected) MaterialTheme.colorScheme.onSecondary else LocalContentColor.current

    Box(
        modifier = modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .background(backgroundColor, RoundedCornerShape(16.dp))
    ) {
        // Botón con la animación
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(28.dp),
                tint = Color.Unspecified
                //tint = textColor
            )

            val scrollState = rememberScrollState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
            ) {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                    color = textColor,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            LaunchedEffect(scrollState) {
                delay(2000) // Espera 2 segundos antes de iniciar la animación
                while (true) {
                    scrollState.animateScrollTo(scrollState.maxValue, tween(6000, easing = LinearEasing))
                    delay(3000)
                    scrollState.animateScrollTo(0, tween(1))
                }
            }
        }

        // Botón transparente encima
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() } // Maneja los eventos de clic
                .background(Color.Transparent) // Hace que el botón sea transparente
        )
    }

}

// Enum para representar los diferentes botones del menú
enum class MenuButton {
    minijuegos,
    traductor,
    acercade
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuRegiones(
    estado: String,
    regiones: List<String>,
    navController: NavController
) {
    Scaffold(
        topBar = { ToolbarWithBackButton(
            title = "Regiones de $estado",
            navController = navController
        ) }
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 70.dp),
            contentPadding = PaddingValues(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(regiones) { region ->
                ListItemRow(item = region) {
                    // Aquí es donde navegamos a la pantalla del traductor cuando se selecciona una región
                    navController.navigate("traductor/$region")
                }
            }
        }
    }
}

@Composable
fun ListItemRow(item: String, onButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = MaterialTheme.shapes.small)
            .background(color = White)
            //.padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable(onClick = onButtonClick) // Hace que el Box sea "clickeable"
            .border(2.dp, Color.Green, shape = MaterialTheme.shapes.small)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 5.dp)
        ) {
            // Aquí agregamos la imagen asociada al estado
            Image(
                painter = painterResource(id = getIconResourceId(item)),
                contentDescription = "Icono de $item",
                modifier = Modifier.size(48.dp)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                text = item,
                style = MaterialTheme.typography.headlineSmall,
                //fontSize = 16.sp
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithBackButton(
    title: String,
    navController: NavController
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                IconButton(
                    onClick = {
                        // Utiliza el NavController para navegar hacia atrás
                        navController.navigateUp()
                    }

                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",tint = White)
                }
                // Ajusta el espacio según tu preferencia
                Text(text = title,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        },
            colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}



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
            .background(color = White)
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
            .background(color = White)
    )
}

@Composable
fun BotonTraducir(
    textoOriginal: String,
    traducciones: Map<String, String>,
    textoBoton: String,
    onTraduccionReady: (String) -> Unit,
    onTextArea1ValueChanged: (String) -> Unit,
    isTextArea1Visible: Boolean,
    textoTraducido: String
) {
    val textoTrimmed = textoOriginal.trim()
    println("Texto Original después de trim: \"$textoTrimmed\"")

    Button(
        onClick = {
            if (textoBoton == "Traducir") {
                val textoTraducido = traducir(textoOriginal, traducciones)
                onTraduccionReady(textoTraducido)
            } else if (textoBoton == "Traducción Inversa") {
                val textoSegundoTextArea = if (!isTextArea1Visible) textoTraducido else textoOriginal
                val claveTraduccion = traducirDesdeValor(textoSegundoTextArea, traducciones)
                // Actualiza el texto en el primer TextArea con la clave de la traducción
                onTextArea1ValueChanged(claveTraduccion)
            }
        },
        modifier = Modifier
            .width(150.dp)
            .height(55.dp),

    ) {
        Text(
            textoBoton,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun BotonCambiar(onClick: () -> Unit) {
    val WIDTH1 = 150.dp


    var width by remember { mutableStateOf(WIDTH1) }

    Button(
        onClick = {
            width = when (width) {

                else -> WIDTH1
            }
            onClick()
        },
        modifier = Modifier.width(width),
        //colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)

    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_cambiar),
            contentDescription = "Cambiar",
            modifier = Modifier.size(ButtonDefaults.IconSize)

        )
        Text("Cambiar")
    }
}

@Composable
fun Navigation(estadosRegionesMap: Map<String, List<String>>, traducciones: Map<String, String>, context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "bienvenida") {
        composable("bienvenida",
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
        ) {
            BienvenidaScreen(navController = navController)
        }
        composable("minijuegos",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(700)
                )
            }
        ) {
            minigamesScreen(navController = navController)
        }
        composable("estados",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(700)
                )
            }
        ) {
            MenuEstados(estadosRegionesMap, navController)
        }
        composable("acercade",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(700)
                )
            }
        ) {
            AcercaDeScreen(navController = navController)
        }
        composable(
            route = "traductor_regiones/{estado}",
            arguments = listOf(navArgument("estado") { type = NavType.StringType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
        ) { backStackEntry ->
            val estado = backStackEntry.arguments?.getString("estado")
            estado?.let {
                val regiones = estadosRegionesMap[it] ?: emptyList()
                MenuRegiones(estado, regiones, navController)
            }
        }
        composable(
            route = "juegos_regiones/{estado}",
            arguments = listOf(navArgument("estado") { type = NavType.StringType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
        ) { backStackEntry ->
            val estado = backStackEntry.arguments?.getString("estado")
            estado?.let {
                StateRegionsScreen(navController, it)
            }
        }
        composable(
            route = "juegos_categorias/{estado}/{region}",
            arguments = listOf(navArgument("estado") { type = NavType.StringType }, navArgument("region") { type = NavType.StringType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
        ) { backStackEntry ->
            val estado = backStackEntry.arguments?.getString("estado")
            val region = backStackEntry.arguments?.getString("region")
            if (estado != null && region != null) {
                CategorySelectionScreen(navController, estado, region)
            }
        }
        composable(
            route = "juegos/{estado}/{region}/{categoria}",
            arguments = listOf(navArgument("estado") { type = NavType.StringType }, navArgument("region") { type = NavType.StringType }, navArgument("categoria") { type = NavType.StringType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
        ) { backStackEntry ->
            val estado = backStackEntry.arguments?.getString("estado")
            val region = backStackEntry.arguments?.getString("region")
            val categoria = backStackEntry.arguments?.getString("categoria")
            if (estado != null && region != null && categoria != null) {
                RegionGamesScreen(navController, estado, region, categoria)
            }
        }
        composable(
            route = "traductor/{region}",
            arguments = listOf(navArgument("region") { type = NavType.StringType }),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
        ) { backStackEntry ->
            val region = backStackEntry.arguments?.getString("region")
            region?.let {
                val resourceId = getResourceIdForRegion(it)
                val inputStream = context.resources.openRawResource(resourceId)
                val traduccionesRegion = cargarTraducciones(inputStream)
                TraductorScreen(region, navController, traduccionesRegion, context)
            }
        }
        composable(
            route = "{region}/{categoria}/{game}",
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
        ) { backStackEntry ->
            val region = backStackEntry.arguments?.getString("region") ?: ""
            val categoria = backStackEntry.arguments?.getString("categoria") ?: ""
            val game = backStackEntry.arguments?.getString("game") ?: ""
            when (game) {
                "Sopa de letras" -> SopaDeLetras(navController, region)
                "Memorama" -> MemoramaGameComposable(navController, region)
                "Crucigrama" -> CrosswordGame(navController, region)
                "Acompletar la oración" -> FillInTheBlanksGame(navController, region)
                "Ahorcado" -> HangmanScreen(navController, region)
                "Colorear" -> ColoringScreen(navController, region)
                "Partes del cuerpo" -> Cuerpo(navController, region)
                "Colores" -> {
                    startGameLoop(context)
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TraductorScreen(
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
                navController = navController
            )
        },
    ) {
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
            context = context // Pasar context como argumento
        )
    }
}

@Composable
fun ViewContainerContent(
    isTextArea1Visible: Boolean,
    textoOriginal: String,
    textoTraducido: String,
    onTextArea1ValueChanged: (String) -> Unit,
    onTextArea2ValueChanged: (String) -> Unit,
    onBotonCambiarClick: () -> Unit,
    onBotonTraducirClick: (String) -> Unit,
    onTraduccionReady: (String) -> Unit, // Añadir este argumento
    traducciones: Map<String, String>, // Agregar traducciones como parámetro
    regionSeleccionada: String, // Agregar regionSeleccionada como parámetro
    context: Context // Agregar context como parámetro
) {
    var textoBotonTraducir by remember { mutableStateOf("Traducir") }
    LazyColumn(
        modifier = Modifier.padding(top = 70.dp,),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            if (isTextArea1Visible) {
                Row {
                    TextArea(textoOriginal=textoOriginal, onValueChanged = { newText -> onTextArea1ValueChanged(newText) })
                }
            } else {
                Row {
                    TextAreaExample2(
                        textoTraducido = textoTraducido,
                        onValueChanged = { newText ->
                            onBotonTraducirClick(newText)
                        }
                    )
                }
            }

            Row {
                BotonCambiar {
                    // Cambia la visibilidad y ejecuta la animación
                    onBotonCambiarClick()
                    textoBotonTraducir = if (textoBotonTraducir == "Traducir") "Traducción Inversa" else "Traducir"
                }
            }

            Row {
                if (isTextArea1Visible) {
                    TextAreaExample2(
                        textoTraducido = textoTraducido,
                        onValueChanged = { newText ->
                            onBotonTraducirClick(newText)
                        }
                    )
                } else {
                    TextArea(textoOriginal=textoOriginal,onValueChanged = { newText -> onTextArea1ValueChanged(newText) })
                }
            }
            println("Valor de textoTraducido: $textoTraducido")
            Row {
                println("Valor de textoOriginal: \"$textoOriginal\"")
                BotonTraducir(
                    textoOriginal = textoOriginal,
                    traducciones = traducciones,
                    textoBoton = textoBotonTraducir,
                    onTraduccionReady = { traduccion ->
                        // Utiliza la traducción recibida
                        onBotonTraducirClick(traduccion)
                        onTraduccionReady(traduccion)
                    },
                    onTextArea1ValueChanged = { claveTraduccion  ->
                        // Actualizar texto en el primer TextArea
                        onTextArea1ValueChanged(claveTraduccion )
                    },
                    isTextArea1Visible = isTextArea1Visible,
                    textoTraducido = textoTraducido
                )
            }
            if (textoBotonTraducir=="Traducción Inversa") {
                Row {
                    SpecialCharacterButton(character = "ɨ", onCharacterClick = { character ->
                        // Agrega el caracter al texto en TextAreaExample2
                        onTextArea2ValueChanged(textoTraducido + character + "")
                    })
                    SpecialCharacterButton(character = "’", onCharacterClick = { character ->
                        // Agrega el caracter al texto en TextAreaExample2
                        onTextArea2ValueChanged(textoTraducido + character + "")
                    })
                    SpecialCharacterButton(character = "ŋ", onCharacterClick = { character ->
                        // Agrega el caracter al texto en TextAreaExample2
                        onTextArea2ValueChanged(textoTraducido + character + "")
                    })
                }
            }
            Row {
                Button(
                    onClick = {
                        // Llama a la función reproducirAudio pasando la palabra original y la región seleccionada
                        reproducirAudio(textoOriginal, regionSeleccionada, context)
                    }
                ) {
                    Text("Reproducir Audio")
                }
            }

        }
    }
}

@Composable
fun SpecialCharacterButton(character: String, onCharacterClick: (String) -> Unit) {
    Button(onClick = { onCharacterClick(character) }) {
        Text(character)
    }
}

// Función para obtener el ID del recurso para el archivo JSON de la región
private fun getResourceIdForRegion(region: String): Int {
    return when (region) {
        "Region Soteapan" -> R.raw.veracruz_regsoteapan_traducciones
        "Region Sayula" -> R.raw.veracruz_regsayula_traducciones
        "Region Texistepec" -> R.raw.veracruz_regtexistepec_traducciones
        "Region Oluta" -> R.raw.veracruz_regoluta_traducciones
        "Region San Gabriel Chilac" -> R.raw.puebla_regnorte_traducciones
        "Region Ocotepec" -> R.raw.oaxaca_regitsmo_traducciones
        else -> R.raw.traducciones// Define un archivo JSON predeterminado si es necesario
    }
}

// Función para obtener el ID de la imagen asociada al estado
@DrawableRes
fun getIconResourceId(estado: String): Int {
    return when (estado) {
        "Puebla(Nahualt)" -> R.drawable.puebla_icon
        "Oaxaca(Mixe)" -> R.drawable.oaxaca_icon
        "Veracruz(Popoluca)" -> R.drawable.veracruz_icon
        // Agrega más casos según sea necesario para otros estados
        else -> R.drawable.regiones// Imagen predeterminada en caso de que no haya ninguna definida
    }
}

@DrawableRes
fun getIconResourceIdgame(estado: String): Int {
    return when (estado) {
        "Puebla" -> R.drawable.puebla_icon
        "Oaxaca" -> R.drawable.oaxaca_icon
        "Veracruz" -> R.drawable.veracruz_icon
        // Agrega más casos según sea necesario para otros estados
        else -> R.drawable.regiones// Imagen predeterminada en caso de que no haya ninguna definida
    }
}

fun reproducirAudio(palabra: String, region: String, context: Context) {
    val nombreArchivo = "audios/palabra_${palabra
        .trim()
        .lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}" +
            "_${region.toLowerCase(Locale.ROOT).replace(" ","")}.mp3"
    println("Nombre del archivo de audio: $nombreArchivo")

    try {
        // Crear el reproductor de audio y reproducir el audio
        val assetFileDescriptor = context.assets.openFd(nombreArchivo)
        val mediaPlayer = MediaPlayer().apply {
            setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
            prepare()
            start()
        }
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release() // Liberar recursos después de reproducir el audio
        }
    } catch (e: IOException) {
        Toast.makeText(context, "No se encontró el audio para \"$palabra\" en la región \"$region\"", Toast.LENGTH_SHORT).show()
    }
}



// Función para buscar la clave basada en el valor en el JSON
fun traducirDesdeValor(textoTraducido: String, traducciones: Map<String, String>): String {
    for ((clave, valorTraduccion) in traducciones) {
        println("Este es el valorTradducción" + valorTraduccion)
        println("Este es el valor" + textoTraducido)
        if (valorTraduccion == textoTraducido
            .trim()
            .lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) {
            println("Este es la clave" + clave)
            return clave
        }

    }
    return "Clave no encontrada"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AcercaDeScreen(navController: NavController) {
    var selectedButton by remember { mutableStateOf(MenuButton.acercade) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            topBar = {
                ToolbarWithBackButton(
                    title = "Acerca de",
                    navController = navController
                )
            },
            bottomBar = {
                NavigationBar(
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.primary),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BottomMenuItem(
                                iconRes = R.drawable.translate_icon,
                                text = "Traductor - Ikakpa'ap aŋmatyi",
                                isSelected = selectedButton == MenuButton.traductor,
                                onClick = {
                                    selectedButton = MenuButton.traductor
                                    navController.popBackStack()
                                    navController.navigate("estados")
                                },
                                modifier = Modifier.weight(1f)
                            )
                            BottomMenuItem(
                                iconRes = R.drawable.games_icon,
                                text = "Juegos - Michkuyyaj",
                                isSelected = selectedButton == MenuButton.minijuegos,
                                onClick = {
                                    selectedButton = MenuButton.minijuegos
                                    navController.popBackStack()
                                    navController.navigate("minijuegos")
                                },
                                modifier = Modifier.weight(1f)
                            )
                            BottomMenuItem(
                                iconRes = R.drawable.acercade_icon,
                                text = "Acerca de - Tyi iniitypa'ap",
                                isSelected = selectedButton == MenuButton.acercade,
                                onClick = { selectedButton = MenuButton.acercade },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                )
            },
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.logodejuego),
                        contentDescription = null,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .align(Alignment.Center)
                            .graphicsLayer(alpha = 0.3f),
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item {
                            Text(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                text = "MICHUY IANNA",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    shadow = Shadow(
                                        offset = Offset(10f, 10f),
                                        blurRadius = 10f
                                    )
                                ),
                                color = Color.Black
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            Text(
                                text = "Versión: \n 1.0",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            Text(
                                text = "Esta aplicación esta diseñada para ayudar a los usuarios a aprender a comunicarse eficazmente " +
                                        "en diversas lenguas maternas de mexico en todo el mundo. Con un enfoque en la " +
                                        "preservación y promoción de la diversidad lingüística, esta aplicación ofrece una " +
                                        "plataforma intuitiva y accesible que permite a los usuarios Jugar y traducir entre una " +
                                        "amplia gama de idiomas nativos, facilitando así la comunicación entre personas de " +
                                        "diferentes culturas y trasfondos lingüísticos.",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            Text(
                                text = "Desarrollado por: \n Equipo MɨCHKUY IANNA",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            Text(
                                text = "Contacto: ",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                        item {
                            EmailLink(email = "sgjesus2000@gmail.com")
                        }
                        item {
                            Spacer(modifier = Modifier.height(35.dp))
                        }
                        // Agrega más elementos según sea necesario
                    }
                }
            }
        )
    }
}



@Composable
fun EmailLink(email: String) {
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(
            tag = "URL",
            annotation = "mailto:$email"
        )
        withStyle(
            style = SpanStyle(color = Color.Blue, fontSize = 16.sp)
        ) {
            append(email)
        }
        pop()
    }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle the result if needed
    }
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse(annotation.item)
                    }
                    context.startActivity(intent)
                }
        }
    )
}

val games = mapOf(
    "Veracruz" to mapOf(
        "Soteapan" to mapOf(
            "Básico" to listOf("Memorama", "Colorear"),
            "Medio" to listOf("Sopa de letras", "Crucigrama", "Ahorcado"),
            "Avanzado" to listOf("Acompletar la oración", "Partes del cuerpo")
        ),
        "Sayula" to mapOf(
            "Básico" to listOf("Memorama", "Colorear"),
            "Medio" to listOf("Sopa de letras", "Crucigrama", "Ahorcado"),
            "Avanzado" to listOf()
        ),
        "Oluta" to mapOf(
            "Básico" to listOf("Memorama", "Colorear"),
            "Medio" to listOf("Sopa de letras", "Crucigrama", "Ahorcado"),
            "Avanzado" to listOf()
        ),
        "Texistepec" to mapOf(
            "Básico" to listOf("Memorama", "Colorear"),
            "Medio" to listOf("Sopa de letras", "Crucigrama", "Ahorcado"),
            "Avanzado" to listOf()
        )
    ),
    "Puebla" to mapOf(
        "San Gabriel Chilac" to mapOf(
            "Básico" to listOf("Memorama", "Colorear"),
            "Medio" to listOf("Sopa de letras", "Crucigrama", "Ahorcado"),
            "Avanzado" to listOf()
        )
    ),
    "Oaxaca" to mapOf(
        "Ocotepec" to mapOf(
            "Básico" to listOf("Memorama", "Colorear"),
            "Medio" to listOf("Sopa de letras", "Crucigrama", "Ahorcado"),
            "Avanzado" to listOf()
        )
    )
)



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun minigamesScreen(navController: NavController) {
    var selectedButton by rememberSaveable { mutableStateOf(MenuButton.minijuegos) }

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Juegos",
                navController = navController
            )
        },
        bottomBar = {
            NavigationBar(
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primary),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomMenuItem(
                            iconRes = R.drawable.translate_icon,
                            text = "Traductor - Ikakpa'ap aŋmatyi",
                            isSelected = selectedButton == MenuButton.traductor,
                            onClick = {
                                if (selectedButton != MenuButton.traductor) {
                                    selectedButton = MenuButton.traductor
                                    navController.popBackStack()
                                    navController.navigate("estados")
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                        BottomMenuItem(
                            iconRes = R.drawable.games_icon,
                            text = "Juegos - Michkuyyaj",
                            isSelected = selectedButton == MenuButton.minijuegos,
                            onClick = { selectedButton = MenuButton.minijuegos },
                            modifier = Modifier.weight(1f)
                        )
                        BottomMenuItem(
                            iconRes = R.drawable.acercade_icon,
                            text = "Acerca de - Tyi iniitypa'ap",
                            isSelected = selectedButton == MenuButton.acercade,
                            onClick = {
                                if (selectedButton != MenuButton.acercade) {
                                    selectedButton = MenuButton.acercade
                                    navController.popBackStack()
                                    navController.navigate("acercade")
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.logodejuego),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    items(games.keys.toList()) { state ->
                        StateCard(state = state) {
                            navController.navigate("juegos_regiones/$state")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun StateCard(state: String, onClick: () -> Unit) {
    //val madera: Painter = painterResource(id = R.drawable.madera)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f) // Para hacer la tarjeta cuadrada
            .clip(CircleShape) // Para hacer la tarjeta redonda
            .clickable { onClick() },
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(8.dp),
        //border = BorderStroke(4.dp, color=verde)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    //.background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp) // Tamaño del contenedor del icono
                        .clip(CircleShape) // Forma circular del contenedor del icono
                        .border(2.dp, Color.Green, CircleShape), // Borde verde alrededor del contenedor del icono
                    contentAlignment = Alignment.Center
                ) {
                    val imageResource = getIconResourceIdgame(state)
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Imagen del estado",
                        modifier = Modifier.size(64.dp)
                    )
                }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
            }
        }
    }
}

@Composable
fun StateRegionsScreen(navController: NavController, state: String) {
    val regions = games[state] ?: emptyMap()
    val state=state

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = state,
                navController = navController
            )
        },
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(regions.keys.toList()) { region ->
                    RegionCard(region = region) {
                        navController.navigate("juegos_categorias/$state/$region")
                    }
                }
            }
        }
    )
}

@Composable
fun RegionCard(region: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(7.dp),
        border = BorderStroke(4.dp, color= Green)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = region,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun RegionGamesScreen(navController: NavController, state: String, region: String, category: String) {
    val gamesList = games[state]?.get(region)?.get(category) ?: emptyList()

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "$region - $category",
                navController = navController
            )
        },
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(gamesList) { game ->
                    GameCard(game = game) {
                        navController.navigate("$region/$category/$game")
                    }
                }
            }
        }
    )
}

@Composable
fun GameCard(game: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(4.dp, color= Green)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = game,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CategorySelectionScreen(navController: NavController, state: String, region: String) {
    val categories = listOf("Básico", "Medio", "Avanzado")

    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "$region - Categorías",
                navController = navController
            )
        },
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(categories) { category ->
                    CategoryCard(category = category) {
                        navController.navigate("juegos/$state/$region/$category")
                    }
                }
            }
        }
    )
}

@Composable
fun CategoryCard(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(4.dp, color= Green)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
