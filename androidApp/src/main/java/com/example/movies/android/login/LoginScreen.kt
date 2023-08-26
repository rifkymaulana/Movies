package com.example.movies.android.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movies.android.Movie
import com.example.movies.android.R
import com.example.movies.android.common.Button
import com.example.movies.android.database.AccountEntity
import com.example.movies.android.ui.Primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class LoginInput(
    val email: String,
    val password: String
)

@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val loginInput = remember { mutableStateOf(LoginInput("", "")) }

    // move to home if user is already logged in
    val application = context.applicationContext as Movie
    val database = (application as Movie).database
    val accountDao = database.accountDao()

    LaunchedEffect(key1 = true) {
        val account = accountDao.getAccountByIsLogin(true)

        if (account != null) {
            navController.navigate("home")
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = loginInput.value.email,
                onValueChange = { newValue ->
                    loginInput.value = loginInput.value.copy(email = newValue)
                },
                label = { Text(text = "Email") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.message),
                        contentDescription = null
                    )
                },
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Primary,
                    focusedLabelColor = Primary,
                    cursorColor = Primary
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            val localFocusManager = LocalFocusManager.current
            val passwordVisible = remember {
                mutableStateOf(false)
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                label = { Text(text = "Password") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Primary, focusedLabelColor = Primary, cursorColor = Primary
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                ),
                singleLine = true,
                keyboardActions = KeyboardActions {
                    localFocusManager.clearFocus()
                },
                maxLines = 1,
                value = loginInput.value.password,
                onValueChange = { newValue ->
                    loginInput.value = loginInput.value.copy(password = newValue)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_lock_18),
                        contentDescription = ""
                    )
                },
                trailingIcon = {
                    val iconImage = if (passwordVisible.value) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    val description = if (passwordVisible.value) {
                        stringResource(id = R.string.hide_password)
                    } else {
                        stringResource(id = R.string.show_password)
                    }

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(imageVector = iconImage, contentDescription = description)
                    }
                },
                visualTransformation = if (passwordVisible.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                text = stringResource(R.string.login),
                onClick = {
                    val input = loginInput.value
                    var account: AccountEntity? = null

                    CoroutineScope(Dispatchers.IO).launch {
                        // check email and password in database is correct
                        account =
                            accountDao.getAccountByEmailAndPassword(input.email, input.password)
                    }

                    // if account is null, email or password is incorrect
                    if (account == null) {
                        return@Button
                    }

                    // update isLogin to true when user login
                    CoroutineScope(Dispatchers.IO).launch {
                        accountDao.updateAccount(account!!.id, true)
                    }

                    // navigate to home screen
                    navController.navigate("home")
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // don't have an account? navigate to register screen
            Button(
                text = stringResource(R.string.register),
                onClick = {
                    navController.navigate("register")
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
