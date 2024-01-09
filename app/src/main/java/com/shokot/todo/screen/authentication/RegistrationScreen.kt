package com.shokot.todo.screen.authentication

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shokot.todo.R
import com.shokot.todo.domain.entity.User
import com.shokot.todo.navigation.AuthenticationScreen
import com.shokot.todo.presentation.UserViewModel
import com.shokot.todo.utility.Helper
import com.shokot.todo.utility.Helper.CustomOutlinedTextField
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(navController: NavController, userViewModel: UserViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Helper.Background()
        RegistrationForm(navController, userViewModel)
    }
}

@Composable
fun RegistrationForm(navController: NavController, userViewModel: UserViewModel) {
    val backgroundAlpha = 0.6f
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isCredentialValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf(R.string.nothing) }

    val msg = stringResource(id = R.string.user_successfully_created)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(email, password, confirmPassword) {
        isCredentialValid = true
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.register),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface.copy(alpha = backgroundAlpha),
                    shape = MaterialTheme.shapes.large
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .verticalScroll(scrollState)
            ) {
                //username
                CustomOutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = R.string.username,
                    icon = Icons.Default.AccountBox,
                    keyboardType = KeyboardType.Email,
                    space = 5
                )
                //email
                CustomOutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = R.string.email,
                    icon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email,
                    space = 5
                )

                //password
                CustomOutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = R.string.password,
                    icon = Icons.Default.Lock,
                    keyboardType = KeyboardType.Password,
                    space = 5
                )

                //confirm password
                CustomOutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                    },
                    label = R.string.confirm_password,
                    icon = Icons.Default.Lock,
                    keyboardType = KeyboardType.Password,
                    space = 5
                )
            }
            // Show error if email is not valid
            AnimatedVisibility(visible = !isCredentialValid) {
                Text(
                    text = stringResource(id = errorMessage),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.already_have_account),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        navController.navigate(AuthenticationScreen.Login.route)
                    })
            Spacer(modifier = Modifier.height(15.dp))
            //button register
            Button(
                shape = MaterialTheme.shapes.extraSmall,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val response = handleOnSubmit(
                        username,
                        email,
                        password,
                        confirmPassword,
                    ) {
                        isCredentialValid = false
                    }
                    if (response == R.string.nothing) {
                        coroutineScope.launch {
                            val doesEmailExists = userViewModel.doesEmailExists(email)
                            doesEmailExists.firstOrNull()?.let {
                                if (it) {
                                    isCredentialValid = false
                                    errorMessage = R.string.email_already_exists
                                } else {
                                    val user = User(
                                        username = username,
                                        email = email,
                                        password = password
                                    )
                                    userViewModel.insertUser(user)
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    navController.navigate(AuthenticationScreen.Login.route)
                                }
                            } ?: run {
                                isCredentialValid = false
                                errorMessage = R.string.email_already_exists
                            }
                        }
                    } else {
                        errorMessage = response
                    }
                }) {
                Text(text = stringResource(id = R.string.btn_register))
            }
        }
    }
}

fun handleOnSubmit(
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    isCredentialValid: () -> Unit
): Int {

    if (username.isEmpty()) {
        isCredentialValid()
        return R.string.empty_username
    }

    if (!Helper.isEmailValid(email)) {
        isCredentialValid()
        return R.string.invalid_email
    }

    if (password.length < 6) {
        isCredentialValid()
        return R.string.password_min_val
    }

    if (!password.contentEquals(confirmPassword)) {
        isCredentialValid()
        return R.string.password_not_equal
    }

    return R.string.nothing
}




