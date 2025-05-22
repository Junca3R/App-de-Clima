package com.example.climaapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climaapp.repository.WeatherRepository

class WheatherviewModelFactory(
    private val repository: WeatherRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WheatherviewModel::class.java)) {
            return WheatherviewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
