package com.example.climaapp.model

data class WeatherResponse(
    val name: String,// nombre de la ciudad
    val main : Main,
    val weather : List<Weather>
)

data class Main(
    val temp: Double,//temperatura actual
    val temp_max : Double,// temperatura maxima
    val temp_min : Double,// temperatura minima

)

data class Weather(
    val main: String,//Descripción general (Clear, Rain, )
    val description : String// Descripción más detallada
)