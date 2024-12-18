package com.nooro.weatherapp.domain.model

import com.nooro.weatherapp.entities.data.CitySearchResults
import com.nooro.weatherapp.entities.data.WeatherSearchResults
import com.nooro.weatherapp.entities.ui_models.CitySuggestionUIModel
import com.nooro.weatherapp.entities.ui_models.FullWeatherUIModel
import com.nooro.weatherapp.entities.ui_models.WeatherResultsUIModel

class DataToUIMapper constructor() {

    fun dataToWeatherResultsUIModel(data: WeatherSearchResults): WeatherResultsUIModel =
        with(data) {
            WeatherResultsUIModel(
                cityName = location.name,
                weatherIcon = current.condition.icon,
                tempC = current.roundedC ?: 0,
                tempF = current.roundedF ?: 0,
            )
        }

    fun citySearchResults(data: CitySearchResults): CitySuggestionUIModel = with(data) {
        CitySuggestionUIModel(
            cityName = name,
            cityRegion = region,
            cityCountry = country
        )
    }

    fun dataToFullWeatherUIModel(data: WeatherSearchResults): FullWeatherUIModel = with(data){
        FullWeatherUIModel(
            cityName = location.name,
            region = location.region,
            weatherIcon = current.condition.icon,
            tempC = current.roundedC ?: 0,
            tempF = current.roundedF ?: 0,
            feelsLikeC = current.roundedFeels_C ?: 0,
            feelsLikeF = current.roundedFeels_F?: 0,
            humidity = data.current.humidity ?: 0
        )
    }
}