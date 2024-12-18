package com.nooro.weatherapp.presentation.ui.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nooro.weatherapp.entities.ui_models.FullWeatherUIModel
import com.nooro.weatherapp.presentation.ui.state.HomeScreenState
import com.nooro.weatherapp.usecases.RetrieveCitiesUseCase
import com.nooro.weatherapp.usecases.SearchWeatherResultsUseCase
import com.silverorange.videoplayer.network.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val retrieveCitiesUseCase: RetrieveCitiesUseCase,
    private val searchWeatherResultsUseCase: SearchWeatherResultsUseCase,
) : ViewModel() {

    private val state: MutableState<HomeScreenState> = mutableStateOf(HomeScreenState())
    val _state: State<HomeScreenState> = state

    init {
        getStoredCities()
    }

    private fun getStoredCities() {
        viewModelScope.launch(Dispatchers.IO) {
            retrieveCitiesUseCase().collectLatest { data ->
                if (data != null) {
                    withContext(Dispatchers.Main) {
                        state.value = _state.value.copy(cityList = ArrayDeque(getListFrom(data)))
                    }
                }
            }
        }
    }

    fun getWeatherReportForSavedCities() {
        viewModelScope.launch(Dispatchers.IO) {
            val report = mutableListOf<FullWeatherUIModel>()
            val data: Flow<List<FullWeatherUIModel>> = channelFlow {
                _state.value.cityList.forEach { city ->
                    searchWeatherResultsUseCase(city).collectLatest { result ->
                        when (result.status) {
                            Resource.STATUS.SUCCESS -> {
                                result.data?.let { report.add(it) }
                            }
                            else -> {}
                        }
                    }
                }
                send(report)
            }
            data.collectLatest {
                withContext(Dispatchers.Main){
                    state.value = _state.value.copy(weatherUpdates = report)
                }
            }
        }
    }

    private fun getListFrom(stringSet: String): List<String> {
        val gson = Gson()
        val listCities = object : TypeToken<List<String>>() {}.type
        val list: List<String> = gson.fromJson(stringSet, listCities)
        return list
    }
}