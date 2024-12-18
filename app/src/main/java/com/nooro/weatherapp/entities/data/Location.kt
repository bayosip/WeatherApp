package com.nooro.weatherapp.entities.data

import androidx.annotation.Keep

@Keep
data class Location(
    val name: String?,
    val region: String?,
    val country:String?,
    val lat: Float?,
    val lon: Float?,
    val tz_id: String?,
    val localtime: String?,
)
