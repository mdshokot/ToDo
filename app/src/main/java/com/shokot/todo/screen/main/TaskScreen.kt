package com.shokot.todo.screen.main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shokot.todo.R
import com.shokot.todo.domain.entity.Task
import com.shokot.todo.presentation.TaskViewModel
import com.shokot.todo.utility.Helper.CustomOutlinedTextField
import com.shokot.todo.utility.TaskType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navController: NavController,
    taskViewModel: TaskViewModel,
) {
    val taskId = navController.currentBackStackEntry
        ?.arguments?.getString("taskId")?.toIntOrNull() ?: 0
    val task = taskViewModel.getTaskById(taskId).collectAsState(initial = null).value
    val context = LocalContext.current
    if (task !== null) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(task.graphName, style = MaterialTheme.typography.titleMedium)
                },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    })
            }
        ) {
            TaskForm(task, it, taskViewModel, navController, context)
        }
    }
}

@Composable
fun TaskForm(
    task: Task,
    paddingValues: PaddingValues,
    taskViewModel: TaskViewModel,
    navController: NavController,
    context: Context
) {
    var title by rememberSaveable { mutableStateOf(task.title) }
    var description by rememberSaveable { mutableStateOf(task.description) }
    var isFavorite by rememberSaveable { mutableStateOf(task.favorite) }
    var value by rememberSaveable { mutableStateOf(task.value.toString()) }

    val coroutineScope = rememberCoroutineScope()
    val savedStr = stringResource(id = R.string.task_saved_success)
    var openDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        if (openDialog) {
            confirmDeleteDialog(taskViewModel, { openDialog = false }, navController, context,task)
        }
        // Title
        CustomOutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = R.string.task_title,
            icon = Icons.Default.Info,
            keyboardType = KeyboardType.Text,
            space = 5
        )

        // Description
        CustomOutlinedTextField(
            value = task.description,
            onValueChange = { description = it },
            label = R.string.task_descrition,
            icon = Icons.Default.Info,
            keyboardType = KeyboardType.Text,
            space = 5
        )

        if (task.type == TaskType.VALUE) {
            // Value
            CustomOutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = R.string.task_value,
                icon = Icons.Default.Info,
                keyboardType = KeyboardType.Number,
                space = 5
            )
        }
        // Checkbox for marking as favorite
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Mark as Favorite", style = MaterialTheme.typography.bodyMedium)
            Checkbox(
                checked = isFavorite,
                onCheckedChange = { isFavorite = it },
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Spacer to push buttons to the bottom
        Spacer(modifier = Modifier.weight(1f))

        // Buttons (Delete and Save)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            ElevatedButton(
                onClick = {
                    //delete the task and all the rest of the staff
                    openDialog = true
                    // Toast.makeText(context, deleteStr, Toast.LENGTH_SHORT).show()
                    // navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterStart),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(text = stringResource(id = R.string.delete_task))
            }

            ElevatedButton(
                onClick = {
                    //save operation
                    coroutineScope.launch {
                        val tempValue = if (value.trim() == "") {
                            null
                        } else {
                            value.toIntOrNull()
                        }
                        taskViewModel.updateTask(
                            task.copy(
                                title = title,
                                description = description,
                                favorite = isFavorite,
                                value = tempValue
                            )
                        )
                        navController.popBackStack()
                        Toast.makeText(context, savedStr, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}

@Composable
fun confirmDeleteDialog(
    taskViewModel: TaskViewModel,
    onClose: () -> Unit,
    navController: NavController,
    context: Context,
    task: Task
) {
    val deleteStr = stringResource(id = R.string.task_delete_success)
    val coroutineScope = rememberCoroutineScope()
    AlertDialog(
        text = {
            Text(
                text = stringResource(id = R.string.delete_task_body),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        title = {
            Text(text = stringResource(id = R.string.delete_task_title), style = MaterialTheme.typography.titleMedium)
        },
        onDismissRequest = { onClose() },
        confirmButton = {
            ElevatedButton(
                onClick = {
                    // Handle confirmation logic here
                    coroutineScope.launch {
                        taskViewModel.deleteTask(task)
                        onClose()
                        Toast.makeText(context, deleteStr, Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                    onClose()
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(text = stringResource(id = R.string.delete))
            }
        }, dismissButton = {
            OutlinedButton(
                onClick = {
                    onClose()
                }
            ) {
                Text(text =stringResource(id = R.string.cancel))
            }
        })

}