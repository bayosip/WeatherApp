package com.nooro.weatherapp.usecases

import com.nooro.weatherapp.domain.model.DataToUIMapper
import com.nooro.weatherapp.entities.ui_models.FullWeatherUIModel
import com.nooro.weatherapp.repository.Repository
import com.silverorange.videoplayer.network.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchWeatherResultsUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(
        city: String,
    ): Flow<Resource<FullWeatherUIModel?>> = repository.searchForCityWeatherReport(city)
}