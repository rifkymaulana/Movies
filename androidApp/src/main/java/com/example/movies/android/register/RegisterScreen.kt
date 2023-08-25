package com.example.movies.android.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.example.movies.android.login.LoginScreen

@Composable
fun RegisterScreen(
    navController: NavController
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                text = stringResource(R.string.name),
                icon = painterResource(id = R.drawable.message)
            )

            TextField(
                text = stringResource(R.string.email),
                icon = painterResource(id = R.drawable.message)
            )

            PasswordField(
                text = stringResource(R.string.password),
                icon = painterResource(id = R.drawable.baseline_lock_18)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                text = stringResource(R.string.register),
                onClick = {
                    navController.navigate("login")
                }
            )
        }

    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    val navController = NavController(LocalContext.current)
    RegisterScreen(navController)
}