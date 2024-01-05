package com.shokot.todo.screen.main.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shokot.todo.domain.dao.MyTask
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.shokot.todo.navigation.MainAppScreen
import com.shokot.todo.presentation.TaskViewModel
import com.shokot.todo.utility.TaskType
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun MyTaskCard(
    myTask: MyTask,
    navController: NavController,
    taskViewModal: TaskViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var isSwiped by remember { mutableStateOf(myTask.completed) }

    val color = if (myTask.completed) {
        Color.Green
    } else {
        MaterialTheme.colorScheme.primaryContainer
    }
    var backgroundColor by remember {
        mutableStateOf(color)
    }
    val modify = SwipeAction(
        icon = {
        },
        background = Color.Green,
        isUndo = true,
        onSwipe = {
            coroutineScope.launch {
                taskViewModal.taskCompleted(myTask.userId, myTask.taskId)
                backgroundColor = Color.Green
                isSwiped = true
            }
        },
    )

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        SwipeableActionsBox(
            startActions = if (isSwiped) {
                listOf()
            } else {
                listOf(modify)
            },
            swipeThreshold = 150.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(backgroundColor)
                    .padding(10.dp, 0.dp)
                    .clickable {
                        navController.navigate(MainAppScreen.Task.route + "/${myTask.taskId}")
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = myTask.title, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (myTask.favorite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = ""
                        )
                    }
                    if (myTask.type == TaskType.VALUE) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)  // Adjust the size of the box as needed
                                .background(
                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                    shape = CircleShape
                                )
                                .padding(8.dp),  // Adjust the padding as needed
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = myTask.value.toString(),  // Replace with your actual number
                                color = MaterialTheme.colorScheme.inversePrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

