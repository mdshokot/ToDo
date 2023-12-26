package com.shokot.todo.navigation.graph

import android.content.SharedPreferences
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.shokot.todo.ThemeViewModel
import com.shokot.todo.navigation.Graph
import com.shokot.todo.navigation.MainAppScreen
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.presentation.RegistrationViewModel
import com.shokot.todo.screen.main.GraphScreen
import com.shokot.todo.screen.main.HomeScreen
import com.shokot.todo.screen.main.ProfileScreen
import com.shokot.todo.screen.main.components.home.TaskDialogViewModel

fun NavGraphBuilder.mainAppGraph(
    navController: NavController,
    themeViewModel: ThemeViewModel,
    registrationViewModel: RegistrationViewModel,
    preferences: SharedPreferences,
    taskViewModal: TaskDialogViewModel,
    homeScreenViewModel: HomeScreenViewModel,
) {
    val user  =  registrationViewModel.getUserById(preferences.getInt("userId",1))
    navigation(startDestination = MainAppScreen.Home.route, route = Graph.mainApp) {
        composable(route = MainAppScreen.Home.route) {
            HomeScreen(navController = navController,taskViewModal,homeScreenViewModel)
        }
        composable(route = MainAppScreen.Graph.route) {
            GraphScreen(navController = navController)
        }
        composable(route = MainAppScreen.Profile.route) {
            ProfileScreen(navController = navController,themeViewModel = themeViewModel,registrationViewModel,user)
        }
    }
}