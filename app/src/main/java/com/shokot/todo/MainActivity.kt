package com.shokot.todo

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.shokot.todo.ui.theme.ToDoTheme
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import com.shokot.todo.navigation.AppNavigation
import com.shokot.todo.presentation.GraphScreenViewModel
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.presentation.TaskViewModel
import com.shokot.todo.presentation.UserViewModel
import com.shokot.todo.screen.main.components.home.TaskDialogViewModel
import com.shokot.todo.utility.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

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
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ThemeViewModel(preferences) as T
                        }
                    }
                }
            )
            val userViewModel by viewModels<UserViewModel>()
            val userFlow = userViewModel.getUserById(preferences.getInt(PreferencesKeys.USER_ID, 0))
            val user = userFlow.collectAsState(initial = null).value
            val homeScreenViewModel by viewModels<HomeScreenViewModel>()
            val taskViewModel by viewModels<TaskViewModel>()
            val taskDialogViewModal by viewModels<TaskDialogViewModel>()
            val graphScreenViewModel by viewModels<GraphScreenViewModel>()

            if(user !== null){
                userViewModel.setMyUser(user)
            }
            ToDoTheme(darkTheme = themeViewModel.isDarkTheme) {
                AppNavigation(themeViewModel,userViewModel,homeScreenViewModel,taskViewModel,taskDialogViewModal,graphScreenViewModel)
            }
        }
    }
}

class ThemeViewModel(private val preferences: SharedPreferences) : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(preferences.getBoolean("isDarkTheme", false))

    var isDarkTheme: Boolean
        get() = _isDarkTheme.value
        private set(value) {
            _isDarkTheme.value = value
        }

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
        saveThemePreference(isDarkTheme)
    }
    private fun saveThemePreference(isDarkTheme: Boolean) {
        preferences.edit().putBoolean("isDarkTheme", isDarkTheme).apply()
    }
}

