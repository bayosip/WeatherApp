package com.nooro.weatherapp.repository

import com.nooro.weatherapp.entities.data.WeatherSearchResults
import com.nooro.weatherapp.entities.ui_models.CitySuggestionUIModel
import com.nooro.weatherapp.entities.ui_models.FullWeatherUIModel
import com.silverorange.videoplayer.network.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun searchForCitiesWithName(city: String): Flow<Resource<List<CitySuggestionUIModel>>>
    suspend fun searchForCityWeatherReport(city: String): Flow<Resource<FullWeatherUIModel>>
    suspend fun getSavedCities(): Flow<String?>
    suspend fun updateSavedCities(update:String)
}