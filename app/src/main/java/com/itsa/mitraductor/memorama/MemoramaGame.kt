import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itsa.mitraductor.app.ToolbarWithBackButton
import com.itsa.mitraductor.memorama.EmojiViewModel
import com.itsa.mitraductor.memorama.ImageModel
import com.itsa.mitraductor.memorama.MusicViewModel
import com.itsa.mitraductor.memorama.getWordForRegion
import com.itsa.mitraductor.memorama.subcategoriesByRegion
import com.itsa.mitraductor.ui.theme.MusicViewModelFactory

val levelConfig = mapOf(
    1 to Pair(15, 60), // Nivel 1: 20 vidas, 60 segundos
    2 to Pair(10, 50), // Nivel 2: 15 vidas, 50 segundos
    3 to Pair(5, 40)  // Nivel 3: 10 vidas, 40 segundos
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MemoramaGameComposable(navController: NavController, region: String) {
    val viewModel: EmojiViewModel = viewModel()
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val musicViewModel: MusicViewModel = viewModel(factory = MusicViewModelFactory(lifecycle))
    var currentLevel by remember { mutableIntStateOf(1) }
    val (vidasInicial, tiempoInicial) = levelConfig[currentLevel] ?: Pair(20, 60) // Valores por defecto si no se encuentra el nivel
    val subcategories = subcategoriesByRegion[region] ?: listOf() // Obtén las subcategorías disponibles para la región
    var selectedSubcategory by remember { mutableStateOf(subcategories.firstOrNull() ?: "") }
    val lenguaMaterna = getWordForRegion(region)

    LaunchedEffect(currentLevel) {
        //val (vidasInicial, tiempoInicial) = levelConfig[currentLevel] ?: Pair(20, 60)
        viewModel.setVidas(vidasInicial)
        viewModel.setTiempoRestante(tiempoInicial)
        //viewModel.loadImages(region, "Nivel $currentLevel")
    }

    viewModel.audioToPlay.observeAsState().value?.let { audioFileName ->
        viewModel.playAudio(audioFileName, context)
    }

    LaunchedEffect(key1 = true) {
        if (selectedSubcategory.isNotEmpty()) {
            viewModel.loadImages(region, selectedSubcategory)
            musicViewModel.playMusic(context)
        }
    }

    val cards: List<ImageModel> by viewModel.getImages().observeAsState(listOf())
    val allCardsMatched by viewModel.allCardsMatched.observeAsState(false)
    val juegoTerminado by viewModel.juegoTerminado.observeAsState(false) // Observar el estado de juego terminado
    val JuegoGanado by viewModel.juegoGanado.observeAsState(false)
    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Memorama de la lengua $lenguaMaterna",
                navController = navController,
            )
        },
        content = {paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when {
                    allCardsMatched && currentLevel == 3 -> {
                        CongratsMessage(viewModel, region, selectedSubcategory) {
                            selectedSubcategory = "Nivel 1"
                            currentLevel = 1
                            viewModel.resetGame()
                            viewModel.loadImages(region, selectedSubcategory)
                        }
                    }
                    allCardsMatched && currentLevel < 3-> {
                        // Espera a que las nuevas imágenes se carguen antes de avanzar de nivel
                        NextLevel(viewModel, region, "Nivel ${currentLevel + 1}") {
                            currentLevel += 1
                            //val (vidasInicial, tiempoInicial) = levelConfig[currentLevel] ?: Pair(20, 60)
                            selectedSubcategory = "Nivel $currentLevel"
                            viewModel.loadImages(region, selectedSubcategory)
                        }
                    }
                    juegoTerminado -> {
                        // Mostrar mensaje de juego terminado
                        LosseMessage(viewModel, region, selectedSubcategory){
                            selectedSubcategory="Nivel 1"
                            viewModel.resetGame()
                            viewModel.loadImages(region, selectedSubcategory)
                        }
                    }
                    cards.isEmpty() -> {
                        // Mostrar mensaje de carga mientras se cargan las imágenes
                        Text("Cargando cartas...")
                    }
                    else -> {
                        // Mostrar el contenido principal del juego cuando las imágenes estén listas
                        MainContent(cards, viewModel, region, selectedSubcategory, subcategories)
                    }
                }
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(cards: List<ImageModel>, viewModel: EmojiViewModel, region: String, subcategory: String, subcategories: List<String>) {
    // Observa el número de vidas y el tiempo restante
    val vidas by viewModel.vidas.observeAsState(initial = 15) // Cambia el valor inicial según tu lógica
    val tiempoRestante by viewModel.tiempoRestante.observeAsState(initial = 60) // Cambia el valor inicial según tu lógica
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    // Esto empujará "Vidas" a la izquierda y "Tiempo" a la derecha
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    // --- LADO IZQUIERDO (Vidas) ---
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Vidas",
                            modifier = Modifier.size(28.dp),
                            tint = Color.Red
                        )
                        // Sugerencia: Baja un poco el tamaño si sigue sin caber (ej. 20.sp)
                        Text(
                            text = " : $vidas",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1 // Asegura que no baje de línea
                        )
                    }

                    // --- LADO DERECHO (Tiempo) ---
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Timer,
                            contentDescription = "Tiempo Restante",
                            modifier = Modifier.size(28.dp),
                            tint = Color.Blue
                        )
                        Text(
                            text = " : $tiempoRestante",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1 // Asegura que no baje de línea
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(top = 60.dp, start = 10.dp, end = 10.dp)
        ) {

            Spacer(modifier = Modifier.height(2.dp))

            CardsGrid(cards = cards, viewModel = viewModel, region, subcategory)
        }
    }
}
@Composable
fun CardsGrid(cards: List<ImageModel>, viewModel: EmojiViewModel, region: String, subcategory: String) {
    LazyVerticalGrid(
        GridCells.Fixed(4)
    ) {
        items(cards.count()) { cardIndex ->
            CardItem(cards[cardIndex], viewModel, region, subcategory)
        }
    }
}

@Composable
fun CardItem(image: ImageModel, viewModel: EmojiViewModel, region: String, subcategory: String) {
    // Estado de rotación basado en si la carta está seleccionada
    val rotation by animateFloatAsState(
        targetValue = if (image.isSelect) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )
    val rotationimg by animateFloatAsState(
        targetValue = if (image.isSelect) -180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    // Usamos Card en lugar de Box para mostrar la carta
    if (image.isVisible) {
    Card(
        modifier = Modifier
            .padding(all = 5.dp)
            .size(150.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density // Ajuste de distancia de cámara para efecto 3D
            }
            .clickable {
                // Lógica de selección de la carta
                if (image.isVisible) {
                    viewModel.updateShowVisibleCard(image.id, region, subcategory)
                }
            }
            .background(
                color = Color.White.copy(alpha = if (image.isVisible) 0.4F else 0.0F),
                //shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(if (image.isVisible) 8.dp else 0.dp),
    ) {
        if (rotation <= 90f) {
                // Mostrar reverso cuando la rotación es de 0 a 90 grados
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.QuestionMark,
                        contentDescription = "Inverso",
                        modifier = Modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            } else {
                // Mostrar anverso cuando la rotación es de 90 a 180 grados
                Image(
                    painter = painterResource(id = image.imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .graphicsLayer {
                            rotationY = rotationimg
                        }
                )
            }
        }
    }else {
        // Opción para que no haya nada si `image.isVisible` es falso
        Spacer(modifier = Modifier.size(150.dp)) // Mantener el espacio sin mostrar el Card
    }
}


@Composable
fun LosseMessage(viewModel: EmojiViewModel, region: String, subcategory: String, onCongratsMessage: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Ups, perdiste vuelve a intentarlo!",
                textAlign = TextAlign.Center,
                fontSize = 35.sp,
                color = Color(0xFF6A1B9A),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            println("Imágenes cargadas para la región: $region")
            IconButton(
                onClick = { onCongratsMessage()},
                modifier = Modifier
                    .padding(16.dp)
                    .size(56.dp)
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "Reload Game",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

@Composable
fun NextLevel(viewModel: EmojiViewModel, region: String, subcategory: String, onNextLevel: () -> Unit) {
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Felicidades avanza al siguiente nivel!",
                textAlign = TextAlign.Center,
                fontSize = 35.sp,
                color = Color(0xFF6A1B9A),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            println("Imágenes cargadas para la región: $region")
            IconButton(
                onClick = { onNextLevel()  },
                modifier = Modifier
                    .padding(16.dp)
                    .size(56.dp)
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "Reload Game",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}


@Composable
fun CongratsMessage(viewModel: EmojiViewModel, region: String, subcategory: String, onCongratsMessage: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡felicidades! Lo haz hecho genial",
                textAlign = TextAlign.Center,
                fontSize = 35.sp,
                color = Color(0xFF6A1B9A),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            println("Imágenes cargadas para la región: $region")
            IconButton(
                onClick = { onCongratsMessage()},
                modifier = Modifier
                    .padding(16.dp)
                    .size(56.dp)
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "Reload Game",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}





