package com.shokot.todo.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.shokot.todo.navigation.AuthenticationScreen
import com.shokot.todo.navigation.Graph
import com.shokot.todo.presentation.RegistrationViewModel
import com.shokot.todo.screen.authentication.LoginScreen
import com.shokot.todo.screen.authentication.RegistrationScreen


fun NavGraphBuilder.authenticationGraph(
    navController: NavController,
    registrationViewModel: RegistrationViewModel
) {
    navigation(startDestination = AuthenticationScreen.Login.route, route = Graph.authentication) {
        composable(route = AuthenticationScreen.Login.route) {
            LoginScreen(navController = navController,registrationViewModel);
        }
        composable(route = AuthenticationScreen.Registration.route) {
            RegistrationScreen(navController = navController,registrationViewModel)
        }
    }
}