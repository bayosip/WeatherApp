package com.nooro.weatherapp.entities.ui_models

data class WeatherResultsUIModel(
    val cityName: String?,
    val weatherIcon: String?,
    val tempC: Int = 0,
    val tempF:Int = 0,
)
