package com.example.bikeshare.map.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bikeshare.repository.BikeShareRepository
import com.example.bikeshare.repository.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.bikeshare.interfaces.Result
import com.example.bikeshare.repository.interfaces.BikeShareRepositoryInterface
import com.google.android.libraries.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Marker(val name: String, val latLng: LatLng)

class BikeShareMapViewModel(private val repository: BikeShareRepositoryInterface): ViewModel() {
    sealed class State {
        object Loading: State()
        data class CityDataLoaded(val markers: List<Marker>): State()
    }

    private var _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getBikeShareCities()
            }
            when (result) {
                is Result.Success<Response> -> {
                    _state.emit(State.CityDataLoaded(
                        markers = result.data.networks.map {
                            Marker(name = it.location.city, latLng = LatLng(it.location.latitude, it.location.longitude))
                        }
                    ))
                }
                else -> Log.d("view model", "error")
            }
        }
    }
}

class BikeShareViewModelFactory(private val repository: BikeShareRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = BikeShareMapViewModel(repository) as T
}