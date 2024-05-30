package com.itsa.mitraductor.ui.theme

import java.util.*

class ImageModel(
    var imageResId: Int,
    val audioFileName: String,
    var isVisible: Boolean = true,
    var isSelect: Boolean = false,
    var id: String = UUID.randomUUID().toString(),
) {}