package com.shokot.todo.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shokot.todo.domain.entity.UserTask

@Dao
interface UserTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserTask(userTask: UserTask)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserTask(userTask: UserTask)

    @Delete
    suspend fun deleteUserTask(userTask: UserTask)

    @Query("SELECT * FROM user_task WHERE id = :id")
    suspend fun getUserTaskById(id: Int): UserTask?

}