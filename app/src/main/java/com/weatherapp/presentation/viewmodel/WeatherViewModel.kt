package com.weatherapp.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.network.model.WeatherResponse
import com.weatherapp.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    var weatherResponse = mutableStateOf<WeatherResponse?>(null)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)

    fun getWeatherByCity(cityName: String, context: Context) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                weatherResponse.value = repository.getWeatherByCity(cityName)
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = "Failed to fetch weather data."
                showToast(context, "Error: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
    }

    fun getWeatherForCurrentLocation(lat: Double, lon: Double, context: Context) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                weatherResponse.value = repository.getWeatherByCoordinates(lat, lon)
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = "Failed to fetch weather data for location."
                showToast(context, "Error: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
