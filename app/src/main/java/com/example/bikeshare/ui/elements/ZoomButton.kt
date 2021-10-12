package com.example.bikeshare.ui.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val Minus = "-"
private const val Plus = "+"

@Composable
fun ZoomOut(colors: ButtonColors = defaultColors(), onClick: () -> Unit) {
    ZoomButton(colors = colors, text = Minus, onClick = onClick)
}

@Composable
fun ZoomIn(colors: ButtonColors = defaultColors(), onClick: () -> Unit) {
    ZoomButton(colors = colors, text = Plus, onClick = onClick)
}

@Composable
private fun defaultColors(): ButtonColors =
    ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary
    )

@Composable
private fun ZoomButton(colors: ButtonColors, text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(8.dp),
        colors = colors,
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.h5)
    }
}
