package com.shokot.todo.utility

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import com.shokot.todo.navigation.BottomNavigationItem
import com.shokot.todo.navigation.MainAppScreen


object Helper {
    val bottomNavigationItems = listOf(
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

    fun isMainApp(route : String?) : Boolean{
        val showBottomNavBar = listOf(
            MainAppScreen.Home.route,
            MainAppScreen.Profile.route,
            MainAppScreen.Graph.route,
        )

        return showBottomNavBar.contains(route)
    }
}
