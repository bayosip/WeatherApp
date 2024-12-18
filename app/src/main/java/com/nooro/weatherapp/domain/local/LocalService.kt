package com.nooro.weatherapp.domain.local

import kotlinx.coroutines.flow.Flow

interface LocalService {
    suspend fun saveCity(
        city: String?
    )

    fun getCity(): Flow<String?>
}