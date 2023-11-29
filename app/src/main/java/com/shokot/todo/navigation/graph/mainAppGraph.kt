package com.shokot.todo.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.shokot.todo.navigation.MainAppScreen
import com.shokot.todo.screen.main.GraphScreen
import com.shokot.todo.screen.main.HomeScreen
import com.shokot.todo.screen.main.ProfileScreen

fun NavGraphBuilder.mainAppGraph(navController: NavController) {
    navigation(startDestination = MainAppScreen.Home.route, route = "mainApp") {
        composable(route = MainAppScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = MainAppScreen.Graph.route) {
            GraphScreen(navController = navController)
        }
        composable(route = MainAppScreen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}