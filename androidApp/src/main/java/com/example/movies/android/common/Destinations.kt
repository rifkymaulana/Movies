package com.example.movies.android.common

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val title: String
    val route: String
    val routeWithArgs: String
}

object Register : Destination {
    override val title: String
        get() = "Register"

    override val route: String
        get() = "register"

    override val routeWithArgs: String
        get() = route
}

object Login : Destination {
    override val title: String
        get() = "Login"

    override val route: String
        get() = "login"

    override val routeWithArgs: String
        get() = route
}


object Home : Destination {
    override val title: String
        get() = "Movies"

    override val route: String
        get() = "home"

    override val routeWithArgs: String
        get() = route
}

object Detail : Destination {
    override val title: String
        get() = "Movie details"

    override val route: String
        get() = "detail"

    override val routeWithArgs: String
        get() = "$route/{movieId}"

    val arguments = listOf(
        navArgument(name = "movieId") { type = NavType.IntType }
    )
}

object Category : Destination {
    override val title: String
        get() = "Category"

    override val route: String
        get() = "category"

    override val routeWithArgs: String
        get() = route
}

// account
object Account : Destination {
    override val title: String
        get() = "Account"

    override val route: String
        get() = "account"

    override val routeWithArgs: String
        get() = route
}


val movieDestinations = listOf(
    Register,
    Login,
    Home,
    Detail,
    Category
)
