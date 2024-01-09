package com.shokot.todo.screen.main.components.home

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shokot.todo.R
import com.shokot.todo.domain.entity.Task
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.utility.Helper.CustomOutlinedTextField
import com.shokot.todo.utility.PreferencesKeys
import com.shokot.todo.utility.TaskType
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


@Composable
fun TaskDialog(taskDialogViewModel: TaskDialogViewModel, homeScreenViewModel: HomeScreenViewModel) {
    Dialog(onDismissRequest = {
        taskDialogViewModel._showModal.value = false
        taskDialogViewModel.clearAllFields()
    }
    ) {
        CustomDialogUI(taskDialogViewModel, homeScreenViewModel)
    }
}

@Composable
fun CustomDialogUI(taskDialogViewModel: TaskDialogViewModel, homeScreenViewModel: HomeScreenViewModel) {
    val preferences: SharedPreferences =
        LocalContext.current.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)
    val userId = preferences.getInt(PreferencesKeys.USER_ID, -1)
    val items = stringArrayResource(R.array.task_select_option)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val successMsg = stringResource(id = R.string.add_task_succes)
    val normalType = stringResource(id = R.string.normal)
    taskDialogViewModel.setMySelectedItem(normalType)
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.padding(10.dp, 15.dp, 10.dp, 10.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.add_task),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .height(350.dp)
                    .padding(10.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false)
            ) {
                //task type dropdown
                CustomDropDown(
                    textFieldLabelRes = R.string.select_task_type,
                    defaultOption = R.string.normal,
                    items = items,
                    selectedItem = taskDialogViewModel.selectedItem,
                    onSelectedItem = { taskDialogViewModel.setMySelectedItem(it) }
                )
                Spacer(modifier = Modifier.height(5.dp))
                //title
                CustomOutlinedTextField(
                    value = taskDialogViewModel.title,
                    onValueChange = {
                        taskDialogViewModel.setMyTitle(it)
                    },
                    label = R.string.task_title,
                    icon = Icons.Default.Info,
                    keyboardType = KeyboardType.Text,
                    space = 5
                )
                //description
                CustomOutlinedTextField(
                    value = taskDialogViewModel.description,
                    onValueChange = {
                        taskDialogViewModel.setMyDescription(it)
                    },
                    label = R.string.task_descrition,
                    icon = Icons.Default.Info,
                    keyboardType = KeyboardType.Text,
                    space = 5
                )
                //graph name
                CustomOutlinedTextField(
                    value = taskDialogViewModel.graphName,
                    onValueChange = {
                        taskDialogViewModel.setMyGraphName(it)
                    },
                    label = R.string.task_graph_name,
                    icon = Icons.Default.Info,
                    keyboardType = KeyboardType.Text,
                    space = 5
                )
                if (taskDialogViewModel.selectedItem == stringResource(id = R.string.task_value)) {
                    //Value
                    CustomOutlinedTextField(
                        value = taskDialogViewModel.value,
                        onValueChange = {
                            taskDialogViewModel.setMyValue(it)
                        },
                        label = R.string.task_value,
                        icon = Icons.Default.Info,
                        keyboardType = KeyboardType.Number,
                        space = 5
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(
                    onClick = {
                        taskDialogViewModel.hideDialog()
                        taskDialogViewModel.clearAllFields()
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(
                    onClick = {
                        //check if graph name already exists
                        coroutineScope.launch {
                            val doesGraphNameExists =
                                homeScreenViewModel.doesGraphNameExists(graphName = taskDialogViewModel.graphName)
                            doesGraphNameExists.firstOrNull()?.let {
                                if (it) {
                                    Toast.makeText(
                                        context,
                                        "Graph name already exists",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    handleAddTask(
                                        context,
                                        homeScreenViewModel,
                                        taskDialogViewModel,
                                        userId,
                                        successMsg,
                                        normalType
                                    )
                                }
                            } ?: run {
                                handleAddTask(
                                    context,
                                    homeScreenViewModel,
                                    taskDialogViewModel,
                                    userId,
                                    successMsg,
                                    normalType
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.add),
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}

private fun handleAddTask(
    context: Context,
    homeScreenViewModel: HomeScreenViewModel,
    taskDialogViewModel: TaskDialogViewModel,
    userId: Int,
    successMsg: String,
    normalType: String
) {
    val value = taskDialogViewModel.value.toIntOrNull()
    val task = Task(
        title = taskDialogViewModel.title,
        description = taskDialogViewModel.description,
        value = value,
        userId = userId,
        graphName = taskDialogViewModel.graphName,
        type = if (taskDialogViewModel.isNormal(normalType)) {
            TaskType.NORMAL
        } else {
            TaskType.VALUE
        }
    )
    homeScreenViewModel.insertTask(task)
    Toast.makeText(context, successMsg, Toast.LENGTH_SHORT).show()
    taskDialogViewModel._showModal.value = false
    taskDialogViewModel.clearAllFields()
}