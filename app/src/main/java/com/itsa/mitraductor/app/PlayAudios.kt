package com.itsa.mitraductor.app

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import java.io.IOException

fun playAudio(audioFileName: String, context: Context) {
    val _audioToPlay = MutableLiveData<String>()

    var mediaPlayer: MediaPlayer? = null
    try {
        mediaPlayer?.release()
        val assetFileDescriptor = context.assets.openFd("audios/$audioFileName")
        MediaPlayer().apply {
            setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
            prepare()
            start()
        }
        _audioToPlay.value = audioFileName
    } catch (e: IOException) {
        e.printStackTrace()
    }
}