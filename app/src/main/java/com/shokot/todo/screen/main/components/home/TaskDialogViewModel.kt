package com.shokot.todo.screen.main.components.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskDialogViewModel : ViewModel() {

     var _showModal = MutableStateFlow(false)
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var graphName by mutableStateOf("")
        private set
    var value by mutableStateOf("")
        private set
    var selectedItem by mutableStateOf("")
        private set



    fun showDialog(){
        _showModal.value = true
    }

    fun hideDialog(){
        _showModal.value = false
    }

    fun setMyTitle(title: String) {
        this.title = title
    }

    fun setMyDescription(description: String) {
        this.description = description
    }

    fun setMyGraphName(graphName: String) {
        this.graphName = graphName
    }

    fun setMyValue(value: String) {
        this.value = value
    }
    fun clearAllFields(){
        this.title = ""
        this.description = ""
        this.graphName = ""
        this.value = ""
    }
    fun setMySelectedItem(selectedItem: String) {
        this.selectedItem = selectedItem
    }
    fun isNormal(type : String):Boolean{
        return this.selectedItem == type
    }
}