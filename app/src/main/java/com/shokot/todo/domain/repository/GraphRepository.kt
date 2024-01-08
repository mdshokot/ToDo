package com.shokot.todo.domain.repository

import com.shokot.todo.domain.dao.GraphDao
import com.shokot.todo.domain.dao.LineGraphData
import com.shokot.todo.domain.dao.PieChartData
import com.shokot.todo.domain.entity.Graph
import com.shokot.todo.domain.entity.Task
import kotlinx.coroutines.flow.Flow

class GraphRepository(
    private val graphDao: GraphDao
) {

    suspend fun insertGraph(graph: Graph): Long = graphDao.insertGraph(graph)

    suspend fun updateGraph(graph: Graph) = graphDao.updateGraph(graph)

    suspend fun deleteGraph(graph: Graph) = graphDao.deleteGraph(graph)

    fun getAllTaskByUserId(userId:Int): Flow<List<Task>> = graphDao.getAllTaskByUserId(userId)

    fun selectLineGraphData(userId:Int,taskId:Int):Flow<List<LineGraphData>> = graphDao.selectLineGraphData(userId,taskId)

    fun getPiChartData(userId: Int, taskId: Int): Flow<List<PieChartData>> = graphDao.getPiChartData(userId, taskId)
}