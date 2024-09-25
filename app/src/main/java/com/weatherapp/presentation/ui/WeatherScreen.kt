package com.weatherapp.presentation.ui

import android.Manifest
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.weatherapp.presentation.viewmodel.WeatherViewModel

/**
 * WeatherScreen is the main UI component that displays weather information and handles user input.
 *
 * This composable function takes the WeatherViewModel for managing the weather data,
 * a launcher for requesting location permissions, and the context for performing actions
 * such as showing toast messages.
 *
 * It allows the user to either search for weather by city name or request weather based on their current location.
 */
@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel,
    requestLocationPermissionLauncher: (String) -> Unit,
    context: Context // Pass the context for showing toasts or other context-dependent tasks
) {
    // State to track the user input for the city name
    var cityName by remember { mutableStateOf("") }

    // Observe changes in weather data, loading state, and any error messages from the ViewModel
    val weatherResponse by weatherViewModel.weatherResponse
    val isLoading by weatherViewModel.isLoading
    val errorMessage by weatherViewModel.errorMessage

    // Column layout for arranging the UI components vertically
    Column(modifier = Modifier.padding(16.dp)) {
        // TextField for user to input the city name
        TextField(
            value = cityName,
            onValueChange = { newCityName ->
                cityName = newCityName
            },
            label = { Text("Enter city") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Button to fetch weather data by city name
        Button(onClick = {
            if (cityName.isNotEmpty()) {
                weatherViewModel.getWeatherByCity(cityName, context) // Call ViewModel method to fetch weather
            }
        }) {
            Text("Get Weather by City")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Button to request location permission and fetch weather for current location
        Button(onClick = {
            requestLocationPermissionLauncher(Manifest.permission.ACCESS_FINE_LOCATION)
        }) {
            Text("Use Current Location")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display loading indicator while the weather data is being fetched
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            // Display error message if there's an error
            errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            // Display weather data once it has been successfully fetched
            weatherResponse?.let { weather ->
                Text(text = "Temperature: ${weather.main.temp} °C")
                weather.weather.firstOrNull()?.icon?.let { icon ->
                    // Display the weather icon using Coil image loader
                    Image(
                        painter = rememberImagePainter("http://openweathermap.org/img/w/$icon.png"),
                        contentDescription = "Weather icon"
                    )
                }
            }
        }
    }
}
