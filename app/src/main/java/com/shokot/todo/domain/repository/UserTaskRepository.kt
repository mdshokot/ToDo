package com.shokot.todo.domain.repository

import com.shokot.todo.domain.dao.UserTaskDao
import com.shokot.todo.domain.entity.UserTask

class UserTaskRepository(
    private val dao: UserTaskDao,
) {

    suspend fun insertUserTask(userTask: UserTask) = dao.insertUserTask(userTask)

    suspend fun updateUserTask(userTask: UserTask) = dao.updateUserTask(userTask)

    suspend fun deleteUserTask(userTask: UserTask) = dao.deleteUserTask(userTask)

    suspend fun getUserTaskById(id: Int): UserTask? = dao.getUserTaskById(id)

     fun getUserTaskByUserIdAndDate(userId: Int, currentDate: String): List<UserTask> = dao.getUserTaskByUserIdAndDate(userId,currentDate)


}