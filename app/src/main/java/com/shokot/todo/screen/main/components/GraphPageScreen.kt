package com.shokot.todo.screen.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import co.yml.charts.common.model.Point
import com.shokot.todo.R
import com.shokot.todo.navigation.Graph
import com.shokot.todo.presentation.GraphScreenViewModel
import com.shokot.todo.screen.main.components.graph.CustomLineChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphPageScreen(
    navController: NavController,
    graphScreenViewModel: GraphScreenViewModel,
    userId: Int,
    taskId: Int
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.go_back),
                    style = MaterialTheme.typography.titleLarge
                )
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Graph.mainApp) {
                            popUpTo(Graph.mainApp) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                })
        }
    ) { paddingValues ->
        val graphDataList = graphScreenViewModel.selectLineGraphData(userId, taskId).collectAsState(
            initial = emptyList()
        ).value

        val points: List<Point> = graphDataList.mapIndexed { index, graphData ->
            val dateIndex = index + 1
            Point(dateIndex.toFloat(), graphData.valore.toFloat())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            if(points.isNotEmpty()){
                CustomLineChart(points)
            }
        }
    }
}

