package com.example.bikeshare.factory

import androidx.compose.runtime.Composable
import com.example.bikeshare.map.viewmodel.BikeShareMapViewModel
import com.example.bikeshare.repository.BikeShareRepository
import com.example.bikeshare.TabScreen
import com.example.bikeshare.TabScreen.*
import com.example.bikeshare.map.MapScreen
import com.example.bikeshare.map.viewmodel.BikeShareViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel

class TabFactory {
    companion object {
        private val repo = BikeShareRepository()
        private lateinit var bikeShareMapViewModel: BikeShareMapViewModel

        @Composable
        fun Create(tabs: List<TabScreen>) {
            tabs.forEach {
                when(it) {
                    MAP -> {
                        bikeShareMapViewModel = viewModel(factory = BikeShareViewModelFactory(repo) )
                        MapScreen.View(bikeShareMapViewModel)
                    }
//                    LIST -> {
//
//                    }
                }
            }
        }
    }
}