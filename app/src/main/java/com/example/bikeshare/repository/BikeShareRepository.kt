package com.example.bikeshare.repository

import com.example.bikeshare.repository.interfaces.BikeShareRepositoryInterface
import com.google.gson.GsonBuilder
import java.net.URL
import com.example.bikeshare.interfaces.Result

class BikeShareRepository: BikeShareRepositoryInterface {
    override suspend fun getBikeShareCities(): Result<Response> {
        val url = URL("https://api.citybik.es/v2/networks")
        val connection = url.openConnection()
        connection.connect()
        val response = connection.getInputStream().bufferedReader().use { it.readText() }
        val gson = GsonBuilder().setPrettyPrinting().create()
        val model = gson.fromJson(response, Response::class.java)
        return Result.Success(model)
    }
}

data class Response (
    val networks: List<Network>
)

data class Network (
    val href: String,
    val id: String,
    val location: Location,
    val name: String,
    val source: String? = null,
    val gbfsHref: String? = null
)

data class Location (
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)
