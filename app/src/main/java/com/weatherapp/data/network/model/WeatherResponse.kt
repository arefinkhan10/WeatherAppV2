package com.weatherapp.data.network.model

import com.squareup.moshi.Json

/**
 * WeatherResponse represents the structure of the weather data
 * returned by the weather API.
 *
 * This data class uses Moshi annotations to map JSON fields to Kotlin properties.
 *
 * @property main contains temperature information.
 * @property weather contains a list of weather conditions, including icons.
 */
data class WeatherResponse(
    @Json(name = "main") val main: Main,
    @Json(name = "weather") val weather: List<Weather>
) {
    /**
     * Main represents the main weather information such as temperature.
     *
     * @property temp stores the current temperature.
     */
    data class Main(
        @Json(name = "temp") val temp: Double
    )

    /**
     * Weather represents specific weather conditions such as the icon for the current weather state.
     *
     * @property icon stores the icon code representing the weather condition.
     */
    data class Weather(
        @Json(name = "icon") val icon: String
    )
}
