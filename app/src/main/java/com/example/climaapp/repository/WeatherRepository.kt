package com.example.climaapp.repository

import com.example.climaapp.model.WeatherResponse
import com.example.climaapp.network.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    // Creamos la instancia Retrofit para conectarnos con la API
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")  // URL base de la API
        .addConverterFactory(GsonConverterFactory.create())   // Para convertir JSON a objetos Kotlin
        .build()

    // Creamos el servicio para hacer las peticiones con Retrofit
    private val weatherService = retrofit.create(WeatherService::class.java)

    // Función pública para obtener el clima por ciudad
    suspend fun getWeatherByCity(city: String, apiKey: String, units: String): WeatherResponse {
        // Llamamos al método de la interfaz que hicimos con Retrofit
        return weatherService.getWeatherByCity(city, apiKey, units)
    }
}
