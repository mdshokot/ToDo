package com.shokot.todo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shokot.todo.domain.dao.MyTask
import com.shokot.todo.domain.entity.Task
import com.shokot.todo.domain.entity.UserTask
import com.shokot.todo.domain.repository.GraphRepository
import com.shokot.todo.domain.repository.TaskRepository
import com.shokot.todo.domain.repository.UserTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    fun getAllTaskOfUser(userId:Int,currDate:String):Flow<List<MyTask>>{

        return taskRepository.getAllTaskOfUser(userId,currDate)
    }

    //modo per inserire una task
    var task by mutableStateOf(Task(graphName = "prova", type = "prova", userId = 0))
        private set
    // task section

    fun getTaskById(id: Int): Flow<Task> {
            return  taskRepository.getTaskById(id)
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskId = taskRepository.insertTask(task)
            //insert in userTask
            val userTask = UserTask(userId = task.userId, taskId = taskId.toInt())
            userTaskRepository.insertUserTask(userTask)
        }
    }

    fun doesGraphNameExists(graphName: String): Flow<Boolean> {
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