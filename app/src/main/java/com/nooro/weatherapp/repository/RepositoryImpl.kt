package com.nooro.weatherapp.repository

import android.util.Log
import com.nooro.weatherapp.domain.local.LocalService
import com.nooro.weatherapp.domain.model.DataToUIMapper
import com.nooro.weatherapp.domain.network.ApiService
import com.nooro.weatherapp.domain.network.util.BaseDataSource
import com.nooro.weatherapp.entities.ui_models.CitySuggestionUIModel
import com.nooro.weatherapp.entities.ui_models.FullWeatherUIModel
import com.silverorange.videoplayer.network.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: DataToUIMapper,
    private val localService: LocalService,
) : Repository, BaseDataSource() {


    override suspend fun searchForCitiesWithName(city: String):
            Flow<Resource<List<CitySuggestionUIModel>>> = flow {
        emit(Resource.loading())
        val response = getResult {
            apiService.searchForCity( city)
        }
        when (response.status) {
            Resource.STATUS.SUCCESS -> {
                val result = response.data?.map { data ->
                    mapper.citySearchResults(data)
                }

                if (result != null) {
                    emit(Resource.success(result))
                } else emit(Resource.error(message = "No Data..."))
            }

            Resource.STATUS.ERROR -> {
                Log.d("TAG", "getSearchResults: request error")
                emit(Resource.error(message = "Request Error..."))
            }

            Resource.STATUS.LOADING -> emit(Resource.loading())
        }

    }

    override suspend fun searchForCityWeatherReport(city: String):
            Flow<Resource<FullWeatherUIModel>> = flow {
        emit(Resource.loading())
        val response = getResult {
            apiService.searchForWeatherReportByCity(city)
        }
        when (response.status) {
            Resource.STATUS.SUCCESS -> {
                val result = response.data?.let { mapper.dataToFullWeatherUIModel(it) }

                if (result != null) {
                    emit(Resource.success(result))
                } else emit(Resource.error(message = "No Data..."))
            }

            Resource.STATUS.ERROR -> {
                Log.d("TAG", "getSearchResults: request error")
                emit(Resource.error(message = "Request Error..."))
            }

            Resource.STATUS.LOADING -> emit(Resource.loading())
        }
    }

    override suspend fun getSavedCities(): Flow<String?> = localService.getCity()

    override suspend fun updateSavedCities(update: String) {
        localService.saveCity(update)
    }
}