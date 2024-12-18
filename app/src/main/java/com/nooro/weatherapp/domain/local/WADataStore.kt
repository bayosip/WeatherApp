package com.nooro.weatherapp.domain.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "DSTORE:"

@Singleton
class WaDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun <T> save(key: Preferences.Key<T>, data: T?) {
        dataStore.edit { preferences ->
            if (data == null) {
                preferences.remove(key)
            } else {
                preferences[key] = data
                Log.d(TAG, preferences[key].toString())
            }
        }
    }

    fun <T> get(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    fun <T> isKeyStored(key: Preferences.Key<T>): Flow<Boolean> =
        dataStore.data.map { preference ->
            preference.contains(key)
        }
}