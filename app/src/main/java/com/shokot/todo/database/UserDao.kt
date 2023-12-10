package com.shokot.todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.shokot.todo.database.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
     fun insertUser(user: User)

    @Delete
     fun deleteUser(user: User)

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserInformation(email: String): User?

    @Query(" SELECT * FROM user")
    fun getAllUsers() : Flow<List<User>>
}