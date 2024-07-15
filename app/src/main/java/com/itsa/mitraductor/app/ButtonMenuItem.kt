package com.itsa.mitraductor.app

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun BottomMenuItem(
    @DrawableRes iconRes: Int,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.tertiary else Color.White
    val textColor = if (isSelected) MaterialTheme.colorScheme.onSecondary else LocalContentColor.current

    Box(
        modifier = modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .background(backgroundColor, RoundedCornerShape(16.dp))
    ) {
        // Botón con la animación
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(28.dp),
                tint = Color.Unspecified
                //tint = textColor
            )

            val scrollState = rememberScrollState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
            ) {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                    color = textColor,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            LaunchedEffect(scrollState) {
                delay(2000) // Espera 2 segundos antes de iniciar la animación
                while (true) {
                    scrollState.animateScrollTo(scrollState.maxValue, tween(6000, easing = LinearEasing))
                    delay(3000)
                    scrollState.animateScrollTo(0, tween(1))
                }
            }
        }

        // Botón transparente encima
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() } // Maneja los eventos de clic
                .background(Color.Transparent) // Hace que el botón sea transparente
        )
    }

}