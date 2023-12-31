package com.shokot.todo.domain.repository

import com.shokot.todo.domain.dao.TaskDao
import com.shokot.todo.domain.entity.Task

class TaskRepository(
    private val dao: TaskDao
) {

    suspend fun insertTask(task: Task): Long = dao.insertTask(task)

    suspend fun updateTask(task: Task) = dao.updateTask(task)

    suspend fun deleteTask(task: Task) = dao.deleteTask(task)

    suspend fun getTaskById(id : Int): Task? = dao.getTaskById(id)

    fun doesGraphNameExist(graphName: String): Boolean = dao.doesGraphNameExist(graphName)
}