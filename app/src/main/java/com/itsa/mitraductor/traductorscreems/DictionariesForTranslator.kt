package com.itsa.mitraductor.traductorscreems

import com.itsa.mitraductor.R

// Función para obtener el ID del recurso para el archivo JSON de la región
fun getResourceIdForRegion(region: String): Int {
    return when (region) {
        "Region Soteapan" -> R.raw.veracruz_regsoteapan_traducciones
        "Region Sayula" -> R.raw.veracruz_regsayula_traducciones
        "Region Texistepec" -> R.raw.veracruz_regtexistepec_traducciones
        "Region Oluta" -> R.raw.veracruz_regoluta_traducciones
        "Region San Gabriel Chilac" -> R.raw.puebla_regnorte_traducciones
        "Region Ocotepec" -> R.raw.oaxaca_regitsmo_traducciones
        "Region Manzanillo" -> R.raw.colima_regmanzanillo_traducciones
        else -> R.raw.traducciones// Define un archivo JSON predeterminado si es necesario
    }
}
