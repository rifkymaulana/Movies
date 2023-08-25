package com.example.movies

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movies.android.common.Detail
import com.example.movies.android.common.Home
import com.example.movies.android.common.Login
import com.example.movies.android.common.MovieAppBar
import com.example.movies.android.common.movieDestinations
import com.example.movies.android.detail.DetailScreen
import com.example.movies.android.detail.DetailViewModel
import com.example.movies.android.home.HomeScreen
import com.example.movies.android.home.HomeViewModel
import com.example.movies.android.login.LoginScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieApp() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    val scaffoldState = rememberScaffoldState()

    val isSystemDark = isSystemInDarkTheme()
    val statusBarColor = if (isSystemDark) {
        MaterialTheme.colors.primaryVariant
    } else {
        Color.Transparent
    }
    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor, darkIcons = !isSystemDark)
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = movieDestinations.find {
        backStackEntry?.destination?.route == it.route ||
                backStackEntry?.destination?.route == it.routeWithArgs
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            currentScreen?.let { screen ->
                if (screen != Login) {
                    if (screen == Home) {
                        MovieAppBar(
                            canNavigateBack = false,
                            currentScreen = screen
                        ) {
                        }
                    } else {
                        MovieAppBar(
                            canNavigateBack = navController.previousBackStackEntry != null,
                            currentScreen = screen
                        ) {
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    ) { innerPaddings ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(innerPaddings),
            startDestination = Login.route
        ) {
            composable(Login.route) {
                LoginScreen(navController)
            }

            composable(Home.routeWithArgs) {
                val homeViewModel: HomeViewModel = koinViewModel()
                HomeScreen(
                    uiState = homeViewModel.uiState,
                    loadNextMovies = {
                        homeViewModel.loadMovies(forceReload = it)
                    },
                    navigateToDetail = {
                        navController.navigate(
                            "${Detail.route}/${it.id}"
                        )
                    }
                )
            }

            composable(Detail.routeWithArgs, arguments = Detail.arguments) {
                val movieId = it.arguments?.getInt("movieId") ?: 0
                val detailViewModel: DetailViewModel = koinViewModel(
                    parameters = { parametersOf(movieId) }
                )

                DetailScreen(uiState = detailViewModel.uiState)
            }
<<<<<<< HEAD

            composable(Category.routeWithArgs) {
                CategoryScreen(
                    navigateToDetail = {
                        navController.navigate(
                            "${Detail.route}/${it.id}"
                        )
                    }
                )
            }
=======
>>>>>>> 3d6bb84 (Revert "feat: category screen")
        }
    }
}