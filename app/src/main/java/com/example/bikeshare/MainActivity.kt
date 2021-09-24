package com.example.bikeshare

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private const val InitialZoom = 5f

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapScreen()
        }
    }
}

@Composable
fun MapScreen() {
    // The MapView lifecycle is handled by this composable. As the MapView also needs to be updated
    // with input from Compose UI, those updates are encapsulated into the MapViewContainer
    // composable. In this way, when an update to the MapView happens, this composable won't
    // recompose and the MapView won't need to be recreated.
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(mapView, -37.840935, 144.946457)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MapScreen()
}

@Composable
private fun MapViewContainer(
    map: MapView,
    latitude: Double,
    longitude: Double
) {
    val cameraPosition = remember(latitude, longitude) {
        LatLng(latitude, longitude)
    }

//    // Always called when zooming
//    // Need to fix
//    val coroutineScope = rememberCoroutineScope()
//    coroutineScope.launch {
//        val result = BikeShareRepository().getBikeShareCities()
//        result.run {
//            when(this) {
//                is Result.Success<Response> -> Log.d("hell ya", data.networks.size.toString())
//                else -> Log.d("fuck", "damn")
//            }
//        }
//    }

    LaunchedEffect(map) {
        val googleMap = map.awaitMap()
        googleMap.addMarker { position(cameraPosition) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
    }

    var zoom by rememberSaveable(map) { mutableStateOf(InitialZoom) }

    BikeShareMap(map = map, cameraPosition = cameraPosition, zoom = { zoom })

    MapButtonColumn(zoom = zoom, onZoomChanged = { zoom = it.coerceIn(MinZoom, MaxZoom) })
}

@Composable
private fun BikeShareMap(map: MapView, cameraPosition: LatLng, zoom: () -> Float) {
    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        // Reading zoom so that AndroidView recomposes when it changes. The getMapAsync lambda
        // is stored for later, Compose doesn't recognize state reads
        val mapZoom = zoom()
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            googleMap.setZoom(mapZoom)
            // Move camera to the same place to trigger the zoom update
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
        }
    }
}

@Composable
private fun MapButtonColumn(zoom: Float, onZoomChanged: (Float) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        ZoomControls(
            orientation = StackOrientation.HORIZONTAL,
            zoom = zoom,
            onZoomChanged = onZoomChanged
        )
    }
}

const val MinZoom = 2f
const val MaxZoom = 20f