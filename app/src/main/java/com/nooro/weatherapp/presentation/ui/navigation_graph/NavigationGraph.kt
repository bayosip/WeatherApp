package com.nooro.weatherapp.presentation.ui.navigation_graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nooro.weatherapp.presentation.ui.navigateWithArguments
import com.nooro.weatherapp.presentation.ui.screens.Home.HomeWeatherScreen
import com.nooro.weatherapp.presentation.ui.screens.ScreenNames
import com.nooro.weatherapp.presentation.ui.screens.search.SearchResultScreen
import com.nooro.weatherapp.presentation.ui.view_model.HomeScreenViewModel
import com.nooro.weatherapp.presentation.ui.view_model.SearchScreenViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    searchScreenViewModel: SearchScreenViewModel,
    messageBus: (String) -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = ScreenNames.HOME
    ) {

        composable(ScreenNames.HOME) {
            val viewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            searchScreenViewModel._state.value.message?.let {
                messageBus(
                    it.getContentIfNotHandled() ?: ""
                )
            }
            HomeWeatherScreen(
                state = viewModel._state,
                getWeather = {
                    viewModel.getWeatherReportForSavedCities()
                }
            ) { message ->
                messageBus(message)
            }
        }

        composable(ScreenNames.RESULTS) {
            SearchResultScreen(searchScreenViewModel._state,
                onSelect = { city ->
                    searchScreenViewModel.onSelect(city)
                    navigateWithArguments(
                        screen = ScreenNames.HOME,
                        navController = navController
                    )
                }) { message ->
                messageBus(message)
            }
        }
    }
}