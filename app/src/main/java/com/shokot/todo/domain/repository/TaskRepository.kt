package com.shokot.todo.domain.repository

import com.shokot.todo.domain.dao.MyTask
import com.shokot.todo.domain.dao.TaskDao
import com.shokot.todo.domain.entity.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val dao: TaskDao
) {

    suspend fun insertTask(task: Task): Long = dao.insertTask(task)

    suspend fun updateTask(task: Task) = dao.updateTask(task)

    suspend fun deleteTask(task: Task) = dao.deleteTask(task)

     fun getTaskById(id : Int): Flow<Task> = dao.getTaskById(id)

    fun doesGraphNameExist(graphName: String): Flow<Boolean> = dao.doesGraphNameExist(graphName)

    fun getAllTaskOfUser(userId:Int,currDate:String): Flow<List<MyTask>> = dao.getAllTaskOfUser(userId,currDate)
}