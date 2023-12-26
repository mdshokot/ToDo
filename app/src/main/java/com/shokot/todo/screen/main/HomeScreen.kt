package com.shokot.todo.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shokot.todo.R
import com.shokot.todo.presentation.HomeScreenViewModel
import com.shokot.todo.screen.main.components.home.CustomDropDown
import com.shokot.todo.screen.main.components.home.TaskDialog
import com.shokot.todo.screen.main.components.home.TaskDialogViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    taskViewModal: TaskDialogViewModel,
    homeScreenViewModel: HomeScreenViewModel
) {
    val items = stringArrayResource(R.array.task_filter_options)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        CustomDropDown(
            textFieldLabelRes = R.string.select_label,
            items = items,
            selectedItem = homeScreenViewModel.selectedItem,
            onSelectedItem = { homeScreenViewModel.setMySelectedItem(it) },
            defaultOption = R.string.all
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (taskViewModal.showModal) {
            TaskDialog(taskViewModal)
        }
    }
}








