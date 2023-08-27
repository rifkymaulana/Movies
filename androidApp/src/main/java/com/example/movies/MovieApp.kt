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
import com.example.movies.android.account.AccountScreen
import com.example.movies.android.category.CategoryScreen
import com.example.movies.android.common.Account
import com.example.movies.android.common.Category
import com.example.movies.android.common.Detail
import com.example.movies.android.common.Home
import com.example.movies.android.common.Login
import com.example.movies.android.common.MovieBottomBar
import com.example.movies.android.common.MovieFav
import com.example.movies.android.common.PreLogin
import com.example.movies.android.common.Register
import com.example.movies.android.common.movieDestinations
import com.example.movies.android.detail.DetailScreen
import com.example.movies.android.detail.DetailViewModel
import com.example.movies.android.home.HomeScreen
import com.example.movies.android.home.HomeViewModel
import com.example.movies.android.login.LoginScreen
import com.example.movies.android.login.PreLoginScreen
import com.example.movies.android.moviefav.MovieFavScreen
import com.example.movies.android.register.RegisterScreen
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
        backStackEntry?.destination?.route == it.route || backStackEntry?.destination?.route == it.routeWithArgs
    }

    Scaffold(scaffoldState = scaffoldState, bottomBar = {
        currentScreen?.let { screen ->
            if (screen != Login && screen != Register  && screen != PreLogin) {
                MovieBottomBar(navController)
            }
        }
    }) { innerPaddings ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(innerPaddings),
            startDestination = PreLogin.route
        ) {

            composable(Register.route) {
                RegisterScreen(navController)
            }

            composable(PreLogin.route) {
                PreLoginScreen(navController)
            }

            composable(Login.route) {
                LoginScreen(navController = navController)
            }

            composable(Home.routeWithArgs) {
                val homeViewModel: HomeViewModel = koinViewModel()
                HomeScreen(uiState = homeViewModel.uiState, loadNextMovies = {
                    homeViewModel.loadMovies(forceReload = it)
                }, navigateToDetail = {
                    navController.navigate(
                        "${Detail.route}/${it.id}"
                    )
                })
            }

            composable(Detail.routeWithArgs, arguments = Detail.arguments) {
                val movieId = it.arguments?.getInt("movieId") ?: 0
                val detailViewModel: DetailViewModel =
                    koinViewModel(parameters = { parametersOf(movieId) })

                DetailScreen(uiState = detailViewModel.uiState)
            }

            composable(Category.routeWithArgs) {
                CategoryScreen(navigateToDetail = {
                    navController.navigate(
                        "${Detail.route}/${it.id}"
                    )
                })
            }

            composable(MovieFav.routeWithArgs) {
                val homeViewModel: HomeViewModel = koinViewModel()
                MovieFavScreen(uiState = homeViewModel.uiState, loadNextMovies = {
                    homeViewModel.loadMovies(forceReload = it)
                }, navigateToDetail = {
                    navController.navigate(
                        "${Detail.route}/${it.id}"
                    )
                })
            }

            composable(Account.routeWithArgs) {
                AccountScreen(navController)
            }
        }
    }
}