package com.shokot.todo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeScreenViewModel : ViewModel() {
    var selectedItem by mutableStateOf("")
        private set

    fun setMySelectedItem(selectedItem: String) {
        this.selectedItem = selectedItem
    }
}