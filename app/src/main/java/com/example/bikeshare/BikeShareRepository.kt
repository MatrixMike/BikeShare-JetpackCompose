package com.example.bikeshare

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class BikeShareRepository {
    suspend fun getBikeShareCities(): Result<Response> {
        withContext(Dispatchers.IO) {
            val url = URL("https://api.citybik.es/v2/networks")
            (url.openConnection() as? HttpURLConnection)?.run {
                requestMethod = "GET"
                setRequestProperty("Content-Type", "application/json; utf-8")
                setRequestProperty("Accept", "application/json")
                doInput = true
                doOutput = false

                val response = inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Default) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser().parse(response))
                    Log.d("Pretty Printed JSON :", prettyJson)
                    val model = gson.fromJson(response, Response::class.java)

                    Log.d("parsed", model.networks[0].name)
                    return@withContext Result.Success(model)
                }
            }
        }
        return Result.Error(Exception("shit fuck"))
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
    val gbfsHref: String? = null,
    val license: License? = null
)

data class License (
    val name: Name,
    val url: String
)

enum class Name {
    DataLicenceGermanyAttributionVersion20,
    OGLV3License,
    OpenDataCommonsOpenDatabaseLicense10ODBL,
    OpenLicence
}

data class Location (
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)
