package com.shokot.todo.screen.authentication

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shokot.todo.R
import com.shokot.todo.navigation.AuthenticationScreen
import com.shokot.todo.navigation.Graph
import com.shokot.todo.presentation.RegistrationViewModel
import com.shokot.todo.utility.Helper
import com.shokot.todo.utility.Helper.CustomOutlinedTextField
import com.shokot.todo.utility.Helper.isEmailValid

@Composable
fun LoginScreen(navController: NavController, registrationViewModel: RegistrationViewModel) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var logoVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        logoVisible = true
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Helper.Background()
        LoginForm(
            navController,
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            logoVisible,
            registrationViewModel
        )
    }
}

@Composable
fun LoginForm(
    navController: NavController,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    logoVisible: Boolean,
    registrationViewModel: RegistrationViewModel
) {
    val logoSize = 150
    val backgroundAlpha = 0.6f
    val logoScaleAnimationDuration = 700
    val scale by animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0f,
        animationSpec = tween(durationMillis = logoScaleAnimationDuration),
        label = "box scale animation"
    )
    var isCredentialValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf(R.string.nothing) }
    val context = LocalContext.current

    LaunchedEffect(email) {
        isCredentialValid = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp)
            .background(
                MaterialTheme.colorScheme.surface.copy(alpha = backgroundAlpha),
                shape = MaterialTheme.shapes.large
            )
    ) {

        // Logo
        Box(
            modifier = Modifier
                .size((logoSize * scale).dp)
                .offset(y = (-(logoSize * scale) / 2).dp)
                .align(Alignment.TopCenter)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)

        ) {
            Image(
                painter = painterResource(id = R.drawable.todo_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(22.dp)
            )
            Text(
                text = "ToDo",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        // Form
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Email field
                CustomOutlinedTextField(
                    value = email,
                    onValueChange = {
                        onEmailChange(it)
                        isCredentialValid = true
                    },
                    label = R.string.email,
                    icon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email,
                    space = 5
                )

                // Password field
                CustomOutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = R.string.password,
                    icon = Icons.Default.Lock,
                    keyboardType = KeyboardType.Password,
                    space = 15
                )
                // Show error if email is not valid
                AnimatedVisibility(visible = !isCredentialValid) {
                    Text(
                        text = stringResource(id = errorMessage),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                //don't have account section
                Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = stringResource(R.string.dont_have_account),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .clickable{
                            navController.navigate(AuthenticationScreen.Registration.route)
                    })
                Spacer(modifier = Modifier.height(10.dp))
                // Login button
                Button(
                    shape = MaterialTheme.shapes.extraSmall,
                    onClick = {

                        //Toast.makeText(context, user.email, Toast.LENGTH_LONG).show()
                        errorMessage = handleOnLoginClick(navController,email,password,registrationViewModel,context) {
                            isCredentialValid = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.login))

                }
            }
        }
    }
}

fun handleOnLoginClick(
    navController: NavController,
    email: String,
    password: String,
    registrationViewModel: RegistrationViewModel,
    context: Context,
    isCredentialValid: () -> Unit
) : Int {
    //check email error
    if (!isEmailValid(email)) {
        isCredentialValid()
        return R.string.invalid_email
    }

    val user = registrationViewModel.getUserByEmail(email)

    //check credential error
    //check if there is such email in the database confront its passwords and return ,and set the error to
    if (user == null) {
        //do this when successful login
        isCredentialValid()
        return R.string.invalid_credentials
    }

    if (password != user.password) {
        //do this when successful login
        isCredentialValid()
        return R.string.invalid_credentials
    }
    val preferences: SharedPreferences = context.applicationContext.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)
    preferences.edit().putInt("userId",user.id).apply()

    navController.navigate(Graph.mainApp) {
        popUpTo(Graph.authentication) {
            inclusive = true
        }
    }

    return R.string.nothing
}
