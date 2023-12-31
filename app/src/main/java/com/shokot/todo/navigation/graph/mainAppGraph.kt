package com.shokot.todo.navigation.graph

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.shokot.todo.ThemeViewModel
import com.shokot.todo.navigation.Graph
import com.shokot.todo.navigation.MainAppScreen
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.presentation.UserViewModel
import com.shokot.todo.screen.main.GraphScreen
import com.shokot.todo.screen.main.HomeScreen
import com.shokot.todo.screen.main.ProfileScreen
import com.shokot.todo.screen.main.components.home.TaskDialogViewModel
import com.shokot.todo.utility.PreferencesKeys

fun NavGraphBuilder.mainAppGraph(
    navController: NavController,
    themeViewModel: ThemeViewModel,
    userViewModel: UserViewModel,
    taskViewModal: TaskDialogViewModel,
    homeScreenViewModel: HomeScreenViewModel,
) {

    navigation(startDestination = MainAppScreen.Home.route, route = Graph.mainApp) {
        composable(route = MainAppScreen.Home.route) {
            HomeScreen(navController = navController, taskViewModal, homeScreenViewModel)
        }
        composable(route = MainAppScreen.Graph.route) {
            GraphScreen(navController = navController)
        }
        composable(route = MainAppScreen.Profile.route) {
            ProfileScreen(
                navController = navController, themeViewModel = themeViewModel,
                userViewModel = userViewModel
            )
        }
    }
}