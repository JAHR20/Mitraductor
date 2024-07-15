import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itsa.mitraductor.app.ToolbarWithBackButton
import com.itsa.mitraductor.memorama.EmojiViewModel
import com.itsa.mitraductor.memorama.ImageModel
import com.itsa.mitraductor.memorama.MusicViewModel
import com.itsa.mitraductor.memorama.subcategoriesByRegion
import com.itsa.mitraductor.ui.theme.MusicViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MemoramaGameComposable(navController: NavController, region: String) {
    val viewModel: EmojiViewModel = viewModel()
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val musicViewModel: MusicViewModel = viewModel(factory = MusicViewModelFactory(lifecycle))

    val subcategories = subcategoriesByRegion[region] ?: listOf() // Obtén las subcategorías disponibles para la región
    var selectedSubcategory by remember { mutableStateOf(subcategories.firstOrNull() ?: "") }

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
    Scaffold(
        topBar = {
            ToolbarWithBackButton(
                title = "Memorama",
                navController = navController
            )
        },
        content = {
            Box(modifier = Modifier.padding(top = 70.dp)) {
                if (allCardsMatched) {
                    CongratsMessage(viewModel, region, selectedSubcategory)
                } else {
                    MainContent(cards, viewModel, region, selectedSubcategory, subcategories)
                }
            }
        }
    )
}
@Composable
fun SubcategorySelectionButton(
    subcategories: List<String>,
    onSubcategorySelected: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Button(onClick = { showDialog = true }) {
            Text(text = "Seleccionar Subcategoría")
        }

        // AlertDialog para mostrar las opciones de subcategoría
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Seleccionar Subcategoría") },
                text = {
                    Column {
                        subcategories.forEach { subcategory ->
                            TextButton(onClick = {
                                onSubcategorySelected(subcategory)
                                showDialog = false
                            }) {
                                Text(text = subcategory)
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


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(cards: List<ImageModel>, viewModel: EmojiViewModel, region: String, subcategory: String, subcategories: List<String>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Memorama de Lenguaje Popoluca")
                },
                actions = {
                    IconButton(onClick = { viewModel.loadImages(region, subcategory) }) {
                        Icon(
                            Icons.Filled.Refresh,
                            contentDescription = "Reload Game"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(top = 60.dp, start = 16.dp, end = 16.dp)
        ) {
            SubcategorySelectionButton(
                subcategories = subcategories,
                onSubcategorySelected = { selectedSubcategory ->
                    viewModel.loadImages(region, selectedSubcategory)
                }
            )

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
    Box(
        modifier = Modifier
            .padding(all = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(150.dp)
                .background(
                    color = Color.Black.copy(alpha = if (image.isVisible) 0.4F else 0.0F),
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    if (image.isVisible) {
                        viewModel.updateShowVisibleCard(image.id, region,subcategory)
                    }
                }

        ) {
            if (image.isSelect) {
                Image(
                    painter = painterResource(id = image.imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }
    }
}


@Composable
fun CongratsMessage(viewModel: EmojiViewModel, region: String, subcategory: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Felicidades!",
                fontSize = 35.sp,
                color = Color(0xFF6A1B9A),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            println("Imágenes cargadas para la región: $region")
            IconButton(
                onClick = { viewModel.loadImages(region,subcategory) },
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


