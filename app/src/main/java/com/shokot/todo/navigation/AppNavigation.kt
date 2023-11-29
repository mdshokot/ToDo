package com.shokot.todo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.shokot.todo.navigation.graph.authenticationGraph
import com.shokot.todo.navigation.graph.mainAppGraph

@Composable
fun AppNavigation(){
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = AuthenticationScreen.Login.route) {
        authenticationGraph(navController);
        mainAppGraph(navController);
    }
}