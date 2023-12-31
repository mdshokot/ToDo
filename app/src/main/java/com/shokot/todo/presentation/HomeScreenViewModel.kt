package com.shokot.todo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shokot.todo.domain.entity.Task
import com.shokot.todo.domain.entity.UserTask
import com.shokot.todo.domain.repository.GraphRepository
import com.shokot.todo.domain.repository.TaskRepository
import com.shokot.todo.domain.repository.UserTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val userTaskRepository: UserTaskRepository,
    private val graphRepository: GraphRepository,
) : ViewModel() {
    var selectedItem by mutableStateOf("")
        private set

    //modo per inserire una task
    var task by mutableStateOf(Task(id = 0, graphName = "prova", type = "prova", userId = 0))
        private set
    // task section

    fun getTaskById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempTask = taskRepository.getTaskById(id)
            if (tempTask !== null) {
                task = tempTask
            }
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskId = taskRepository.insertTask(task)
            //insert in userTask
            val userTask = UserTask(userId = task.userId, taskId = taskId)
        }
    }

    fun doesGraphNameExists(graphName: String): Boolean {
        return taskRepository.doesGraphNameExist(graphName)
    }

    fun updateTitle(title: String) {
        this.task = task.copy(title = title)
    }

    fun updateDescription(description: String) {
        this.task = task.copy(description = description)
    }

    fun updateValue(value: Int?) {
        this.task = task.copy(value = value)
    }


    // select section
    fun setMySelectedItem(selectedItem: String) {
        this.selectedItem = selectedItem
    }
}