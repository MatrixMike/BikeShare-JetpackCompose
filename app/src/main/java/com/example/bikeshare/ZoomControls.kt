package com.example.bikeshare

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bikeshare.StackOrientation.VERTICAL
import com.example.bikeshare.StackOrientation.HORIZONTAL
import com.example.bikeshare.ZoomButton.Companion.ZoomIn
import com.example.bikeshare.ZoomButton.Companion.ZoomOut

@Composable
fun ZoomControls(
    orientation: StackOrientation = VERTICAL,
    zoom: Float,
    onZoomChanged: (Float) -> Unit
) {
    orientation.View {
        ZoomButtonStack(zoom = zoom, onZoomChanged = onZoomChanged)
    }
}

@Composable
private fun StackOrientation.View(content: @Composable () -> Unit)  {
    when(this) {
        VERTICAL -> {
            Column { content() }
        }
        HORIZONTAL -> {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                content()
            }
        }
    }
}

@LayoutScopeMarker
@Composable
private fun ZoomButtonStack(
    zoom: Float,
    onZoomChanged: (Float) -> Unit
) {
    ZoomOut {
        onZoomChanged(
            Zoom.OUT.calculate(zoom)
        )
    }
    ZoomIn {
        onZoomChanged(
            Zoom.IN.calculate(zoom)
        )
    }
}