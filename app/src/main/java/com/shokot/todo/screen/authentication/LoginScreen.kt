package com.shokot.todo.screen.authentication

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shokot.todo.R
import com.shokot.todo.navigation.Graph
import com.shokot.todo.ui.theme.ToDoTheme

@Composable
fun LoginScreen(navController: NavController) {
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
        Background()
        LoginForm(
            navController,
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            logoVisible
        )
    }
}

@Composable
fun Background() {
    Image(
        painter = painterResource(id = R.drawable.login_bg),
        contentDescription = "login background",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}

fun isEmailValid(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(emailPattern.toRegex())
}

@Composable
fun LoginForm(
    navController: NavController,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    logoVisible: Boolean
) {
    val logoSize = 150
    val backgroundAlpha = 0.6f
    val logoScaleAnimationDuration = 700
    val scale by animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0f,
        animationSpec = tween(durationMillis = logoScaleAnimationDuration),
        label = "box scale animation"
    )

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
                    onValueChange = onEmailChange,
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

                // Login button
                Button(
                    onClick = {
                        //do this when successful login
                        navController.navigate(Graph.mainAppHelper) {
                            popUpTo(Graph.authentication) {
                                inclusive = true
                            }
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


@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
    icon: ImageVector,
    keyboardType: KeyboardType,
    space: Int
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    var isValid by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = { text ->
            onValueChange(text)
            isValid = if (label == R.string.password) text.isNotEmpty() else isEmailValid(text)
        },
        label = { Text(text = stringResource(label)) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        visualTransformation = if (label == R.string.password) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        modifier = Modifier.fillMaxWidth(),
        isError = isValid
    )

    Spacer(modifier = Modifier.height(space.dp))
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ToDoTheme {
        val navController = rememberNavController();
        LoginScreen(navController = navController);
    }
}