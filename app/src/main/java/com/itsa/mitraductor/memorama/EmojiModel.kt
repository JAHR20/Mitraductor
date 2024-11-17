package com.itsa.mitraductor.memorama

import java.util.*

data class ImageModel(
    var imageResId: Int,
    val audioFileName: String,
    var isVisible: Boolean = true,
    var isSelect: Boolean = false,
    var id: String = UUID.randomUUID().toString(),
)