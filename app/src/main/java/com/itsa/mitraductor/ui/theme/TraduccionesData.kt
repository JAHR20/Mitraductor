package com.itsa.mitraductor.ui.theme

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TraduccionesData(@SerialName("traducciones") val traducciones: Map<String, String>)