package com.shokot.todo.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.shokot.todo.navigation.AuthenticationScreen
import com.shokot.todo.screen.authentication.LoginScreen
import com.shokot.todo.screen.authentication.RegistrationScreen

fun NavGraphBuilder.authenticationGraph(navController: NavController) {
    navigation(startDestination = AuthenticationScreen.Login.route, route = "authentication") {
        composable(route = AuthenticationScreen.Login.route) {
            LoginScreen(navController = navController);
        }
        composable(route = AuthenticationScreen.Registration.route) {
            RegistrationScreen(navController = navController)
        }
    }
}