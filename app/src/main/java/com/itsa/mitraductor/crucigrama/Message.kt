package com.itsa.mitraductor.crucigrama

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun MessageWithFormat(message: String) {
    Text(
        fontSize = 20.sp,
        text = message,
        style = MaterialTheme.typography.titleSmall,
        color = Color(0xFF1F54AF)
    )
}

