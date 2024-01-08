package com.shokot.todo.presentation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import co.yml.charts.common.model.Point
import com.shokot.todo.domain.dao.LineGraphData
import com.shokot.todo.domain.dao.MyTask
import com.shokot.todo.domain.dao.PieChartData
import com.shokot.todo.domain.entity.Task
import com.shokot.todo.domain.repository.GraphRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import javax.inject.Inject


@HiltViewModel
class GraphScreenViewModel  @Inject constructor(
    private val graphRepository: GraphRepository,
) : ViewModel() {

    fun getAllTaskById(userId:Int):Flow<List<Task>>{
        return graphRepository.getAllTaskByUserId(userId)
    }

    fun selectLineGraphData(userId: Int,taskId:Int):Flow<List<LineGraphData>>{
        return graphRepository.selectLineGraphData(userId, taskId)
    }
    fun getAllPIData(userId: Int,taskId:Int): Flow<List<PieChartData>>{
        return graphRepository.getPiChartData(userId,taskId)
    }
    
}