package com.nooro.weatherapp.domain.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nooro.weatherapp.domain.local.LocalService
import com.nooro.weatherapp.domain.local.LocalServiceImpl
import com.nooro.weatherapp.domain.local.WaDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule{

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.datastore
    }

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "nooro-data")

    @Singleton
    @Provides
    fun provideLocalDatasource(dataStore: WaDataStore): LocalService= LocalServiceImpl(dataStore)
}