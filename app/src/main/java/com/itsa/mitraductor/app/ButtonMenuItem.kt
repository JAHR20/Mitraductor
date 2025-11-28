package com.itsa.mitraductor.app

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee // <--- LA IMPORTACIÓN MÁGICA
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomMenuItem(
    @DrawableRes iconRes: Int,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.tertiary else Color.White
    val textColor =if (isSelected) Color.White else Color.Black

    Box(
        modifier = modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .background(backgroundColor, RoundedCornerShape(16.dp))
            // Movemos el click aquí para simplificar, o puedes dejar el Box transparente si prefieres
            .clickable { onClick() }
    ) {
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
            )

            // Eliminamos el Box extra y el scrollState manual
            Text(
                text = text,
                fontSize = 14.sp,
                maxLines = 1,
                color = textColor,
                // AQUÍ ESTÁ LA MAGIA:
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .basicMarquee(
                        iterations = Int.MAX_VALUE, // Repetir por siempre
                        velocity = 30.dp, // Velocidad (ajusta si lo quieres más rápido)
                        delayMillis = 2000 // Espera 2 seg antes de empezar
                    )
            )
        }
    }
}