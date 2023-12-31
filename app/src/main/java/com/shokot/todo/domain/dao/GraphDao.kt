package com.shokot.todo.domain.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.shokot.todo.domain.entity.Graph

@Dao
interface GraphDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGraph(graph: Graph): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGraph(graph: Graph)

    @Delete
    suspend fun deleteGraph(graph:Graph)

}