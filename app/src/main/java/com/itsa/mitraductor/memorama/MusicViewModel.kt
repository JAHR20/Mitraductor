package com.itsa.mitraductor.memorama

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.itsa.mitraductor.R

class MusicViewModel(lifecycle: Lifecycle) : ViewModel(), LifecycleObserver {
    private var mediaPlayer: MediaPlayer? = null

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        mediaPlayer?.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        mediaPlayer?.pause()
    }

    fun playMusic(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.music_memorama_bajo).apply {
                isLooping = true // Esto hará que la música se repita
                setVolume(0.35f, 0.35f)
                start() // Iniciar la música
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release() // Liberar los recursos cuando ya no se necesite el ViewModel
    }
}



