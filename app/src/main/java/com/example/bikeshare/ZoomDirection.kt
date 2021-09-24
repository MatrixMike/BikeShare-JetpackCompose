package com.example.bikeshare

private const val ZoomPercentage = 0.2f
private const val ZoomOutBy = 1 - (1 * ZoomPercentage)
private const val ZoomInBy = 1 + (1 * ZoomPercentage)


enum class Zoom(private val zoomPercentage: Float) {
    OUT(ZoomOutBy),
    IN(ZoomInBy);

    fun calculate(zoom: Float): Float {
        return zoom * zoomPercentage
    }
}