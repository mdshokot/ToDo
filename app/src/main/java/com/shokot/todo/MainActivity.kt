package com.shokot.todo

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.shokot.todo.ui.theme.ToDoTheme
import androidx.compose.foundation.isSystemInDarkTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shokot.todo.navigation.AppNavigation
import com.shokot.todo.presentation.GraphScreenViewModel
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.presentation.TaskViewModel
import com.shokot.todo.presentation.UserViewModel
import com.shokot.todo.screen.main.ProfileViewModel
import com.shokot.todo.screen.main.components.home.TaskDialogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var  fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            val userViewModel by viewModels<UserViewModel>()
            val homeScreenViewModel by viewModels<HomeScreenViewModel>()
            val taskViewModel by viewModels<TaskViewModel>()
            val taskDialogViewModal by viewModels<TaskDialogViewModel>()
            val graphScreenViewModel by viewModels<GraphScreenViewModel>()
            val preferences: SharedPreferences = this.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)
            val profileViewModel  by viewModels<ProfileViewModel>()
            ToDoTheme(darkTheme = isSystemInDarkTheme()) {
                AppNavigation(
                    userViewModel,
                    homeScreenViewModel,
                    taskViewModel,
                    taskDialogViewModal,
                    graphScreenViewModel,
                    fusedLocationClient,
                    profileViewModel
                )
            }
        }
    }
}




