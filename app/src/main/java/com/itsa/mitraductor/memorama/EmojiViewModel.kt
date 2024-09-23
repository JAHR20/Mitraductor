package com.itsa.mitraductor.memorama

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itsa.mitraductor.R
import com.itsa.mitraductor.traductorscreems.quitarAcentos
import java.io.IOException

class EmojiViewModel : ViewModel() {
    private val _audioToPlay = MutableLiveData<String>()
    val audioToPlay: LiveData<String> = _audioToPlay

    private var mediaPlayer: MediaPlayer? = null



    // Se invoca cuando se libera la instancia del ViewModel
    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release() // Liberar el reproductor de audio
        mediaPlayer = null
    }

    private val images: MutableLiveData<MutableList<ImageModel>> by lazy {
        MutableLiveData<MutableList<ImageModel>>()
    }

    private val _allCardsMatched = MutableLiveData<Boolean>(false)
    val allCardsMatched: LiveData<Boolean>
        get() = _allCardsMatched

    private fun checkAllCardsMatched() {
        if (images.value?.all { it.isVisible == false } == true) {
            _allCardsMatched.value = true
        }
    }

    fun resetAllCardsMatched() {
        _allCardsMatched.value = false
    }

    fun getImages(): LiveData<MutableList<ImageModel>> {
        return images
    }

    fun loadImages(region: String, subcategory: String) {
        val regionFormateada = quitarAcentos(region.lowercase().replace(" ", "_"))
        val pue="pueblanahuatl/region_$regionFormateada"
        val oax="oaxacamixe/region_$regionFormateada"
        val ver="veracruzpopoluca/region_$regionFormateada"

        val imageSetsByRegion = mapOf(
            "Soteapan" to mapOf(
                "Familia" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_abuela, "${ver}/Abuela.mp3"),
                    ImageModel(R.drawable.img_memorama_mujer, "${ver}/Mujer.mp3"),
                    ImageModel(R.drawable.img_memorama_tia, "${ver}/Tia.mp3"),
                    ImageModel(R.drawable.img_memorama_abuelo, "${ver}/Abuelo.mp3"),
                    ImageModel(R.drawable.img_memorama_ninia, "${ver}/Niña.mp3"),
                    ImageModel(R.drawable.img_memorama_ninio, "${ver}/Niño.mp3"),
                    ImageModel(R.drawable.img_memorama_tio, "${ver}/Tio.mp3"),
                    ImageModel(R.drawable.img_memorama_hombre, "${ver}/Hombre.mp3"),
                    ImageModel(R.drawable.img_memorama_abuela, "${ver}/Abuela.mp3"),
                    ImageModel(R.drawable.img_memorama_mujer, "${ver}/Mujer.mp3"),
                    ImageModel(R.drawable.img_memorama_tia, "${ver}/Tia.mp3"),
                    ImageModel(R.drawable.img_memorama_abuelo, "${ver}/Abuelo.mp3"),
                    ImageModel(R.drawable.img_memorama_ninia, "${ver}/Niña.mp3"),
                    ImageModel(R.drawable.img_memorama_ninio, "${ver}/Niño.mp3"),
                    ImageModel(R.drawable.img_memorama_tio, "${ver}/Tio.mp3"),
                    ImageModel(R.drawable.img_memorama_hombre, "${ver}/Hombre.mp3")
                    // Agrega las imágenes para la región 1...
                ),
                "Animales" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_soteapan_cocodrilo, "${ver}/Cocodrilo.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_conejo, "${ver}/Conejo.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_paloma, "${ver}/Paloma.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_perro, "${ver}/Perro.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_pollo, "${ver}/Pollo.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_raton, "${ver}/Raton.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_tortuga, "${ver}/Tortuga.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_tucan, "${ver}/Tucan.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_cocodrilo, "${ver}/Cocodrilo.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_conejo, "${ver}/Conejo.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_paloma, "${ver}/Paloma.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_perro, "${ver}/Perro.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_pollo, "${ver}/Pollo.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_raton, "${ver}/Raton.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_tortuga, "${ver}/Tortuga.mp3"),
                    ImageModel(R.drawable.img_memorama_soteapan_tucan, "${ver}/Tucan.mp3"),
                    // ... elementos de la subcategoría Animales
                )
            ),
            "Sayula" to mapOf(
                "Familia" to mutableListOf(

                ),
                "Animales" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_sayula_camaron, "${ver}/Camaron.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_mosquito, "${ver}/Mosquito.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_pajaro, "${ver}/Pajaro.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_pescado, "${ver}/Pescado.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_puerco, "${ver}/Puerco.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_armadillo, "${ver}/Armadillo.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_caballo, "${ver}/Caballo.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_burro, "${ver}/Burro.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_camaron, "${ver}/Camaron.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_mosquito, "${ver}/Mosquito.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_pajaro, "${ver}/Pajaro.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_pescado, "${ver}/Pescado.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_puerco, "${ver}/Puerco.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_armadillo, "${ver}/Armadillo.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_caballo, "${ver}/Caballo.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_burro, "${ver}/Burro.mp3"),
                    // Agrega las imágenes para la región 2...
                ),
                "Frutas" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_sayula_naranja,"palabra_Naranja_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_guanabana,"palabra_Guanabana_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_platano,"palabra_Platano_regionsayula.mp3"),

                    ImageModel(R.drawable.img_memorama_sayula_naranja,"palabra_Naranja_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_guanabana,"palabra_Guanabana_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_platano,"palabra_Platano_regionsayula.mp3"),
                )
            ),
            "Oluta" to mapOf(
                "Familia" to mutableListOf(

                ),
                "Frutas" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_oluta_jaula, ""),
                    ImageModel(R.drawable.img_memorama_oluta_lapiz, ""),
                    ImageModel(R.drawable.img_memorama_oluta_machete, ""),
                    ImageModel(R.drawable.img_memorama_oluta_sandia, ""),
                    ImageModel(R.drawable.img_memorama_oluta_sombrero, ""),
                    ImageModel(R.drawable.img_memorama_oluta_tijeras, ""),
                    ImageModel(R.drawable.img_memorama_oluta_tomate, ""),
                    ImageModel(R.drawable.img_memorama_oluta_jaula, ""),
                    ImageModel(R.drawable.img_memorama_oluta_lapiz, ""),
                    ImageModel(R.drawable.img_memorama_oluta_machete, ""),
                    ImageModel(R.drawable.img_memorama_oluta_sandia, ""),
                    ImageModel(R.drawable.img_memorama_oluta_sombrero, ""),
                    ImageModel(R.drawable.img_memorama_oluta_tijeras, ""),
                    ImageModel(R.drawable.img_memorama_oluta_tomate, ""),
                ),
                "Animales" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_oluta_arania, ""),
                    ImageModel(R.drawable.img_memorama_oluta_ardilla, ""),
                    ImageModel(R.drawable.img_memorama_oluta_armadillo, "${ver}/Armadillo.mp3"),
                    ImageModel(R.drawable.img_memorama_oluta_caballo, "${ver}/Caballo.mp3"),
                    ImageModel(R.drawable.img_memorama_oluta_iguana, "${ver}/Iguana.mp3"),
                    ImageModel(R.drawable.img_memorama_oluta_venado, ""),
                    ImageModel(R.drawable.img_memorama_oluta_mapache, ""),
                    ImageModel(R.drawable.img_memorama_oluta_colibri, ""),
                    ImageModel(R.drawable.img_memorama_oluta_arania, ""),
                    ImageModel(R.drawable.img_memorama_oluta_ardilla, ""),
                    ImageModel(R.drawable.img_memorama_oluta_armadillo, "${ver}/Armadillo.mp3"),
                    ImageModel(R.drawable.img_memorama_oluta_caballo, "${ver}/Caballo.mp3"),
                    ImageModel(R.drawable.img_memorama_oluta_iguana, "${ver}/Iguana.mp3"),
                    ImageModel(R.drawable.img_memorama_oluta_venado, ""),
                    ImageModel(R.drawable.img_memorama_oluta_mapache, ""),
                    ImageModel(R.drawable.img_memorama_oluta_colibri, ""),

                )
            ),
            "Texistepec" to mapOf(
                "Familia" to mutableListOf(

                ),
                "Animales" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_texistepec_arbol, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_caracol, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_carne, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_collar, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_hueso, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_iguana, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_pluma, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_sol, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_arbol, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_caracol, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_carne, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_collar, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_hueso, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_iguana, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_pluma, ""),
                    ImageModel(R.drawable.img_memorama_texistepec_sol, ""),
                    // Agrega las imágenes para la región 2...
                )
            ),
            "San Gabriel Chilac" to mapOf(
                "Familia" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_abuela, "${pue}/Abuela.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_abuelo, "${pue}/Abuelo.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_hermana, "${pue}/Hermana.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_hermano, "${pue}/Hermano.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_hombre, "${pue}/Hombre.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_mujer, "${pue}/Mujer.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_papa, "${pue}/Papá.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_mama, "${pue}/Mamá.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_abuela, "${pue}/Abuela.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_abuelo, "${pue}/Abuelo.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_hermana, "${pue}/Hermana.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_hermano, "${pue}/Hermano.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_hombre, "${pue}/Hombre.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_mujer, "${pue}/Mujer.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_papa, "${pue}/Papá.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_mama, "${pue}/Mamá.mp3"),
                ),
                "Animales" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_lagartija, "${pue}/Lagartija.mp3"),//
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_raton, "${pue}/Ratón.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_pavo, "${pue}/Pavo.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabriel_alacran, "${pue}/Alacrán.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_rana, "${pue}/Rana.mp3"),//
                    ImageModel(R.drawable.img_memorama_sangabriel_pescado, "${pue}/Pescado.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_tecolote, "${pue}/Tecolote.mp3"),//
                    ImageModel(R.drawable.img_memorama_sangabriel_puerco, "${pue}/Puerco.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_lagartija, "${pue}/Lagartija.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_raton, "${pue}/Ratón.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_pavo, "${pue}/Pavo.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabriel_alacran, "${pue}/Alacrán.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_rana, "${pue}/Rana.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabriel_pescado, "${pue}/Pescado.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabrielchilac_tecolote, "${pue}/Tecolote.mp3"),
                    ImageModel(R.drawable.img_memorama_sangabriel_puerco, "${pue}/Puerco.mp3"),
                )
            ),
            "Ocotepec" to mapOf(
                "Familia" to mutableListOf(
                    
                ),
                "Animales" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_caballo, "${oax}/Oaxaca.mp3"),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_nopal, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_ajo, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_calabaza, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_luna, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_nube, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_metate, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_mango, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_caballo, "${oax}/Oaxaca.mp3"),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_nopal, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_ajo, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_calabaza, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_luna, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_nube, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_metate, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_mango, ""),
                )
            )
            // Agrega más regiones según sea necesario...
        )
        images.value = imageSetsByRegion[region]?.get(subcategory)?.apply { shuffle() }
        resetAllCardsMatched()
    }



    fun updateShowVisibleCard(id: String, region: String,subcategory: String) {
        val selects: List<ImageModel>? = images.value?.filter { it -> it.isSelect }
        val selectCount: Int = selects?.size ?: 0
        var imageFind: Int = 0;

        if (selectCount >= 2) {

            val hasSameImage: Boolean = selects!!.get(0).imageResId == selects.get(1).imageResId
            if (hasSameImage) {
                imageFind = selects.get(0).imageResId
                selects.get(0).audioFileName?.let { _audioToPlay.postValue(it) }
            }
        }

        val list: MutableList<ImageModel>? = images.value?.map { it ->
            if (selectCount >= 2) {
                it.isSelect = false
            }

            if (it.imageResId == imageFind) {
                it.isVisible = false
            }

            if (it.id == id) {
                it.isSelect = true
            }

            it
        } as MutableList<ImageModel>?

        val visibleCount: Int = list?.filter { it -> it.isVisible }?.size ?: 0
        if (visibleCount <= 0) {
            // Verificar si todas las cartas están emparejadas
            if (images.value?.all { !it.isVisible } == true) {
                _allCardsMatched.value = true
                return
            } else {
                // Si no todas las cartas están emparejadas, cargar nuevas cartas
                loadImages(region,subcategory)
                return
            }
        }

        images.value?.removeAll { true }
        images.value = list

    }

    fun playAudio(audioFileName: String, context: Context) {
        try {
            // Detener y liberar el reproductor de audio anterior, si existe
            mediaPlayer?.release()

            val assetFileDescriptor = context.assets.openFd("audios/$audioFileName")
            mediaPlayer = MediaPlayer().apply {
                setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
                prepare()
                start()
            }
            // Asignar el nombre del archivo de audio actual para observar en la vista
            _audioToPlay.value = audioFileName
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}