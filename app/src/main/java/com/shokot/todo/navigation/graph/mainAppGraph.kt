package com.shokot.todo.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.shokot.todo.ThemeViewModel
import com.shokot.todo.navigation.Graph
import com.shokot.todo.navigation.MainAppScreen
import com.shokot.todo.screen.main.GraphScreen
import com.shokot.todo.screen.main.HomeScreen
import com.shokot.todo.screen.main.ProfileScreen


fun NavGraphBuilder.mainAppGraph(navController: NavController, themeViewModel: ThemeViewModel) {
    navigation(startDestination = MainAppScreen.Home.route, route = Graph.mainApp) {
        composable(route = MainAppScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = MainAppScreen.Graph.route) {
            GraphScreen(navController = navController)
        }
        composable(route = MainAppScreen.Profile.route) {
            ProfileScreen(navController = navController,themeViewModel = themeViewModel)
        }
    }
}