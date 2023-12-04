package com.shokot.todo.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shokot.todo.R
import com.shokot.todo.screen.main.components.home.DropDownMenu

@Composable
fun HomeScreen(navController: NavController) {
    val defaultSelected = stringResource(id = R.string.all)
    var selectedItem by rememberSaveable {
        mutableStateOf(defaultSelected)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        DropDownMenu(selectedItem = selectedItem, onSelectedItemChange = { selectedItem = it })
        Spacer(modifier = Modifier.height(5.dp))

    }
}



