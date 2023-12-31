package com.shokot.todo.domain.repository

import com.shokot.todo.domain.dao.GraphDao
import com.shokot.todo.domain.entity.Graph

class GraphRepository(
    private val graphDao: GraphDao
) {

    suspend fun insertGraph(graph: Graph): Long = graphDao.insertGraph(graph)

    suspend fun updateGraph(graph: Graph) = graphDao.updateGraph(graph)

    suspend fun deleteGraph(graph: Graph) = graphDao.deleteGraph(graph)

}