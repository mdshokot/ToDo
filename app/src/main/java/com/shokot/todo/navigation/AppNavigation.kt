package com.shokot.todo.navigation

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.shokot.todo.navigation.graph.authenticationGraph
import com.shokot.todo.navigation.graph.mainAppGraph
import com.shokot.todo.presentation.GraphScreenViewModel
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.presentation.TaskViewModel
import com.shokot.todo.presentation.UserViewModel
import com.shokot.todo.screen.main.components.home.TaskDialogViewModel
import com.shokot.todo.utility.Helper
import com.shokot.todo.utility.PreferencesKeys

@Composable
fun AppNavigation(
    userViewModel: UserViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    taskViewModel: TaskViewModel,
    taskDialogViewModal: TaskDialogViewModel,
    graphScreenViewModel: GraphScreenViewModel,
    fusedLocationClient: FusedLocationProviderClient,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val currentRoute = navBackStackEntry?.destination?.route
    val preferences: SharedPreferences =
        LocalContext.current.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)

    val hasUser = preferences.contains(PreferencesKeys.USER_ID)
    Scaffold(
        bottomBar = {
            if (Helper.isMainApp(currentRoute)) {
                NavigationBar {
                    Helper.bottomNavigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(selected = index == selectedItemIndex,
                            onClick = {
                                navController.navigate(item.title)
                                selectedItemIndex = index
                            },
                            label = { Text(text = item.title.replaceFirstChar { it.uppercase() }) },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            })
                    }
                }
            } else {
                //reset the  selected index when returning to the login route
                selectedItemIndex = 0
            }
        },
        floatingActionButton = {
            if (currentRoute == MainAppScreen.Home.route) {
                FloatingActionButton(
                    onClick = { taskDialogViewModal._showModal.value = true },
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = if (hasUser) Graph.mainApp else Graph.authentication,
            modifier = Modifier.padding(padding)
        ) {
            authenticationGraph(navController, userViewModel)
            mainAppGraph(
                navController,
                userViewModel,
                taskDialogViewModal,
                homeScreenViewModel,
                taskViewModel,
                graphScreenViewModel,
                fusedLocationClient,
            )
        }
    }
}






