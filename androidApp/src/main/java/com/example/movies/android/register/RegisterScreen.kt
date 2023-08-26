package com.example.movies.android.register

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

data class RegistrationInput(
    val name: String,
    val email: String,
    val password: String
)

@Composable
fun RegisterScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val registrationInput = remember { mutableStateOf(RegistrationInput("", "", "")) }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = registrationInput.value.name,
                onValueChange = { newValue ->
                    registrationInput.value = registrationInput.value.copy(name = newValue)
                },
                label = { Text(text = "Name") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.message), contentDescription = null
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

            OutlinedTextField(
                value = registrationInput.value.email,
                onValueChange = { newValue ->
                    registrationInput.value = registrationInput.value.copy(email = newValue)
                },
                label = { Text(text = "Email") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.message), contentDescription = null
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
                value = registrationInput.value.password,
                onValueChange = { newValue ->
                    registrationInput.value = registrationInput.value.copy(password = newValue)
                },
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.baseline_lock_18), contentDescription = "") },
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
                text = stringResource(R.string.register),
                onClick = {
                    val application = context.applicationContext as Movie
                    val database = (application as Movie).database
                    val accountDao = database.accountDao()

                    val input = registrationInput.value

                    val accountEntity = AccountEntity(
                        name = input.name,
                        email = input.email,
                        password = input.password
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        accountDao.insertAccount(accountEntity)
                    }
                    navController.popBackStack()
                    navController.navigate("login")
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // don't have an account? navigate to register screen
            Button(
                text = stringResource(R.string.login),
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