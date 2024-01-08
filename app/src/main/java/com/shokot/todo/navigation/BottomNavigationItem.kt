package com.shokot.todo.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
)
