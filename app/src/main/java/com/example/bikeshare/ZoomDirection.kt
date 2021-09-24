package com.example.bikeshare

private const val ZoomPercentage = 0.2f
private const val ActualPercent = 1 * ZoomPercentage
private const val ZoomOutBy = 1 - ActualPercent
private const val ZoomInBy = 1 + ActualPercent


enum class Zoom(private val zoomPercentage: Float) {
    OUT(ZoomOutBy),
    IN(ZoomInBy);

    fun calculate(zoom: Float): Float {
        return zoom * zoomPercentage
    }
}