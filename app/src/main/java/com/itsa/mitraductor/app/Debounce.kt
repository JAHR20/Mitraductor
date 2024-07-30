package com.itsa.mitraductor.app

import kotlinx.coroutines.*

private val coroutineScope = CoroutineScope(Dispatchers.Main)
private var debounceJob: Job? = null

fun debounce(delayMillis: Long, action: () -> Unit) {
    debounceJob?.cancel()
    debounceJob = coroutineScope.launch {
        delay(delayMillis)
        action()
    }
}
