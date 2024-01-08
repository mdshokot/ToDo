package com.shokot.todo.screen.main.components.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shokot.todo.R
import com.shokot.todo.utility.SelectOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownUI(
    options: List<SelectOption>,
    selectedOption: String,
    onSelectedOption: (SelectOption) -> Unit,
    defaultOption: SelectOption
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth()
    ) {
        //text-field
        OutlinedTextField(
            value = if (selectedOption === "") {
                defaultOption.option
            } else {
                selectedOption
            },
            label = { Text(text = stringResource(R.string.select_label)) },
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            readOnly = true,
            modifier = Modifier.fillMaxWidth().menuAnchor()
            )
        //options
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize()
        ) {
            options.forEach { selectOption ->
                DropdownMenuItem(
                    text = { Text(selectOption.option) },
                    onClick = {
                        expanded = false
                        onSelectedOption(selectOption)
                    }
                )
            }
        }
    }

}