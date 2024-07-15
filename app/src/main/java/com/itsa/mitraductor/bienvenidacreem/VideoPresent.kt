package com.itsa.mitraductor.bienvenidacreem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun VideoPlayerWithLoop(
    videoResId: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds,
    lifecycle: Lifecycle // Agrega este parámetro
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/$videoResId")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ALL // Reproducir en ciclo continuo
        }
    }

    // Agrega este bloque de código
    DisposableEffect(lifecycle) {
        val lifecycleObserver = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                exoPlayer.playWhenReady = true
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                exoPlayer.playWhenReady = false
            }
        }

        lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { PlayerView(context).apply {
            player = exoPlayer
            useController = false } },
        modifier = modifier,
        update = { view ->
            view.onResume()
        }
    )
}