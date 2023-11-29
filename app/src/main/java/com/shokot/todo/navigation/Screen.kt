package com.shokot.todo.navigation


sealed class Screen(val route: String)

sealed class AuthenticationScreen(route: String) : Screen(route)  {
    object Login : Screen("login")
    object Registration : Screen("registration")
}

sealed class MainAppScreen(route: String) : Screen(route) {
    object Profile : Screen("profile")
    object Home : Screen("home")
    object Graph : Screen("graph")
}
