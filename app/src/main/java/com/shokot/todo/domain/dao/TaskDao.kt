package com.shokot.todo.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shokot.todo.domain.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * from task where task.id = :id")
    fun getTaskById(id : Int): Flow<Task>

    @Query("SELECT EXISTS(SELECT * FROM task WHERE graph_name = :graphName)")
    fun doesGraphNameExist(graphName: String): Flow<Boolean>

    @Query("SELECT value FROM task WHERE id = :taskId")
    fun getTaskValueById(taskId: Int): Flow<Int>

    @Query("SELECT * from task WHERE task.user_id = :userId")
    fun getAllTaskByUserId(userId: Int): List<Task>

    @Query("""
         SELECT
t.title AS title,
t.description AS description,
t.graph_name AS graphName,
t.type AS type,
t.value AS value,
t.favorite AS favorite,
ut.completed AS completed,
ut.task_id AS taskId
         FROM user_task ut
INNER JOIN task t ON t.id = ut.task_id and ut.date = :currDate and ut.user_id = :userId
     """)
    fun getAllTaskOfUser(userId:Int,currDate:String): Flow<List<MyTask>>

    @Query("""
         SELECT
t.title AS title,
t.description AS description,
t.graph_name AS graphName,
t.type AS type,
t.value AS value,
t.favorite AS favorite,
ut.completed AS completed,
ut.task_id AS taskId
FROM user_task ut
INNER JOIN task t ON t.id = ut.task_id 
    AND ut.date = :currDate 
    AND ut.user_id = :userId 
	AND (:type == 'all' OR t.type = :type)
	AND (:favorite is null or t.favorite = :favorite)
     """)
    fun getAllTaskByFilter(userId:Int,currDate:String,type: String,favorite:Boolean?): Flow<List<MyTask>>

    @Query("DELETE FROM graph  WHERE graph.task_id = :taskId")
    suspend fun deleteTaskFromGraph(taskId: Int)

    @Query("DELETE FROM user_task  WHERE user_id = :userId AND task_id = :taskId ")
    suspend fun deleteFromUserTask(userId: Int, taskId:Int)

}

data class MyTask(
    val title: String,
    val description: String,
    val graphName: String,
    val type: String,
    val value: Int?,
    val favorite: Boolean,
    val completed: Boolean,
    val taskId: Int
)