package com.shokot.todo

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shokot.todo.navigation.AppNavigation
import com.shokot.todo.ui.theme.ToDoTheme
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.shokot.todo.presentation.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {

            val preferences: SharedPreferences = this.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)
            val themeViewModel by viewModels<ThemeViewModel>(
                factoryProducer = {
                    object : ViewModelProvider.Factory{
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {

                            return ThemeViewModel(preferences) as T
                        }
                    }
                }
            )

            val registrationViewModel by viewModels<RegistrationViewModel>()

            ToDoTheme(darkTheme = themeViewModel.isDarkTheme) {
                AppNavigation(themeViewModel,registrationViewModel)
            }
        }
    }
}

class ThemeViewModel(private val preferences: SharedPreferences) : ViewModel() {
     var isDarkTheme  by mutableStateOf(preferences.getBoolean("isDarkTheme", false))

    fun toggleTheme(){
        isDarkTheme = !isDarkTheme
        saveThemePreference(isDarkTheme)
    }

    private fun saveThemePreference(isDarkTheme: Boolean) {
        preferences.edit().putBoolean("isDarkTheme", isDarkTheme).apply()
    }
}

