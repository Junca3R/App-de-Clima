package com.example.climaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climaapp.model.WeatherResponse
import com.example.climaapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WheatherviewModel(private val repository: WeatherRepository): ViewModel() {

    // Estado interno mutable para los datos del clima, inicialmente nulo
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    // Estado público inmutable para exponer los datos del clima a la UI
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    // Estado para controlar si la carga de datos está en proceso
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Estado para manejar mensajes de error y mostrarlos en la UI
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Función para obtener el clima de una ciudad específica
    fun getWeather(city: String) {
        // Lanzamos una corrutina en el scope del ViewModel para tareas asíncronas
        viewModelScope.launch {
            _isLoading.value = true   // Indicamos que empezó la carga
            _errorMessage.value = null // Limpiamos errores previos
            try {
                // Llamamos al repositorio para obtener el clima, pasando la ciudad, API key y unidades
                val result = repository.getWeatherByCity(
                    city,
                    apiKey = "c053ee90315d7f94cd5f10208f8f579b",
                    units = "metric"
                )
                // Guardamos el resultado en el estado para que la UI se actualice
                _weatherData.value = result
            } catch (e: Exception) {
                // Si hay error, lo guardamos en el estado para mostrarlo en la UI
                _errorMessage.value = "Error al obtener el clima: ${e.message ?: "Desconocido"}"
                _weatherData.value = null
            } finally {
                // Indicamos que la carga terminó (exitosamente o con error)
                _isLoading.value = false
            }
        }
    }
}
