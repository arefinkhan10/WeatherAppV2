package com.weatherapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.weatherapp.presentation.viewmodel.WeatherViewModel
import com.weatherapp.presentation.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity is the entry point for the weather app's user interface.
 *
 * This class is responsible for setting up the UI using Jetpack Compose and managing
 * location permission requests to fetch the current weather based on the user's location.
 *
 * It uses Hilt for dependency injection to inject the WeatherViewModel, and a permission
 * launcher to request location permissions.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // WeatherViewModel is injected using Hilt and viewModels() delegate
    private val weatherViewModel: WeatherViewModel by viewModels()

    // Launcher for handling location permission requests
    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // If permission is granted, fetch current location and get weather data
                fetchCurrentLocationAndGetWeather()
            } else {
                // Handle the case where permission is denied
            }
        }

    /**
     * Called when the activity is starting. Sets up the content of the app.
     *
     * Uses Jetpack Compose to set the UI and passes the WeatherViewModel and permission launcher
     * to the WeatherScreen composable. The context is also passed for actions such as showing a toast.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // Surface is used as the root container for the UI, setting the background color
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    WeatherScreen(
                        weatherViewModel = weatherViewModel,
                        requestLocationPermissionLauncher = { permission ->
                            requestLocationPermissionLauncher.launch(permission)  // Launch permission request
                        },
                        context = this  // Pass context for additional tasks like showing toast messages
                    )
                }
            }
        }
    }

    /**
     * Fetches the current location and triggers the view model to get weather data.
     *
     * This method is a placeholder where the location fetching logic should be implemented.
     * After fetching the latitude and longitude, it calls the view model's method to fetch
     * the weather data for the current location.
     */
    private fun fetchCurrentLocationAndGetWeather() {
        // Example coordinates for demonstration; replace with actual location fetching logic
        val lat = 12.34  // Example latitude
        val lon = 56.78  // Example longitude
        weatherViewModel.getWeatherForCurrentLocation(lat, lon, this)
    }
}
