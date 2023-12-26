package com.shokot.todo.utility

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun CustomSnackBar(
    scope : CoroutineScope,
    snackbarHostState: SnackbarHostState,
    msg : String,
    actionLabel :String,
    onAction : () -> Unit
){
scope.launch {
    snackbarHostState.currentSnackbarData?.dismiss()

    when(snackbarHostState.showSnackbar(message = msg, actionLabel = actionLabel, duration = SnackbarDuration.Short)){
        SnackbarResult.ActionPerformed -> {
            onAction()
        }
        SnackbarResult.Dismissed -> {}
    }
}
}