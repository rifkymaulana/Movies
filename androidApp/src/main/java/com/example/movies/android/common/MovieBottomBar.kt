package com.example.movies.android.common

// bottom bar
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
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
            }
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
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(id = R.string.settings)
                )
            },
            selected = navController.currentDestination?.route == "favorite",
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.profile)
                )
            },
            selected = navController.currentDestination?.route == "profile",
            onClick = { /*TODO*/ }
        )
    }
}