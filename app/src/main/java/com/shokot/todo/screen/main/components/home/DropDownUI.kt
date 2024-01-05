package com.shokot.todo.screen.main.components.home

import android.util.Log
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
import com.shokot.todo.utility.SelectOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownUI(
    options: List<SelectOption>,
) {
    var expanded by rememberSaveable  { mutableStateOf(false) }
    var selectedText by rememberSaveable  { mutableStateOf(options[0].option) }
    Log.i("HOMEEEEEEEE","recomposition in Dropppppppppppppppppppppdownnnnnnnnnnnnnnn")
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        //text-field
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            readOnly = true,
            modifier = Modifier.menuAnchor()
            )
        //options
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectOption ->
                DropdownMenuItem(
                    text = { Text(selectOption.option) },
                    onClick = {
                        expanded = false
                    }
                )
            }
        }
    }

}