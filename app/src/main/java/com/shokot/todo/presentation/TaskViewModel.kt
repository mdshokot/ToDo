package com.shokot.todo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shokot.todo.domain.entity.Task
import com.shokot.todo.domain.entity.User
import com.shokot.todo.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _task =
        MutableStateFlow(Task(id = 0, graphName = "prova", type = "prova", userId = 0))

    val task: MutableStateFlow<Task>
        get() = _task

    fun setMyTask(task: Task?) {
        if (task !== null) {
            this._task.value = task
        }
    }

    fun getTaskById(id: Int): Flow<Task> {
        return taskRepository.getTaskById(id)
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTaskFromGraph(task.id)
            taskRepository.deleteFromUserTask(task.userId, task.id)
            taskRepository.deleteTask(task)
        }
    }

    fun updateTitle(title: String) {
        this._task.value = this._task.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        this._task.value = this._task.value.copy(description = description)
    }

    fun updateFavorite(favorite: Boolean) {
        this._task.value = this._task.value.copy(favorite = favorite)
    }

    fun updateValue(value: String) {
        val tempValue = if (value.trim() == "") {
            null
        } else {
            value.toIntOrNull()
        }
        this._task.value = this._task.value.copy(value = tempValue)
    }

    fun taskCompleted(userId: Int, taskId: Int) {
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.setTaskCompleted(userId, taskId, currentDate)
        }
    }

}