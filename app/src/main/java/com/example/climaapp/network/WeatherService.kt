package com.example.climaapp.network

import com.example.climaapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface WeatherService{
    @GET ("data/2.5/weather")//Le decimos a Retrofit que vamos a hacer una petición GET a esa ruta del API
     suspend fun getWeatherByCity(
        @Query ("q")city: String,//ciudad que queremos consultar
        @Query ("appid") apikey: String,
        @Query("units") units: String = "metric",// para mostrar grados Celsius
        @Query("lang") lang: String = "es" // para que la descripción venga en español
    ): WeatherResponse
}