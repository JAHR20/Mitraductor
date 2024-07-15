package com.itsa.mitraductor.acercadescreen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun EmailLink(email: String) {
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(
            tag = "URL",
            annotation = "mailto:$email"
        )
        withStyle(
            style = SpanStyle(color = Color.Blue, fontSize = 16.sp)
        ) {
            append(email)
        }
        pop()
    }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle the result if needed
    }
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse(annotation.item)
                    }
                    context.startActivity(intent)
                }
        }
    )
}