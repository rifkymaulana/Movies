package com.example.movies.android.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movies.android.R
import com.example.movies.android.common.Button
import com.example.movies.android.common.PasswordField
import com.example.movies.android.common.TextField

@Composable
fun LoginScreen(
    navController: NavController
) {


    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                text = stringResource(R.string.login),
                onClick = {
                    navController.navigate("home")
                }
            )
        }

    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    val navController = NavController(LocalContext.current)
    LoginScreen(navController)
}
