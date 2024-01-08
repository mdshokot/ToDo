package com.shokot.todo.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shokot.todo.R
import com.shokot.todo.navigation.Graph
import com.shokot.todo.presentation.GraphScreenViewModel
import com.shokot.todo.screen.main.components.graph.CustomPiChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PiChartScreen(
    navController: NavController,
    graphScreenViewModel: GraphScreenViewModel,
    userId: Int,
    taskId: Int
) {

    val piChartData = graphScreenViewModel.getAllPIData(userId, taskId)
        .collectAsState(initial = emptyList()).value
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (piChartData.isNotEmpty()) {
                val completedCount = piChartData.find { it.completed == 1 }?.count ?: 0
                val notCompletedCount = piChartData.find { it.completed == 0 }?.count ?: 0
                CustomPiChart(completed = completedCount, notCompleted = notCompletedCount)
                // Row with color squares and counts
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Completed section
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color(0xFF20BF55))
                    )
                    Text(
                        text = " $completedCount " + stringResource(id = R.string.completed),
                        modifier = Modifier
                            .alignByBaseline(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    // Not Completed section
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color(0xFF5D615E))
                    )
                    Text(
                        text = " $notCompletedCount " + stringResource(id = R.string.not_completed),
                        modifier = Modifier.alignByBaseline(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }

        }
    }

}