package com.example.bikeshare

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ZoomButton {
    companion object {
        @Composable
        fun ZoomOut(colors: ButtonColors = defaultColors(), onClick: () -> Unit) {
            ZoomButton(colors = colors, text = "-", onClick = onClick)
        }

        @Composable
        fun ZoomIn(colors: ButtonColors = defaultColors(), onClick: () -> Unit) {
            ZoomButton(colors = colors, text = "+", onClick = onClick)
        }

        @Composable
        private fun defaultColors(): ButtonColors {
            return ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.onPrimary,
                contentColor = MaterialTheme.colors.primary
            )
        }

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
    }
}