package com.shokot.todo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shokot.todo.R
import com.shokot.todo.domain.dao.MyTask
import com.shokot.todo.domain.entity.Task
import com.shokot.todo.domain.entity.UserTask
import com.shokot.todo.domain.repository.GraphRepository
import com.shokot.todo.domain.repository.TaskRepository
import com.shokot.todo.domain.repository.UserTaskRepository
import com.shokot.todo.utility.SelectOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val userTaskRepository: UserTaskRepository,
    private val graphRepository: GraphRepository,
) : ViewModel() {
    var selectedItem by mutableStateOf("")
        private set

    var sortType = MutableStateFlow("all")
        private set

    fun setSortType(newSortType: String) {
        sortType.value = newSortType
    }

    private val _userId = MutableStateFlow(0)
    var userId: Int
        get() = _userId.value
        set(value) {
            _userId.value = value
        }

     val tasks : Flow<List<MyTask>> = sortType.flatMapLatest { sortType ->
    // Replace with your actual user ID
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        when (sortType) {
            "normal" -> taskRepository.getAllTaskNormalOrValue(userId,"normal",currentDate)
            "value" -> taskRepository.getAllTaskNormalOrValue(userId,"value",currentDate)
            "all" ->  taskRepository.getAllTaskOfUser(userId,currentDate)
            "favorite" -> taskRepository.getAllFavoriteTask(userId,currentDate)
            else ->  taskRepository.getAllTaskOfUser(userId,currentDate)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


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

    // select section
    fun setMySelectedItem(selectedItem: String) {
        this.selectedItem = selectedItem
    }
}