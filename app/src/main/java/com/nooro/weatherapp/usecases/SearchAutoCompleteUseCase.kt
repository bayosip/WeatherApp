package com.nooro.weatherapp.usecases

import com.nooro.weatherapp.entities.ui_models.CitySuggestionUIModel
import com.nooro.weatherapp.repository.Repository
import com.silverorange.videoplayer.network.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAutoCompleteUseCase @Inject constructor(
    private val repo: Repository,
) {
    suspend operator fun invoke(city:String): Flow<Resource<List<CitySuggestionUIModel>?>> =
        repo.searchForCitiesWithName(city)
}