package com.shokot.todo.domain.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shokot.todo.domain.entity.Graph
import kotlinx.coroutines.flow.Flow

@Dao
interface GraphDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGraph(graph: Graph): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGraph(graph: Graph)

    @Delete
    suspend fun deleteGraph(graph:Graph)

   // @Query("SELECT date, valore FROM graph WHERE user_id = :userId AND task_id = :taskId")
    //fun getGraphDataForUserAndTask(userId: Int, taskId: Int): Flow<List<Pair<String, Int>>>
}