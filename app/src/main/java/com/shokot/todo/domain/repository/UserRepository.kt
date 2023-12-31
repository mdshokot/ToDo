package com.shokot.todo.domain.repository

import com.shokot.todo.domain.dao.UserDao
import com.shokot.todo.domain.entity.User
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val dao: UserDao
) {
    suspend fun insertUser(user: User):Long = dao.insertUser(user)
    suspend fun updateUser(user: User) = dao.updateUser(user)
    suspend fun deleteUser(user: User) = dao.deleteUser(user)
     fun getUserById(id: Int) : Flow<User?>  = dao.getUserById(id)
    fun getUserByEmail(email: String) : Flow<User> = dao.getUserByEmail(email)

    fun doesEmailExists(email: String): Flow<Boolean> = dao.doesEmailExists(email)
}