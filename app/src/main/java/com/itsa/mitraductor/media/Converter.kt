package com.itsa.mitraductor.media

import com.itsa.mitraductor.deviceDensity


//Converters so that objects appear to be of same size in all the devices.
object Converter {

    fun convertPixelsToDp( pixels: Int) =
        (pixels / deviceDensity).toFloat()

    fun convertDpToPixels( dp: Float) =
        (dp * deviceDensity)
}