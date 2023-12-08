package com.shokot.todo.navigation.graph

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.shokot.todo.navigation.AuthenticationScreen
import com.shokot.todo.navigation.Graph
import com.shokot.todo.screen.authentication.LoginScreen
import com.shokot.todo.screen.authentication.RegistrationScreen


fun NavGraphBuilder.authenticationGraph(navController: NavController) {
    navigation(startDestination = AuthenticationScreen.Login.route, route = Graph.authentication) {
        composable(route = AuthenticationScreen.Login.route) {
            LoginScreen(navController = navController);
        }
        composable(route = AuthenticationScreen.Registration.route) {
            RegistrationScreen(navController = navController)
        }
    }
}