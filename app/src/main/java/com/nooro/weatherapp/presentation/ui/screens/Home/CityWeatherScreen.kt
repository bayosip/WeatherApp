package com.nooro.weatherapp.presentation.ui.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nooro.weatherapp.R
import com.nooro.weatherapp.entities.ui_models.FullWeatherUIModel
import com.nooro.weatherapp.presentation.ui.common.H_Space
import com.nooro.weatherapp.presentation.ui.common.V_MultiStyleText
import com.nooro.weatherapp.presentation.ui.common.V_Space
import com.nooro.weatherapp.presentation.ui.theme.Dimens
import com.nooro.weatherapp.presentation.ui.theme.WeatherTypography
import com.nooro.weatherapp.presentation.ui.theme.text_color_faint
import com.nooro.weatherapp.presentation.ui.theme.text_color_light
import com.nooro.weatherapp.presentation.ui.theme.tile_color


@Composable
fun CityWeatherScreen(
    weatherUIModel: FullWeatherUIModel,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AsyncImage(
            model = "https:${weatherUIModel.weatherIcon}",
            contentDescription = "weather_icon",
            modifier = Modifier
                .width(Dimens.grid_15_5)
                .height(Dimens.grid_14)
        )

        Dimens.grid_3.V_Space()

        V_MultiStyleText(
            text1 = weatherUIModel.cityName ?: "",
            text2 = stringResource(R.string.degrees, weatherUIModel.tempC),
            showIcon1 = true,
            modifier = Modifier.wrapContentSize(),
            style = WeatherTypography.displayMedium
        )

        Row (modifier = Modifier.fillMaxWidth(0.8f)
            .height(Dimens.grid_9_5)
            .background(color = tile_color, shape = RoundedCornerShape(Dimens.grid_2)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.aligned(Alignment.CenterHorizontally)){
            V_MultiStyleText(
                text1 = stringResource(R.string.humidity) ,
                text2 = stringResource(R.string.percentage, weatherUIModel.humidity),
                modifier = Modifier.wrapContentSize(),
                style = WeatherTypography.displayMedium,
                fontSize1 = 12.sp,
                fontSize2 = 15.sp,
                color1 = text_color_faint,
                color2 = text_color_light
            )

            Dimens.grid_6.H_Space()

            V_MultiStyleText(
                text1 = stringResource(R.string.uv),
                text2 = "${weatherUIModel.uv}",
                modifier = Modifier.wrapContentSize(),
                style = WeatherTypography.displayMedium,
                fontSize1 = 12.sp,
                fontSize2 = 15.sp,
                color1 = text_color_faint,
                color2 = text_color_light
            )

            Dimens.grid_6.H_Space()

            V_MultiStyleText(
                text1 = stringResource(R.string.feels_like),
                text2 = "${weatherUIModel.feelsLikeC}",
                modifier = Modifier.wrapContentSize(),
                style = WeatherTypography.displayMedium,
                fontSize1 = 12.sp,
                fontSize2 = 15.sp,
                color1 = text_color_faint,
                color2 = text_color_light,
            )
        }
    }
}