package com.example.bikeshare.map

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.bikeshare.*
import com.example.bikeshare.interfaces.Screen
import com.example.bikeshare.map.viewmodel.BikeShareMapViewModel
import com.example.bikeshare.ui.elements.ZoomControls
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch

private const val InitialZoom = 5f

class MapScreen {
    companion object: Screen<BikeShareMapViewModel> {
        @Composable
        override fun View(viewModel: BikeShareMapViewModel) {
            // The MapView lifecycle is handled by this composable. As the MapView also needs to be updated
            // with input from Compose UI, those updates are encapsulated into the MapViewContainer
            // composable. In this way, when an update to the MapView happens, this composable won't
            // recompose and the MapView won't need to be recreated.
            val mapView = rememberMapViewWithLifecycle()
            MapViewContainer(mapView, -37.840935, 144.946457, viewModel = viewModel)
        }

        @Composable
        private fun MapViewContainer(
            map: MapView,
            latitude: Double,
            longitude: Double,
            viewModel: BikeShareMapViewModel
        ) {
            val cameraPosition = remember(latitude, longitude) {
                LatLng(latitude, longitude)
            }

            when (val state = viewModel.state.collectAsState().value) {
                BikeShareMapViewModel.State.Loading -> {

                }
                is BikeShareMapViewModel.State.CityDataLoaded -> {
                    map.getMapAsync { googleMap ->
                        state.markers.forEach {
                            googleMap.addMarker {
                                position(it.latLng)
                                title(it.name)
                            }
                        }
                    }
                }
            }

            LaunchedEffect(map) {
                val googleMap = map.awaitMap()
                googleMap.addMarker { position(cameraPosition) }
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
            }

            var zoom by rememberSaveable(map) { mutableStateOf(InitialZoom) }

            BikeShareMap(
                map = map,
                cameraPosition = cameraPosition,
                zoom = { zoom }
            )

            MapButtonColumn(
                zoom = zoom,
                onZoomChanged = { zoom = it.coerceIn(MinZoom, MaxZoom) }
            )
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
    }
}