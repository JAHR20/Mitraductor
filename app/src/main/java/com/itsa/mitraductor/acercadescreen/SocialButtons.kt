package com.itsa.mitraductor.acercadescreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.itsa.mitraductor.R

@Composable
fun SocialMediaButtons() {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialMediaButton(
            iconRes = R.drawable.facebook_icon,
            contentDescription = "Facebook",
            onClick = { openFacebookProfile(context, "profile.php?id=61562206343053&mibextid=ZbWKwL") }
        )
        SocialMediaButton(
            iconRes = R.drawable.twitter_icon,
            contentDescription = "Twitter",
            onClick = { openXProfile(context, "https://x.com/MichuyIanna5221?t=0aWGpzRafPdWEApbSyksuQ&s=08")}
        )
    }
}

fun openFacebookProfile(context: Context, profileId: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/$profileId")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Si no se puede abrir la aplicación de Facebook, abrir en el navegador
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/$profileId"))
        context.startActivity(Intent.createChooser(webIntent, "Abrir con"))
    }
}
fun openXProfile(context: Context, profileUrl: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        // Abre la aplicación de X (Twitter) si está instalada
        data = Uri.parse("twitter://user?screen_name=${Uri.parse(profileUrl).lastPathSegment}")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Si no se puede abrir la aplicación, abrir en el navegador
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl))
        context.startActivity(Intent.createChooser(webIntent, "Abrir con"))
    }
}



@Composable
fun SocialMediaButton(iconRes: Int, contentDescription: String, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(painter = painterResource(id = iconRes), contentDescription = contentDescription, tint = Color.Unspecified)
    }
}
fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    context.startActivity(Intent.createChooser(intent, "Abrir con"))
}