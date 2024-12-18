package com.nooro.weatherapp.entities.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CitySearchResults(
    @SerializedName("id")
    val id:Long,
    val name: String?,
    val region: String?,
    val country:String?,
    val lat: Float?,
    val lon: Float?,
    val url: String?,
)
