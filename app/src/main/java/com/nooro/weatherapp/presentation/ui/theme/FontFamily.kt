package com.nooro.weatherapp.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.nooro.weatherapp.R

// Define and load the fonts of the app

private val pRegular = Font(R.font.poppins_regular, FontWeight.Normal)
private val pMedium = Font(R.font.poppins_medium, FontWeight.Medium)

// Create a font family to use in TextStyles
val waFontFamily = FontFamily(pRegular, pMedium)

// Use the font family to define a custom typography
val WeatherTypography = Typography(
    displayMedium = TextStyle(
        fontFamily = waFontFamily,
    )
)