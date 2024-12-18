package com.nooro.weatherapp.usecases

import com.nooro.weatherapp.repository.Repository
import javax.inject.Inject

class UpdateSavedCitiesUseCase@Inject constructor(
    private val repo: Repository,
) {
    suspend operator fun invoke(update: String) {
        repo.updateSavedCities(update)
    }
}