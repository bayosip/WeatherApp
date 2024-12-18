package com.nooro.weatherapp.presentation.ui.screens.Home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nooro.weatherapp.R
import com.nooro.weatherapp.presentation.ui.common.H_Space
import com.nooro.weatherapp.presentation.ui.common.V_MultiStyleText
import com.nooro.weatherapp.presentation.ui.state.HomeScreenState
import com.nooro.weatherapp.presentation.ui.theme.Dimens
import com.nooro.weatherapp.presentation.ui.theme.WeatherTypography
import com.nooro.weatherapp.presentation.ui.theme.text_color_light
import com.nooro.weatherapp.presentation.ui.theme.text_color_normal

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeWeatherScreen(
    state: State<HomeScreenState>,
    getWeather: () -> Unit,
    messageBus: (String) -> Unit,
) {
    val data = state.value.weatherUpdates
    state.value.message?.getContentIfNotHandled()?.let {
        messageBus(it)
    }
    if (state.value.cityList.isNotEmpty()){
        getWeather()
    }
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            data.size
        }
    )

    if (state.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(Dimens.grid_4)
                    .align(Alignment.Center)
            )
        }
    } else {
        if (data.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                V_MultiStyleText(
                    text1 = stringResource(R.string.no_city_selected),
                    text2 = stringResource(R.string.please_select_city),
                    modifier = Modifier.wrapContentSize(),
                    fontSize1 = 30.sp,
                    fontSize2 = 15.sp,
                    style = WeatherTypography.displayMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = Dimens.grid_3)

            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { pageIndex ->
                    CityWeatherScreen(weatherUIModel = data[pageIndex])
                }

                if (data.size > 1) {
                    DotsIndicator(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = Dimens.grid_0_5),
                        totalDots = data.size,
                        selectedIndex = pagerState.currentPage
                    )
                }
            }

            LaunchedEffect(key1 = state.value.cityList) {
                getWeather()
            }
        }
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = Color.Black,
    unSelectedColor: Color = text_color_light,
) {

    LazyRow(
        modifier = modifier
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(Dimens.grid_1_5)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(Dimens.grid_1)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Dimens.grid_0_25.H_Space()
            }
        }
    }
}