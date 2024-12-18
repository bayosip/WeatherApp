package com.nooro.weatherapp.entities.ui_models

import androidx.annotation.Keep

@Keep
data class FullWeatherUIModel(
    val cityName: String?,
    val region: String?,
    val weatherIcon: String?,
    val tempC: Int = 0,
    val tempF:Int = 0,
    val humidity: Int = 0,
    val uv: Int = 0,
    val feelsLikeC: Int = 0,
    val feelsLikeF: Int = 0,
)
