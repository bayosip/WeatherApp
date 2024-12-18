package com.nooro.weatherapp.domain.network

import com.nooro.weatherapp.entities.data.CitySearchResults
import com.nooro.weatherapp.entities.data.WeatherSearchResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //path variable substitution for the api endpoint .
    // in the path we change the "user" with the string user we get from getUser Method
    // parsing the last parameter or url --> String user

    @GET("search.json")
    suspend fun searchForCity(
        @Query("q") city: String,
    ): Response<List<CitySearchResults>?>

    @GET("current.json")
    suspend fun searchForWeatherReportByCity(
        @Query("q") city: String,
    ): Response<WeatherSearchResults?>
}