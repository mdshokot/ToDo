package com.shokot.todo.screen.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun TaskScreen(
    navController: NavController,
) {
    val taskId = navController.currentBackStackEntry
        ?.arguments?.getString("taskId")?.toIntOrNull() ?: 0

    Text(text = "Task ID: $taskId")
}