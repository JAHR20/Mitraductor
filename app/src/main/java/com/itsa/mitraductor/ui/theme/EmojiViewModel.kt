package com.itsa.mitraductor.ui.theme

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itsa.mitraductor.R
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
        val imageSetsByRegion = mapOf(
            "Soteapan" to mapOf(
                "Familia" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_abuela, "palabra_Abuela_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_mujer, "palabra_Mujer_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_tia, "palabra_Tia_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_abuelo, "palabra_Abuelo_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_ninia, "palabra_Niña_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_ninio, "palabra_Niño_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_tio, "palabra_Tio_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_hombre, "palabra_Hombre_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_abuela, "palabra_Abuela_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_mujer, "palabra_Mujer_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_tia, "palabra_Tia_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_abuelo, "palabra_Abuelo_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_ninia, "palabra_Niña_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_ninio, "palabra_Niño_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_tio, "palabra_Tio_regionsoteapan.mp3"),
                    ImageModel(R.drawable.img_memorama_hombre, "palabra_Hombre_regionsoteapan.mp3")
                    // Agrega las imágenes para la región 1...
                ),
                "Animales" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_soteapan_cocodrilo, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_conejo, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_paloma, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_perro, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_pollo, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_raton, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_tortuga, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_tucan, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_cocodrilo, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_conejo, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_paloma, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_perro, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_pollo, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_raton, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_tortuga, ""),
                    ImageModel(R.drawable.img_memorama_soteapan_tucan, ""),
                    // ... elementos de la subcategoría Animales
                )
            ),
            "Sayula" to mapOf(
                "Familia" to mutableListOf(

                ),
                "Animales" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_sayula_camaron, "palabra_Camaron_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_mosquito, "palabra_Mosquito_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_pajaro, "palabra_Pajaro_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_pescado, "palabra_Pescado_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_puerco, "palabra_Puerco_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_armadillo, "palabra_Puerco_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_caballo, "palabra_Puerco_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_burro, "palabra_Puerco_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_camaron, "palabra_Camaron_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_mosquito, "palabra_Mosquito_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_pajaro, "palabra_Pajaro_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_pescado, "palabra_Pescado_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_puerco, "palabra_Puerco_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_armadillo, "palabra_Puerco_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_caballo, "palabra_Puerco_regionsayula.mp3"),
                    ImageModel(R.drawable.img_memorama_sayula_burro, "palabra_Puerco_regionsayula.mp3"),
                    // Agrega las imágenes para la región 2...
                ),
                "Frutas" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_sayula_naranja,""),
                    ImageModel(R.drawable.img_memorama_sayula_guanabana,""),
                    ImageModel(R.drawable.img_memorama_sayula_platano,""),

                    ImageModel(R.drawable.img_memorama_sayula_naranja,""),
                    ImageModel(R.drawable.img_memorama_sayula_guanabana,""),
                    ImageModel(R.drawable.img_memorama_sayula_platano,""),
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
                    ImageModel(R.drawable.img_memorama_oluta_armadillo, ""),
                    ImageModel(R.drawable.img_memorama_oluta_caballo, ""),
                    ImageModel(R.drawable.img_memorama_oluta_iguana, ""),
                    ImageModel(R.drawable.img_memorama_oluta_venado, ""),
                    ImageModel(R.drawable.img_memorama_oluta_mapache, ""),
                    ImageModel(R.drawable.img_memorama_oluta_colibri, ""),
                    ImageModel(R.drawable.img_memorama_oluta_arania, ""),
                    ImageModel(R.drawable.img_memorama_oluta_ardilla, ""),
                    ImageModel(R.drawable.img_memorama_oluta_armadillo, ""),
                    ImageModel(R.drawable.img_memorama_oluta_caballo, ""),
                    ImageModel(R.drawable.img_memorama_oluta_iguana, ""),
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

                ),
                "Animales" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_pueblanorte_abeja, ""),
                    ImageModel(R.drawable.img_memorama_pueblanorte_raton, ""),
                    ImageModel(R.drawable.img_memorama_pueblanorte_pavo, ""),
                    ImageModel(R.drawable.img_memorama_sangabriel_alacran, ""),
                    ImageModel(R.drawable.img_memorama_sangabriel_murcielago, ""),
                    ImageModel(R.drawable.img_memorama_sangabriel_pescado, ""),
                    ImageModel(R.drawable.img_memorama_sangabriel_zorrillo, ""),
                    ImageModel(R.drawable.img_memorama_sangabriel_puerco, ""),
                    ImageModel(R.drawable.img_memorama_pueblanorte_abeja, ""),
                    ImageModel(R.drawable.img_memorama_pueblanorte_raton, ""),
                    ImageModel(R.drawable.img_memorama_pueblanorte_pavo, ""),
                    ImageModel(R.drawable.img_memorama_sangabriel_alacran, ""),
                    ImageModel(R.drawable.img_memorama_sangabriel_murcielago, ""),
                    ImageModel(R.drawable.img_memorama_sangabriel_pescado, ""),
                    ImageModel(R.drawable.img_memorama_sangabriel_zorrillo, ""),
                    ImageModel(R.drawable.img_memorama_sangabriel_puerco, ""),
                )
            ),
            "Ocotepec" to mapOf(
                "Familia" to mutableListOf(
                    
                ),
                "Animales" to mutableListOf(
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_caballo, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_nopal, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_ajo, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_calabaza, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_luna, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_nube, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_metate, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_mango, ""),
                    ImageModel(R.drawable.img_memorama_oaxacaitsmo_caballo, ""),
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