package com.nooro.weatherapp.domain.local

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

class LocalServiceImpl constructor(
    private val dataStore: WaDataStore
): LocalService {

    override suspend fun saveCity(city: String?) {

        city?.let { dataStore.save(CITY_KEY, it) }
    }

    override fun getCity(): Flow<String?> = dataStore.get(CITY_KEY)

    companion object{
        private val CITY_KEY = stringPreferencesKey("city")
    }
}