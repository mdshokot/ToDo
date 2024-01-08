package com.shokot.todo.screen.main

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shokot.todo.R
import com.shokot.todo.domain.entity.Task
import com.shokot.todo.navigation.MainAppScreen
import com.shokot.todo.presentation.GraphScreenViewModel
import com.shokot.todo.utility.PreferencesKeys

@Composable
fun GraphScreen(navController: NavController, graphScreenViewModel: GraphScreenViewModel) {

    val preferences: SharedPreferences =
        LocalContext.current.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)
    val userId = preferences.getInt(PreferencesKeys.USER_ID, 0)
    val myTasks = graphScreenViewModel.getAllTaskById(userId = userId)
        .collectAsState(initial = emptyList()).value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(myTasks) { myTask ->
                GraphTaskCardUI(navController, myTask)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun GraphTaskCardUI(
    navController: NavController,
    task: Task,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(10.dp, 15.dp, 10.dp, 10.dp)
            .height(75.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .clickable {
                    if (task.type == "normal") {
                        navController.navigate(MainAppScreen.PIGraphPage.route + "/${task.userId}/${task.id}")
                    } else {
                        navController.navigate(MainAppScreen.GraphPage.route + "/${task.userId}/${task.id}")
                    }
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.graphName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Card(
                modifier = Modifier.size(48.dp),
                shape = CircleShape
            ) {
                Image(
                    painterResource(
                        if (task.type == "normal") {
                            R.drawable.baseline_pie_chart_24
                        } else {
                            R.drawable.baseline_auto_graph_24
                        }
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

            }
        }
    }
}