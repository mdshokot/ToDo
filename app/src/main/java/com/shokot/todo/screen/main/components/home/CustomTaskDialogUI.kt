package com.shokot.todo.screen.main.components.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shokot.todo.R
import com.shokot.todo.utility.Helper.CustomOutlinedTextField


@Composable
fun TaskDialog(taskViewModal: TaskDialogViewModel) {
    Dialog(onDismissRequest = {
        taskViewModal.hideDialog()
        taskViewModal.clearAllFields()
    }
    ) {
        CustomDialogUI(taskViewModal)
    }
}

@Composable
fun CustomDialogUI(taskViewModal: TaskDialogViewModel) {
    val items = stringArrayResource(R.array.task_select_option)

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
                    selectedItem = taskViewModal.selectedItem,
                    onSelectedItem = { taskViewModal.setMySelectedItem(it) }
                )
                Spacer(modifier = Modifier.height(5.dp))
                //title
                CustomOutlinedTextField(
                    value = taskViewModal.title,
                    onValueChange = {
                        taskViewModal.setMyTitle(it)
                    },
                    label = R.string.task_title,
                    icon = Icons.Default.Info,
                    keyboardType = KeyboardType.Text,
                    space = 5
                )
                //description
                CustomOutlinedTextField(
                    value = taskViewModal.description,
                    onValueChange = {
                        taskViewModal.setMyDescription(it)
                    },
                    label = R.string.task_descrition,
                    icon = Icons.Default.Info,
                    keyboardType = KeyboardType.Text,
                    space = 5
                )
                //graph name
                CustomOutlinedTextField(
                    value = taskViewModal.graphName,
                    onValueChange = {
                        taskViewModal.setMyGraphName(it)
                    },
                    label = R.string.task_graph_name,
                    icon = Icons.Default.Info,
                    keyboardType = KeyboardType.Text,
                    space = 5
                )
                if (taskViewModal.selectedItem == stringResource(id = R.string.task_value)) {
                    //Value
                    CustomOutlinedTextField(
                        value = taskViewModal.value,
                        onValueChange = {
                            taskViewModal.setMyValue(it)
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
                        taskViewModal.hideDialog()
                        taskViewModal.clearAllFields()
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

                        taskViewModal.hideDialog()
                        taskViewModal.clearAllFields()
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