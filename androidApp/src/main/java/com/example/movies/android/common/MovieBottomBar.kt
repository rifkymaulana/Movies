package com.example.movies.android.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.movies.android.R

@Composable
fun MovieBottomBar(
    navController: NavController
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth()
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(id = R.string.home)
                )
            },
            selected = navController.currentDestination?.route == "home",
            onClick = {
                navController.navigate("home")
            },
            selectedContentColor = androidx.compose.ui.graphics.Color.Red,
            unselectedContentColor = androidx.compose.ui.graphics.Color.Gray
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Category,
                    contentDescription = stringResource(id = R.string.search)
                )
            },
            selected = navController.currentDestination?.route == "category",
            onClick = {
                navController.navigate("category")
            },
            selectedContentColor = androidx.compose.ui.graphics.Color.Red,
            unselectedContentColor = androidx.compose.ui.graphics.Color.Gray
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(id = R.string.settings)
                )
            },
            selected = navController.currentDestination?.route == "movieFav",
            onClick = {
                navController.navigate("movieFav")

            },
            selectedContentColor = androidx.compose.ui.graphics.Color.Red,
            unselectedContentColor = androidx.compose.ui.graphics.Color.Gray
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.profile)
                )
            },
            selected = navController.currentDestination?.route == "account",
            onClick = {
                navController.navigate("account")
            },
            selectedContentColor = androidx.compose.ui.graphics.Color.Red,
            unselectedContentColor = androidx.compose.ui.graphics.Color.Gray
        )
    }
}