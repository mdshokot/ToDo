package com.shokot.todo.utility

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.shokot.todo.R
import com.shokot.todo.navigation.BottomNavigationItem
import com.shokot.todo.navigation.MainAppScreen


object Helper {
    val bottomNavigationItems = listOf(
        BottomNavigationItem(MainAppScreen.Home.route, Icons.Filled.Home, Icons.Default.Home),
        BottomNavigationItem(
            MainAppScreen.Graph.route,
            Icons.Filled.DateRange,
            Icons.Default.DateRange
        ),
        BottomNavigationItem(
            MainAppScreen.Profile.route,
            Icons.Filled.AccountCircle,
            Icons.Default.AccountCircle
        ),
    )

    fun isMainApp(route: String?): Boolean {
        val showBottomNavBar = listOf(
            MainAppScreen.Home.route,
            MainAppScreen.Profile.route,
            MainAppScreen.Graph.route,
        )

        return showBottomNavBar.contains(route)
    }

    fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
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

    @Composable
    fun CustomOutlinedTextField(
        value: String,
        onValueChange: (String) -> Unit,
        label: Int,
        icon: ImageVector,
        keyboardType: KeyboardType,
        space: Int
    ) {
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = value,
            onValueChange = { text ->
                onValueChange(text)
            },
            label = { Text(text = stringResource(label)) },
            leadingIcon = { Icon(icon, contentDescription = null) },
            visualTransformation = if (label == R.string.password || label == R.string.confirm_password ) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(space.dp))
    }
}
