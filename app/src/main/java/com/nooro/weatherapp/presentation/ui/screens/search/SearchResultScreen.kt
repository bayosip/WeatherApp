package com.nooro.weatherapp.presentation.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.nooro.weatherapp.R
import com.nooro.weatherapp.presentation.ui.common.V_Space
import com.nooro.weatherapp.presentation.ui.common.WeatherResults
import com.nooro.weatherapp.presentation.ui.state.SearchScreenState
import com.nooro.weatherapp.presentation.ui.theme.Dimens
import com.nooro.weatherapp.presentation.ui.theme.WeatherTypography
import com.nooro.weatherapp.presentation.ui.theme.text_color_normal


@Composable
fun SearchResultScreen(
    state: State<SearchScreenState>,
    onSelect: (String) -> Unit,
    messageBus: (String) -> Unit,
) {
    val data = state.value.weatherResultsUIModel
    state.value.message?.getContentIfNotHandled()?.let {
        messageBus(it)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = Dimens.grid_4, horizontal = Dimens.grid_2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (data != null) {
            WeatherResults(data){ city ->
                onSelect(city)
            }
        } else {
            Dimens.grid_4.V_Space()
            Text(
                text = stringResource(R.string.no_results),
                style = WeatherTypography.displayMedium.copy(
                    color = text_color_normal,
                    fontSize = 32.sp
                )
            )
        }

    }
}