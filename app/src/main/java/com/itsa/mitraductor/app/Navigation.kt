package com.itsa.mitraductor.app

import MemoramaGameComposable
import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.itsa.mitraductor.acercadescreen.AcercaDeScreen
import com.itsa.mitraductor.bienvenidacreem.BienvenidaScreen
import com.itsa.mitraductor.crucigrama.CrosswordGame
import com.itsa.mitraductor.cuerpo.Cuerpo
import com.itsa.mitraductor.dibujos.ColoringScreen
import com.itsa.mitraductor.fillntheblanks.FillInTheBlanksGame
import com.itsa.mitraductor.gamesscreens.CategorySelectionScreen
import com.itsa.mitraductor.gamesscreens.RegionGamesScreen
import com.itsa.mitraductor.gamesscreens.StateRegionsScreen
import com.itsa.mitraductor.gamesscreens.minigamesScreen
import com.itsa.mitraductor.hangmangame.HangmanScreen
import com.itsa.mitraductor.media.startGameLoop
import com.itsa.mitraductor.soupgame.SopaDeLetras
import com.itsa.mitraductor.traductorscreems.MenuEstados
import com.itsa.mitraductor.traductorscreems.MenuRegiones
import com.itsa.mitraductor.traductorscreems.TraductorScreen
import com.itsa.mitraductor.traductorscreems.cargarTraducciones
import com.itsa.mitraductor.traductorscreems.getResourceIdForRegion

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

