package com.weatherapp.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.repo.WeatherRepository
import com.weatherapp.data.network.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * WeatherViewModel manages the weather data fetching logic and UI state.
 *
 * This ViewModel interacts with the WeatherRepository to fetch weather data either by city name
 * or geographic coordinates (latitude and longitude). It uses Kotlin Coroutines to handle
 * asynchronous network calls and updates the UI state.
 *
 * @param repository The WeatherRepository is injected via Hilt for accessing weather data.
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    // State to hold the weather response fetched from the repository
    var weatherResponse = mutableStateOf<WeatherResponse?>(null)
        private set

    // State to track the loading status during data fetching
    var isLoading = mutableStateOf(false)
        private set

    // State to hold any error message that occurs during fetching
    var errorMessage = mutableStateOf<String?>(null)
        private set

    /**
     * Fetches weather data by the given city name.
     *
     * This method triggers a network call to fetch weather data for the provided city name.
     * If an error occurs, it updates the error message state and shows a toast message.
     *
     * @param cityName the name of the city to fetch the weather data for.
     * @param context the context used to show a toast in case of error.
     */
    fun getWeatherByCity(cityName: String, context: Context) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Fetch weather data from the repository
                weatherResponse.value = repository.getWeatherByCity(cityName)
                errorMessage.value = null
            } catch (e: Exception) {
                // Handle error and show error toast
                errorMessage.value = "Failed to fetch weather data."
                showToast(context, "Error: ${e.message}")
            } finally {
                // Set loading state to false after completion
                isLoading.value = false
            }
        }
    }

    /**
     * Fetches weather data for the current location based on latitude and longitude.
     *
     * This method triggers a network call to fetch weather data using geographic coordinates.
     * If an error occurs, it updates the error message state and shows a toast message.
     *
     * @param lat the latitude of the current location.
     * @param lon the longitude of the current location.
     * @param context the context used to show a toast in case of error.
     */
    fun getWeatherForCurrentLocation(lat: Double, lon: Double, context: Context) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Fetch weather data from the repository using coordinates
                weatherResponse.value = repository.getWeatherByCoordinates(lat, lon)
                errorMessage.value = null
            } catch (e: Exception) {
                // Handle error and show error toast
                errorMessage.value = "Failed to fetch weather data for location."
                showToast(context, "Error: ${e.message}")
            } finally {
                // Set loading state to false after completion
                isLoading.value = false
            }
        }
    }

    /**
     * Displays a toast message.
     *
     * This method is used to show a toast message in case of an error or other situations where
     * the user needs feedback.
     *
     * @param context the context used to show the toast.
     * @param message the message to be shown in the toast.
     */
    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
