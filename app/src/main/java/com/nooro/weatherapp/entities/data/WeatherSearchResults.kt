package com.nooro.weatherapp.entities.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherSearchResults(
    @SerializedName("location")
    val location: Location,
    @SerializedName("current")
    val current: Current,
)
