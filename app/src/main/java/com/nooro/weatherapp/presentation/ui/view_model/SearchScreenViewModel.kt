package com.nooro.weatherapp.presentation.ui.view_model

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.nooro.weatherapp.domain.network.util.Util
import com.nooro.weatherapp.presentation.ui.state.MessageEvent
import com.nooro.weatherapp.presentation.ui.state.SearchScreenState
import com.nooro.weatherapp.usecases.RetrieveCitiesUseCase
import com.nooro.weatherapp.usecases.SearchAutoCompleteUseCase
import com.nooro.weatherapp.usecases.SearchWeatherResultsUseCase
import com.nooro.weatherapp.usecases.UpdateSavedCitiesUseCase
import com.silverorange.videoplayer.network.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val updateSavedCitiesUseCase: UpdateSavedCitiesUseCase,
    private val retrieveCitiesUseCase: RetrieveCitiesUseCase,
    private val searchAcUseCase: SearchAutoCompleteUseCase,
    private val searchWeatherResultsUseCase: SearchWeatherResultsUseCase,
) : ViewModel() {

    private val TAG = javaClass.canonicalName
    private val state: MutableState<SearchScreenState> = mutableStateOf(SearchScreenState())
    val _state: State<SearchScreenState> = state

    init {
        getStoredCities()
    }

    fun clearSearch(){
        state.value = _state.value.copy(citySearchResults = emptyList())
    }

    fun checkForInternet(context: Context) {
        viewModelScope.launch {
            val connected = Util.isInternetAvailable(context = context)
            state.value = _state.value.copy(
                isInternetConnected = connected,
                message = if (!connected) MessageEvent("No Connection Available") else null
            )
        }

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

    fun onSelect(city: String) {
        val list = _state.value.cityList
        if (list.size >= 5) {
            list.removeFirst()
        }
        list.add(city)
        viewModelScope.launch(Dispatchers.IO) {
            updateSavedCitiesUseCase(changeListToJsonString(list))
        }
    }


    fun getSearchForCity(city: String) {
        if (state.value.isInternetConnected) {
            viewModelScope.launch(Dispatchers.IO) {
                searchAcUseCase(city).collectLatest { result ->
                    when (result.status) {
                        Resource.STATUS.SUCCESS -> {
                            withContext(Dispatchers.Main) {
                                state.value = _state.value.copy(
                                    citySearchResults = result.data ?: emptyList(),
                                    isLoading = false,
                                )
                            }
                        }

                        Resource.STATUS.ERROR -> {
                            state.value = _state.value.copy(
                                isLoading = false,
                                message = MessageEvent(result.message)
                            )
                        }

                        Resource.STATUS.LOADING -> {
                            withContext(Dispatchers.Main) {
                                state.value = _state.value.copy(isLoading = true)
                            }
                        }
                    }
                }
            }
        }
    }

    fun getWeatherReportFor(city: String) {
        if (state.value.isInternetConnected) {
            viewModelScope.launch(Dispatchers.IO) {
                searchWeatherResultsUseCase(city).collectLatest { result ->
                    when (result.status) {
                        Resource.STATUS.SUCCESS -> {
                            withContext(Dispatchers.Main) {
                                state.value = _state.value.copy(
                                    weatherResultsUIModel = result.data,
                                    isLoading = false,
                                )
                            }
                        }

                        Resource.STATUS.ERROR -> {
                            state.value = _state.value.copy(
                                isLoading = false,
                                message = MessageEvent(result.message)
                            )
                        }

                        Resource.STATUS.LOADING -> {
                            withContext(Dispatchers.Main) {
                                state.value = _state.value.copy(isLoading = true)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getListFrom(stringSet: String): List<String> {
        val gson = Gson()
        val listWoSetType = object : TypeToken<List<String>>() {}.type
        val list: List<String> = gson.fromJson(stringSet, listWoSetType)
        return list
    }

    private fun changeListToJsonString(list: List<String>): String {
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gsonPretty.toJson(list)
        return jsonString
    }
}