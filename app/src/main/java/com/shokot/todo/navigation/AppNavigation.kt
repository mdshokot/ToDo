package com.shokot.todo.navigation

import androidx.compose.foundation.layout.padding
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shokot.todo.ThemeViewModel
import com.shokot.todo.navigation.graph.authenticationGraph
import com.shokot.todo.navigation.graph.mainAppGraph
import com.shokot.todo.utility.Helper

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if(Helper.isMainApp(currentRoute)){
                NavigationBar {
                    Helper.bottomNavigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(selected = index == selectedItemIndex,
                            onClick = {
                                navController.navigate(item.tittle)
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

            }else{
                //reset the  selected index when returning to the login route
                selectedItemIndex = 0
            }
        }
    ) { padding ->
        NavHost(navController = navController,
            startDestination = Graph.authentication,
            modifier = Modifier.padding(padding)
        ) {
            authenticationGraph(navController)
            mainAppGraph(navController,themeViewModel)
        }
    }
}






