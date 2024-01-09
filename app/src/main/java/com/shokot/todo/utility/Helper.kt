package com.shokot.todo.utility

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
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
import androidx.security.crypto.MasterKey
import com.shokot.todo.R
import com.shokot.todo.navigation.BottomNavigationItem
import com.shokot.todo.navigation.MainAppScreen
import javax.crypto.KeyGenerator


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
        icon: ImageVector? = null,
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
            leadingIcon = {
                if (icon != null) {
                    Icon(icon, contentDescription = null)
                }
            },
            visualTransformation = if (label == R.string.password || label == R.string.confirm_password) PasswordVisualTransformation() else VisualTransformation.None,
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
fun createOrLoadMasterKey(context: Context): MasterKey {
    val preferences: SharedPreferences =
        context.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)

    return if (!preferences.getBoolean("masterKeySaved", false)) {
        // MasterKey not saved yet, create and save it
        val masterKey = createMasterKey(context)
        saveMasterKey(masterKey, context)
        preferences.edit().putBoolean("masterKeySaved", true).apply()
        masterKey
    } else {
        // MasterKey already saved, retrieve it
        getMasterKey(context)
    }
}

private fun saveMasterKey(masterKey: MasterKey, context: Context) {
    // Here, you would typically save the masterKey to a secure storage like EncryptedSharedPreferences
    // However, since EncryptedSharedPreferences requires a MasterKey, you need to save it initially in a different way.

    // For the purpose of this example, we'll generate and store a cryptographic key using the Android Keystore.
    val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
    val keyGenParameterSpec = KeyGenParameterSpec.Builder(
        "key_for_encrypt",
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(256)
        .setRandomizedEncryptionRequired(false)
        .build()

    keyGenerator.init(keyGenParameterSpec)
    keyGenerator.generateKey()
}

private fun createMasterKey(context: Context): MasterKey {
    return MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
}

private fun getMasterKey(context: Context): MasterKey {
    return MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
}
