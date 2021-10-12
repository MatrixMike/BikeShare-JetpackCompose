package com.example.bikeshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.bikeshare.factory.TabFactory
import com.example.bikeshare.map.viewmodel.BikeShareMapViewModel
import com.example.bikeshare.map.MapScreen
import com.example.bikeshare.repository.MockBikeShareRepository

class MainActivity : ComponentActivity() {
    companion object {
        private val tabs = listOf(TabScreen.MAP)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabFactory.Create(tabs)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MapScreen.View(
        BikeShareMapViewModel(
            MockBikeShareRepository()
        )
    )
}

