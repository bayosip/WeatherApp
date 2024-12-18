package com.nooro.weatherapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nooro.weatherapp.domain.network.util.Util
import com.nooro.weatherapp.presentation.ui.navigation_graph.NavigationGraph
import com.nooro.weatherapp.presentation.ui.navigation_graph.SearchToolBar
import com.nooro.weatherapp.presentation.ui.screens.ScreenNames
import com.nooro.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.nooro.weatherapp.presentation.ui.view_model.SearchScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val searchScreenViewModel: SearchScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                AppContent()
            }
        }

    }

   private val runnable: Runnable = object : Runnable {
        override fun run() {
            searchScreenViewModel.checkForInternet(this@MainActivity)
            Util.getHandler()?.postDelayed(this, 1000)
        }
    }

    @Composable
    fun AppContent() {
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val input = remember {
            mutableStateOf("")
        }
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                SearchToolBar(
                    state = searchScreenViewModel._state,
                    input = input,
                    backAction = {
                        navController.popBackStack()
                    },
                    currentScreen = navController
                        .currentBackStackEntryAsState()
                        .value?.destination?.route ?: ScreenNames.HOME,
                    searchCityAction = { text ->
                        searchScreenViewModel.getSearchForCity(city = text)
                        navigateWithArguments(
                            screen = ScreenNames.RESULTS,
                            navController = navController
                        )
                    }
                ) { city ->
                    searchScreenViewModel.getWeatherReportFor(city)
                    searchScreenViewModel.clearSearch()
                }
            }
        ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                NavigationGraph(
                    navController = navController,
                    searchScreenViewModel = searchScreenViewModel
                ) { message ->
                    if (message.isNotBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message
                            )
                        }
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        Util.getHandler()?.post(runnable)
    }

    override fun onPause() {
        super.onPause()
        Util.getHandler()?.removeCallbacks(runnable)
    }
}

fun navigateWithArguments(
    argument: String? = null,
    screen: String,
    navController: NavHostController
) {
    var route = screen
    // If argument is supplied, navigate using that argument
    argument?.let {
        route = screen.plus("/$it")
    }
    navController.navigate(route)
}