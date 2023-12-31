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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shokot.todo.ThemeViewModel
import com.shokot.todo.navigation.graph.authenticationGraph
import com.shokot.todo.navigation.graph.mainAppGraph
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.presentation.UserViewModel
import com.shokot.todo.screen.main.components.home.TaskDialogViewModel
import com.shokot.todo.utility.Helper
import com.shokot.todo.utility.PreferencesKeys

@Composable
fun AppNavigation(
    themeViewModel: ThemeViewModel,
    userViewModel: UserViewModel,
    homeScreenViewModel: HomeScreenViewModel
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val currentRoute = navBackStackEntry?.destination?.route
    val preferences: SharedPreferences =
        LocalContext.current.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)

    val taskViewModal = viewModel<TaskDialogViewModel>()
    val hasUser = preferences.contains(PreferencesKeys.USER_ID)
    Scaffold(
        bottomBar = {
            if (Helper.isMainApp(currentRoute)) {
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
            } else {
                //reset the  selected index when returning to the login route
                selectedItemIndex = 0
            }
        },
        floatingActionButton = {
            if (currentRoute == MainAppScreen.Home.route) {
                FloatingActionButton(
                    onClick = { taskViewModal.showDialog() },
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
                themeViewModel,
                userViewModel,
                taskViewModal,
                homeScreenViewModel
            )
        }
    }
}






