package com.example.bikeshare.repository.interfaces

import com.example.bikeshare.interfaces.Result
import com.example.bikeshare.repository.Response

interface BikeShareRepositoryInterface {
    suspend fun getBikeShareCities(): Result<Response>
}
