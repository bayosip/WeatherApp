package com.nooro.weatherapp.entities.data

import androidx.annotation.Keep
import kotlin.math.roundToInt

@Keep
data class Current(
    val last_updated: String?,
    val temp_c: Float?,
    val temp_f: Float?,
    val condition: Condition,
    val wind_dir: String?,
    val humidity: Int?,
    val uv: String?,
    val feelslike_c: Float?,
    val feelslike_f: Float?
) {

    val roundedC: Int?
        get() = temp_c?.roundToInt()

    val roundedF: Int?
        get() = temp_f?.roundToInt()

    val roundedFeels_C: Int?
        get() = feelslike_c?.roundToInt()

    val roundedFeels_F: Int?
        get() = feelslike_f?.roundToInt()

    data class Condition(
        val text: String?,
        val icon: String?,
        val code: Long?,
    )
}
