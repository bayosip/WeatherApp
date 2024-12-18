package com.nooro.weatherapp.presentation.ui.state

import com.nooro.weatherapp.entities.ui_models.FullWeatherUIModel

data class HomeScreenState(
    val isInternetConnected:Boolean = true,
    val isLoading: Boolean = false,
    val weatherUpdates: List<FullWeatherUIModel> = emptyList(),
    val cityList: ArrayDeque<String> =  ArrayDeque(),
    val savedData: String? = null,
    val message: MessageEvent<String>? = null
)
