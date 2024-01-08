package com.shokot.todo.screen.main

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shokot.todo.R
import com.shokot.todo.domain.dao.MyTask
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.presentation.TaskViewModel
import com.shokot.todo.screen.main.components.home.DropDownUI
import com.shokot.todo.screen.main.components.home.MyTaskCard
import com.shokot.todo.screen.main.components.home.TaskDialog
import com.shokot.todo.screen.main.components.home.TaskDialogViewModel
import com.shokot.todo.utility.PreferencesKeys
import com.shokot.todo.utility.SelectOption

@Composable
fun HomeScreen(
    navController: NavController,
    taskDialogViewModel: TaskDialogViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    taskScreenViewModel: TaskViewModel
) {
    val preferences: SharedPreferences = LocalContext.current.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)
    val showDialog = taskDialogViewModel._showModal.collectAsState().value
    val defaultOption =  SelectOption("all", stringResource(id = R.string.all))
    homeScreenViewModel.userId = preferences.getInt(PreferencesKeys.USER_ID, 0)
     val myTasks = homeScreenViewModel.tasks.collectAsState(initial = emptyList()).value
    val filterItems = listOf(
        defaultOption,
        SelectOption("normal", stringResource(id = R.string.normal)),
        SelectOption("value", stringResource(id = R.string.task_value)),
        SelectOption("favorite", stringResource(id = R.string.favorite)),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) {
        Log.i("HOME SCREEN","home screen refresh")
        DropDownUI(
            options = filterItems,
            selectedOption = homeScreenViewModel.selectedItem,
            { it -> homeScreenViewModel.setMySelectedItem(it.option); homeScreenViewModel.setSortType(it.id)},
            defaultOption
        )
        Spacer(modifier = Modifier.height(5.dp))
        ToDoList(myTasks,navController,taskScreenViewModel)
        //lazy column
        if (showDialog) {
            TaskDialog(taskDialogViewModel,homeScreenViewModel)
        }
    }
}

@Composable
fun ToDoList(
    myTasks: List<MyTask>,
    navController: NavController,
    taskScreenViewModel: TaskViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(myTasks) { myTask ->
            MyTaskCard(myTask = myTask,navController,taskScreenViewModel)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}











