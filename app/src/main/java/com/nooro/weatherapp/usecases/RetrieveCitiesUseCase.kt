package com.nooro.weatherapp.usecases

import com.nooro.weatherapp.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveCitiesUseCase @Inject constructor(
     private val repo: Repository,
) {
    suspend operator fun invoke(): Flow<String?> = repo.getSavedCities()
}