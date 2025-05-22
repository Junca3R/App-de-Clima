package com.example.climaapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // URL base de la API de OpenWeatherMap, usada para todas las peticiones
    private const val BASE_URL = "https://api.openweathermap.org/"

    // Instancia de Retrofit creada una sola vez (lazy) para hacer las peticiones HTTP
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Configuramos la URL base para Retrofit
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor para transformar JSON a objetos Kotlin
            .build()
    }

    // Servicio de Retrofit para definir los endpoints y métodos HTTP (GET, POST, etc.)
    val api: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
}
