package com.example.bikeshare.repository

import com.example.bikeshare.repository.interfaces.BikeShareRepositoryInterface
import com.example.bikeshare.interfaces.Result

class MockBikeShareRepository: BikeShareRepositoryInterface {
    override suspend fun getBikeShareCities(): Result<Response> {
        TODO("Not yet implemented")
    }
}