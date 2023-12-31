package com.shokot.todo.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shokot.todo.domain.entity.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * from task where task.id = :id")
    suspend fun getTaskById(id : Int): Task?

    @Query("SELECT EXISTS(SELECT * FROM task WHERE graph_name = :graphName)")
    fun doesGraphNameExist(graphName: String): Boolean

    @Query("SELECT value FROM task WHERE id = :taskId")
    suspend fun getTaskValueById(taskId: Int): Int?

    @Query("SELECT * from task WHERE task.user_id = :userId")
    suspend fun getAllTaskByUserId(userId: Int): List<Task>
}