package com.shokot.todo.domain.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shokot.todo.domain.entity.Graph
import com.shokot.todo.domain.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface GraphDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGraph(graph: Graph): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGraph(graph: Graph)

    @Delete
    suspend fun deleteGraph(graph:Graph)

    @Query("SELECT * from task WHERE task.user_id = :userId")
    fun getAllTaskByUserId(userId: Int): Flow<List<Task>>

    @Query("""
        SELECT date, valore
        FROM graph
        where user_id = :userId and task_id = :taskId
        ORDER BY date ASC
    """)
    fun selectLineGraphData(userId:Int,taskId:Int):Flow<List<LineGraphData>>

    @Query("""
        SELECT completed, COUNT(completed) as count
        FROM user_task UT
        LEFT JOIN task T ON T.id = UT.task_id 
        WHERE UT.user_id = :userId AND UT.task_id = :taskId AND T.type = "normal"
        GROUP BY completed
    """)
    fun getPiChartData(userId: Int, taskId: Int): Flow<List<PieChartData>>
}

data class  LineGraphData(
    val date:String,
    val valore:Int
)

data class PieChartData(
    val completed: Int,
    val count: Int
)