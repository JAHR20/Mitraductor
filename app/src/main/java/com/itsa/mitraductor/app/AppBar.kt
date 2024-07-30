package com.itsa.mitraductor.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.itsa.mitraductor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithBackButton(
    title: String,
    navController: NavController
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                IconButton(
                    onClick = {
                        // Utiliza el NavController para navegar hacia atrás
                        navController.navigateUp()
                    }

                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",tint = Color.White)
                }
                // Ajusta el espacio según tu preferencia
                Text(text = title,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
