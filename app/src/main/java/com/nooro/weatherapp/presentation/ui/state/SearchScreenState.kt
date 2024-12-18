package com.nooro.weatherapp.presentation.ui.state

import com.nooro.weatherapp.entities.ui_models.CitySuggestionUIModel
import com.nooro.weatherapp.entities.ui_models.FullWeatherUIModel

data class SearchScreenState(
    val isInternetConnected: Boolean = true,
    val isLoading: Boolean = false,
    val citySearchResults: List<CitySuggestionUIModel> = emptyList(),
    val cityList: ArrayDeque<String> =  ArrayDeque(),
    val weatherResultsUIModel: FullWeatherUIModel? = null,
    val message: MessageEvent<String>? = null
)