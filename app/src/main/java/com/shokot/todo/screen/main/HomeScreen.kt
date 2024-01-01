package com.shokot.todo.screen.main

import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shokot.todo.R
import com.shokot.todo.domain.dao.MyTask
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.screen.main.components.home.CustomDropDown
import com.shokot.todo.screen.main.components.home.MyTaskCard
import com.shokot.todo.screen.main.components.home.TaskDialog
import com.shokot.todo.screen.main.components.home.TaskDialogViewModel
import com.shokot.todo.utility.PreferencesKeys
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    navController: NavController,
    taskViewModal: TaskDialogViewModel,
    homeScreenViewModel: HomeScreenViewModel
) {
    val items = stringArrayResource(R.array.task_filter_options)
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val preferences: SharedPreferences =
        LocalContext.current.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)
    val userId = preferences.getInt(PreferencesKeys.USER_ID, 0)
    val myTasks  = homeScreenViewModel.getAllTaskOfUser(userId,currentDate).collectAsState(initial = emptyList()).value

    //val myTasksFiltered  = homeScreenViewModel.getAllTaskByFilter(userId,currentDate,type="normal",favorite = null).collectAsState(initial = emptyList()).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) {
        CustomDropDown(
            textFieldLabelRes = R.string.select_label,
            items = items,
            selectedItem = homeScreenViewModel.selectedItem,
            onSelectedItem = { homeScreenViewModel.setMySelectedItem(it) },
            defaultOption = R.string.all,
        )
        Spacer(modifier = Modifier.height(5.dp))
        ToDoList(myTasks,navController)
        //lazy column
        if (taskViewModal.showModal) {
            TaskDialog(taskViewModal,homeScreenViewModel)
        }
    }
}

@Composable
fun ToDoList(myTasks: List<MyTask>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(myTasks) { myTask ->
            MyTaskCard(myTask = myTask, onSwipe = { /*TODO*/ }, onUpdate = {},navController)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}











