package com.shokot.todo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shokot.todo.navigation.graph.authenticationGraph
import com.shokot.todo.navigation.graph.mainAppGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = Graph.authentication) {
        authenticationGraph(navController)
        composable(route = Graph.mainAppHelper) {
            mainAppNavigation()
        }
    }
}

@Composable
private fun mainAppNavigation() {
    val mainAppNavController = rememberNavController()
    val items = listOf(
        BottomNavigationItem(MainAppScreen.Home.route, Icons.Filled.Home, Icons.Default.Home),
        BottomNavigationItem(
            MainAppScreen.Graph.route,
            Icons.Filled.DateRange,
            Icons.Default.DateRange
        ),
        BottomNavigationItem(
            MainAppScreen.Profile.route,
            Icons.Filled.AccountCircle,
            Icons.Default.AccountCircle
        ),
    )
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(selected = index == selectedItemIndex,
                        onClick = {
                            mainAppNavController.navigate(item.tittle)
                            selectedItemIndex = index
                        },
                        label = { Text(text = item.tittle.replaceFirstChar { it.uppercase() }) },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.tittle
                            )
                        })
                }
            }
        }
    ) { padding ->
        MainAppNavHost(mainAppNavController, padding)
    }
}

@Composable
private fun MainAppNavHost(mainAppNavController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = mainAppNavController,
        startDestination = Graph.mainApp,
        modifier = Modifier.padding(padding)
    ) {
        mainAppGraph(mainAppNavController)
    }
}


