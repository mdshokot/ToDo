package com.shokot.todo.navigation.graph

import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.shokot.todo.navigation.Graph
import com.shokot.todo.navigation.MainAppScreen
import com.shokot.todo.presentation.GraphScreenViewModel
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.presentation.TaskViewModel
import com.shokot.todo.presentation.UserViewModel
import com.shokot.todo.screen.main.GraphScreen
import com.shokot.todo.screen.main.HomeScreen
import com.shokot.todo.screen.main.PiChartScreen
import com.shokot.todo.screen.main.ProfileScreen
import com.shokot.todo.screen.main.ProfileViewModel
import com.shokot.todo.screen.main.TaskScreen
import com.shokot.todo.screen.main.components.GraphPageScreen
import com.shokot.todo.screen.main.components.home.TaskDialogViewModel

fun NavGraphBuilder.mainAppGraph(
    navController: NavController,
    userViewModel: UserViewModel,
    taskDialogViewModel: TaskDialogViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    taskScreenViewModel: TaskViewModel,
    graphScreenViewModel: GraphScreenViewModel,
    fusedLocationClient: FusedLocationProviderClient,
    profileViewModel: ProfileViewModel,
) {
    navigation(startDestination = MainAppScreen.Home.route, route = Graph.mainApp) {
        composable(route = MainAppScreen.Home.route) {
            HomeScreen(
                navController = navController,
                taskDialogViewModel,
                homeScreenViewModel,
                taskScreenViewModel
            )
        }
        composable(route = MainAppScreen.Graph.route) {
            GraphScreen(navController = navController, graphScreenViewModel)
        }
        composable(route = MainAppScreen.Profile.route) {


            ProfileScreen(
                navController = navController,
                userViewModel = userViewModel,
                fusedLocationClient,
                profileViewModel
            )
        }
        composable(route = MainAppScreen.Task.route + "/{taskId}") {
            TaskScreen(navController = navController, taskScreenViewModel)
        }

        composable(route = MainAppScreen.GraphPage.route + "/{userId}" + "/{taskId}") {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val userId = navBackStackEntry?.arguments?.getString("userId")?.toIntOrNull() ?: 0
            val taskId = navBackStackEntry?.arguments?.getString("taskId")?.toIntOrNull() ?: 0

            GraphPageScreen(navController = navController, graphScreenViewModel, userId, taskId)
        }

        composable(route = MainAppScreen.PIGraphPage.route + "/{userId}" + "/{taskId}") {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val userId = navBackStackEntry?.arguments?.getString("userId")?.toIntOrNull() ?: 0
            val taskId = navBackStackEntry?.arguments?.getString("taskId")?.toIntOrNull() ?: 0

            PiChartScreen(navController = navController, graphScreenViewModel, userId, taskId)
        }
    }
}