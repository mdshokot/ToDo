package com.shokot.todo.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shokot.todo.domain.entity.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user where user.id = :id")
     fun getUserById(id: Int): Flow<User?>

    @Query("SELECT * FROM user where user.email = :email")
     fun getUserByEmail(email: String) : Flow<User>

    @Query("SELECT * FROM user")
     fun getAllUsers(): Flow<List<User>>

     @Query("SELECT EXISTS(SELECT * from user WHERE user.email = :email)")
     fun doesEmailExists(email: String): Flow<Boolean>

     //Todo handle password hashing
     //todo handle when user is not present
     //todo handle email already exists
}