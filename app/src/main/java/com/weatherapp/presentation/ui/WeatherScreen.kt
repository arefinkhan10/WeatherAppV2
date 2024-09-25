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
import com.weatherapp.domain.utilities.Const
import com.weatherapp.domain.utilities.SharedPreferencesManager

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel,
    requestLocationPermissionLauncher: (String) -> Unit,
    context: Context
) {
    // State to hold the city name input
    var cityName by remember { mutableStateOf("") }

    // Observe ViewModel's weather data, loading, and error states
    val weatherResponse by weatherViewModel.weatherResponse
    val isLoading by weatherViewModel.isLoading
    val errorMessage by weatherViewModel.errorMessage

    // Load last entered city when the composable is first displayed
    LaunchedEffect(Unit) {
        val sharedPreferencesUtil = SharedPreferencesManager.getInstance(context)
        val lastEnteredCity = sharedPreferencesUtil.getString(Const.SHARED_PREF_CITY_KEY)
        if (lastEnteredCity != null) {
            cityName = lastEnteredCity
            weatherViewModel.getWeatherByCity(lastEnteredCity, context)  // Trigger weather fetching
        }
    }

    // Column layout for UI components
    Column(modifier = Modifier.padding(16.dp)) {
        // TextField for user to input the city name
        TextField(
            value = cityName,
            onValueChange = { newCityName -> cityName = newCityName },
            label = { Text("Enter city") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Button to fetch weather data by city name
        Button(onClick = {
            if (cityName.isNotEmpty()) {
                weatherViewModel.getWeatherByCity(cityName, context)
            }
        }) {
            Text("Get Weather by City")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Button to request location permission and fetch weather for the current location
        Button(onClick = {
            requestLocationPermissionLauncher(Manifest.permission.ACCESS_FINE_LOCATION)
        }) {
            Text("Use Current Location")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Loading indicator while fetching data
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            // Display error message if any
            errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            // Display weather data
            weatherResponse?.let { weather ->
                Text(text = "City: ${weather.name}")
                Text(text = "Temperature: ${weather.main?.temp ?: "N/A"} °F")
                Text(text = "Humidity: ${weather.main?.humidity ?: "N/A"} %")
                Text(text = "Weather: ${weather.weather?.firstOrNull()?.description ?: "N/A"}")
                weather.weather?.firstOrNull()?.icon?.let { icon ->
                    Image(
                        painter = rememberImagePainter("http://openweathermap.org/img/w/$icon.png"),
                        contentDescription = "Weather icon"
                    )
                }
            }
        }
    }
}
