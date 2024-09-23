package com.itsa.mitraductor

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.itsa.mitraductor.app.Navigation
import com.itsa.mitraductor.traductorscreems.cargarTraducciones
import com.itsa.mitraductor.ui.theme.MitraductorTheme
import java.io.InputStream
import kotlin.properties.Delegates

var deviceWidthInPixel by Delegates.notNull<Float>()
var deviceDensity by Delegates.notNull<Float>()
class MainActivity : ComponentActivity() {

    val estadosRegionesMap: Map<String, List<String>> = mapOf(
        "Puebla(Náhuatl)" to listOf("Region San Gabriel Chilac"),
        "Oaxaca(Mixe)" to listOf("Region Ocotepec"),
        "Veracruz(Popoluca)" to listOf("Region Soteapan", "Region Sayula", "Region Texistepec", "Region Oluta")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var deviceMatrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics( deviceMatrics)

        deviceWidthInPixel = deviceMatrics.widthPixels.toFloat()
        deviceDensity = deviceMatrics.density

        installSplashScreen()

        setContent {

            MitraductorTheme {

                Navigation(estadosRegionesMap, applicationContext)

            }
        }
    }
}



