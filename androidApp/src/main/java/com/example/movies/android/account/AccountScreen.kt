package com.example.movies.android.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movies.android.Movie
import com.example.movies.android.login.accountLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AccountScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as Movie
    val database = application.database
    val accountDao = database.accountDao()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Account Information",
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "Welcome, ${accountLogin?.name}!",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val account = accountDao.getAccountByIsLogin(true)

                    account?.let { nonNullAccount ->
                        withContext(Dispatchers.Main) {
                            accountDao.updateAccount(nonNullAccount.id, false)
                            navController.navigate("preLogin") {
                                popUpTo("preLogin") {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text(text = "Logout", color = Color.White)
        }

    }
}

