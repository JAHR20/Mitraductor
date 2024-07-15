package com.itsa.mitraductor.hangmangame

import com.itsa.mitraductor.R

class SoteapanRegionDataProvider : WordImageProvider {
    override val wordToImageMap: Map<String, Int> = mapOf(
        // Asocia palabras con imágenes para la región Soteapan
        "CHOOMO’" to R.drawable.img_memorama_abuela,
        "WEEWE’" to R.drawable.img_memorama_abuelo,
        "WOOÑI’" to R.drawable.img_memorama_ninia,
        "JAYCHɨɨXI" to R.drawable.img_memorama_ninio,
        "KOOBAK" to R.drawable.hangman_cabeza,
        "JA’ŋKU" to R.drawable.hangman_brazo,
        "WɨɨTYPUY" to R.drawable.hangman_pierna,
        "JɨP" to R.drawable.hangman_boca,
        "IXKUY" to R.drawable.hangman_ojo,
        "IXKAPAK" to R.drawable.hangman_ceja,
        "TAATSɨK" to R.drawable.hangman_oreja,
        "WIÑPAK" to R.drawable.hangman_cara,
        "IXKUYPɨK" to R.drawable.hangman_pestania,
        "KɨNKɨ" to R.drawable.hangman_garganta,
        "KEEWE" to R.drawable.hangman_hombro,
        "SUUTKUY" to R.drawable.hangman_codo,
        "PUY" to R.drawable.hangman_pie,
        "WAAY" to R.drawable.hangman_cabello
        // Agrega más asociaciones según sea necesario para la región 1
    )

    override val gameWords: List<String> = listOf(
        // Palabras para la región Soteapan
        //"PɨɨXIÑ",
        //"YOOMO",
        "MɨJTAY",
        "KOOBAK",
        "JA’ŋKU",
        "WɨɨTYPUY",
        "JɨP",
        "IXKUY",
        "KɨNKɨ",
        "IXKAPAKK",
        "TAATSɨK",
        "PUY",
        "WAAY",
        "SUUTKUY",
        "KEEWE",
        "WIÑPAK",
        "IXKUYPɨK",
        //"ɨSKɨ",

        "WɨɨTYPUY",
    )


}

class SayulaRegionDataProvider : WordImageProvider {
    override val wordToImageMap: Map<String, Int> = mapOf(
        // Asocia palabras con imágenes para la región Sayula
        "JOJN" to R.drawable.pajaro,
        "PI’ICHIK" to R.drawable.naranja,
        "ICHIM" to R.drawable.cerdo,
        "YEMAN" to R.drawable.abanico,
        "UUX" to R.drawable.mosquito,
        "AKX" to R.drawable.pesacdo,
        "OOY" to R.drawable.camaron,
        "NɨN" to R.drawable.tortilla,
        // Agrega más asociaciones según sea necesario para la región 2
    )

    override val gameWords: List<String> = listOf(
        // Palabras para la región Sayula
        "JOJN",
        "PI’ICHIK",
        "ICHIM",
        "YEMAN",
        "UUX",
        "AKX",
        "OOY",
        "NɨN",
    )

}

class OlutaRegionDataProvider : WordImageProvider {
    override val wordToImageMap: Map<String, Int> = mapOf(
        // Asocia palabras con imágenes para la región Sayula
        "JAYA'N" to R.drawable.img_ahorcado_oluta_lapiz,
        "MACHITI" to R.drawable.img_ahorcado_oluta_machete,
        "SANTIYAJ" to R.drawable.img_ahorcado_oluta_sandia,
        "CO'PA'N" to R.drawable.img_ahorcado_oluta_sombrero,
        "PACA'S" to R.drawable.img_ahorcado_oluta_vaca,
        "NACA'N" to R.drawable.img_ahorcado_oluta_jaula,
        "TULCSA'N" to R.drawable.img_ahorcado_oluta_vela,
        "TSUCSA'N" to R.drawable.img_ahorcado_oluta_tijeras,
        "JUYU'C" to R.drawable.img_ahorcado_oluta_arania,
        "CHIPI'N" to R.drawable.img_ahorcado_oluta_tomate,
        // Agrega más asociaciones según sea necesario para la región 2
    )

    override val gameWords: List<String> = listOf(
        // Palabras para la región Sayula
        "JAYA'N", //lapiz
        "MACHITI", //machete
        "SANTIYAJ", //sandia
        "CO'PA'N", //Sombrero
        "PACA'S", //vaca
        "NACA'N", //Jaula
        "TULCSA'N", //vela
        "TSUCSA'N", //tijeras
        "JUYU'C", //Araña
        "CHIPI'N" //tomate
    )

}

class TexistepecRegionDataProvider : WordImageProvider {
    override val wordToImageMap: Map<String, Int> = mapOf(
        // Asocia palabras con imágenes para la región Sayula
        "PA:č" to R.drawable.img_ahorcado_texistepec_iguana,
        "BEMSA?" to R.drawable.img_ahorcado_texistepec_arbol,
        "šEš" to R.drawable.img_ahorcado_texistepec_carne,
        "SO:KKE?" to R.drawable.img_ahorcado_texistepec_caracol,
        "PAK" to R.drawable.img_ahorcado_texistepec_hueso,
        "DAŋ¢Uŋ" to R.drawable.img_ahorcado_texistepec_collar,
        "PIK" to R.drawable.img_ahorcado_texistepec_pluma,
        "HA:M" to R.drawable.img_ahorcado_texistepec_sol,
        // Agrega más asociaciones según sea necesario para la región 2
    )

    override val gameWords: List<String> = listOf(
        // Palabras para la región Sayula
        "PA:č",
        "BEMSA?",
        "šEš",
        "SO:KKE?",
        "PAK",
        "DAŋ¢Uŋ",
        "PIK",
        "HA:M",
    )

}

class PueblanorteRegionDataProvider : WordImageProvider {
    override val wordToImageMap: Map<String, Int> = mapOf(
        // Asocia palabras con imágenes para la región Sayula
        "JAMBRE" to R.drawable.img_ahorcado_puebla_abeja,
        "AHUACATL" to R.drawable.img_ahorcado_puebla_aguacate,
        "COSEMALOTL" to R.drawable.img_ahorcado_puebla_arcoiris,
        "TLAPECHTLI" to R.drawable.img_ahorcado_puebla_camas,
        "NELUICAC" to R.drawable.img_ahorcado_puebla_cielo,
        "YELOTL" to R.drawable.img_ahorcado_puebla_elote,
        "XOCHITL" to R.drawable.img_ahorcado_puebla_flor,
        "TIOPANTLI" to R.drawable.img_ahorcado_puebla_iglesia,
        "HUEXOLOTL" to R.drawable.img_ahorcado_puebla_pavo,
        "QUIMICHI" to R.drawable.img_ahorcado_puebla_raton,

        // Agrega más asociaciones según sea necesario para la región 2
    )

    override val gameWords: List<String> = listOf(
        // Palabras para la región Sayula
        "JAMBRE",
        "AHUACATL",
        "COSEMALOTL",
        "TLAPECHTLI",
        "NELUICAC",
        "YELOTL",
        "XOCHITL",
        "TIOPANTLI",
        "HUEXOLOTL",
        "QUIMICHI",
    )

}

class OaxacaIstmoDataProvider : WordImageProvider {
    override val wordToImageMap: Map<String, Int> = mapOf(
        // Asocia palabras con imágenes para la región Sayula
        "KWAAY" to R.drawable.img_ahorcado_itsmo_caballo,
        "TAAT" to R.drawable.img_ahorcado_itsmo_nopal,
        "ANCHUUXK" to R.drawable.img_ahorcado_itsmo_ajo,
        "TSIꞌI" to R.drawable.img_ahorcado_itsmo_calabaza,
        "POꞌO" to R.drawable.img_ahorcado_itsmo_luna,
        "JOK" to R.drawable.img_ahorcado_itsmo_nube,
        "OOMY" to R.drawable.img_ahorcado_itsmo_pelota,
        "PAAN" to R.drawable.img_ahorcado_itsmo_metate,
        "MAANK" to R.drawable.img_ahorcado_itsmo_mango,
        "JëëN" to R.drawable.img_ahorcado_itsmo_fuego,

        // Agrega más asociaciones según sea necesario para la región 2
    )

    override val gameWords: List<String> = listOf(
        // Palabras para la región Sayula
        "KWAAY",  //Caballo
        "TAAT", //Nopal
        "ANCHUUXK",  //Ajo
        "TSIꞌI",  //calabaza
        "POꞌO", //Luna
        "JOK", //Nube
        "OOMY", //Pelota
        "PAAN", //metate
        "MAANK", //mango
        "JëëN", //fuego
    )

}

// Agrega más clases para más regiones si es necesario

// Define la función getRegionDataProvider para obtener el proveedor de datos de la región
fun getRegionDataProvider(region: String): WordImageProvider {
    return when (region) {
        "Soteapan" -> SoteapanRegionDataProvider()
        "Sayula" -> SayulaRegionDataProvider()
        "Oluta" -> OlutaRegionDataProvider()
        "Texistepec" -> TexistepecRegionDataProvider()
        "San Gabriel Chilac" -> PueblanorteRegionDataProvider()
        "Ocotepec" -> OaxacaIstmoDataProvider()
        // Agrega más casos según lo necesites para otras regiones
        else -> throw IllegalArgumentException("Región no válida: $region")
    }
}
