package com.nooro.weatherapp.presentation.ui.navigation_graph

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.nooro.weatherapp.R
import com.nooro.weatherapp.entities.ui_models.CitySuggestionUIModel
import com.nooro.weatherapp.presentation.ui.common.SearchTextField
import com.nooro.weatherapp.presentation.ui.common.onClick
import com.nooro.weatherapp.presentation.ui.screens.ScreenNames
import com.nooro.weatherapp.presentation.ui.state.SearchScreenState
import com.nooro.weatherapp.presentation.ui.theme.Dimens
import com.nooro.weatherapp.presentation.ui.theme.WeatherTypography
import com.nooro.weatherapp.presentation.ui.theme.text_color_normal


@Composable
fun SearchToolBar(
    state: State<SearchScreenState>,
    input: MutableState<String>,
    currentScreen: String,
    backAction: () -> Unit,
    searchCityAction: (input: String) -> Unit,
    getReport: (city: String) -> Unit,
) {
    val expand = remember { mutableStateOf(false) }
    val menuItemData = state.value.citySearchResults
    expand.value = menuItemData.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.grid_2)
    ) {
        Row(
            modifier = Modifier
                .padding(top = Dimens.grid_4)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentScreen == ScreenNames.RESULTS) {
                IconButton(onClick = {
                    backAction()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_24),
                        contentDescription = "Go Back",
                        tint = text_color_normal,
                    )
                }
            }
            SearchTextField(
                hint = stringResource(R.string.search),
                fieldValue = input,
                action = searchCityAction,
            )
        }

        SuggestionBox(
            expand = expand,
            cities = menuItemData,
            close = {
                expand.value = false

            },
        ) { city ->
            getReport(city)
        }
    }
}

@Composable
fun SuggestionBox(
    expand: State<Boolean>,
    cities: List<CitySuggestionUIModel>,
    close: () -> Unit,
    getReport: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = Dimens.grid_1_5),
    ) {
        if (expand.value) {
            Popup(
                alignment = Alignment.TopCenter,
                properties = PopupProperties(
                    excludeFromSystemGesture = true,
                ),
                // to dismiss on click outside
                onDismissRequest = { close() }
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Dimens.grid_2)
                        .heightIn(max = Dimens.grid_18)
                        .verticalScroll(state = scrollState)
                        .border(
                            width = Dimens.grid_0_125,
                            color = Color.LightGray
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    cities.onEachIndexed { index, city ->
                        if (index != 0) {
                            HorizontalDivider(
                                thickness = Dimens.grid_0_125,
                                color = Color.Gray
                            )
                        }
                        val full_city = stringResource(
                            R.string.city,
                            city.cityName ?: "",
                            city.cityRegion ?: "",
                            city.cityCountry ?: ""
                        )
                        Box(
                            modifier = Modifier
                                .background(Color.LightGray)
                                .fillMaxWidth()
                                .wrapContentSize()
                                .padding(Dimens.grid_1)
                                .onClick {
                                    getReport(full_city)
                                },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = full_city,
                                style = WeatherTypography.displayMedium.copy(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                ),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}