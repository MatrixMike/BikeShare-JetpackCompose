package com.example.bikeshare

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.bikeshare.factory.TabFactory
import com.example.bikeshare.map.viewmodel.BikeShareMapViewModel
import com.example.bikeshare.map.MapScreen
import com.example.bikeshare.repository.BikeShareRepository

const val MinZoom = 2f
const val MaxZoom = 20f

class MainActivity : ComponentActivity() {
    companion object {
        private val tabs = listOf(TabScreen.MAP)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabFactory.Create(tabs)
        }.run {
            Log.d("howdy", "cheers")
            /// repo.getBikeShareCities()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MapScreen.View(BikeShareMapViewModel(BikeShareRepository()))
}

