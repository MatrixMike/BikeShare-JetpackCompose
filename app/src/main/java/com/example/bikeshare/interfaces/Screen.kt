package com.example.bikeshare.interfaces

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

interface Screen<viewModel: ViewModel> {
    @Composable
    fun View(viewModel: viewModel)
}