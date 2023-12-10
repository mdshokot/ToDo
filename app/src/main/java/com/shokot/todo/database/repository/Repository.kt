package com.shokot.todo.database.repository

import com.shokot.todo.database.UserDao
import com.shokot.todo.database.entity.User

class Repository(private val userDao : UserDao) {
    val userList = userDao.getAllUsers()
    fun getUser(email: String) = userDao.getUserInformation(email)
    fun insertUser(user: User) = userDao.insertUser(user)
    fun deleteUser(user: User) = userDao.deleteUser(user)
}