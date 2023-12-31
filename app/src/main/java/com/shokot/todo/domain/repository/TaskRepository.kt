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

    suspend fun deleteTaskFromGraph(taskId: Int) = dao.deleteTaskFromGraph(taskId)
    suspend fun deleteFromUserTask(userId: Int, taskId: Int) =
        dao.deleteFromUserTask(userId, taskId)

    fun getTaskById(id: Int): Flow<Task> = dao.getTaskById(id)

    fun doesGraphNameExist(graphName: String): Flow<Boolean> = dao.doesGraphNameExist(graphName)

    fun getAllTaskOfUser(userId: Int, currDate: String): Flow<List<MyTask>> =
        dao.getAllTaskOfUser(userId, currDate)

    fun getAllTaskByFilter(
        userId: Int,
        currDate: String,
        type: String,
        favorite: Boolean?
    ): Flow<List<MyTask>> = dao.getAllTaskByFilter(userId, currDate, type, favorite)

    fun getAllFavoriteTask(userId: Int, currDate: String): Flow<List<MyTask>> =
        dao.getAllFavoriteTask(userId, currDate)

    fun getAllTaskNormalOrValue(userId: Int, type: String, currDate: String): Flow<List<MyTask>> =
        dao.getAllTaskNormalOrValue(userId, type, currDate)

    suspend fun setTaskCompleted(userId: Int, taskId: Int, currDate: String) =
        dao.setTaskCompleted(userId, taskId, currDate)

    fun getUserTaskById(userId: Int): List<Task> {
        return dao.getAllTaskByUserId(userId)
    }

    suspend fun insertInToGraph(userId: Int, taskId: Int, currentDate: String) = dao.insertInToGraph(userId,taskId,currentDate)
}