package com.shokot.todo.screen.main.components.profile

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shokot.todo.R
import com.shokot.todo.navigation.Graph
import com.shokot.todo.utility.PreferencesKeys

@Composable
fun Logout(navController: NavController, modifier: Modifier, preferences: SharedPreferences){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.before_logout_text))
            ElevatedButton(
                onClick = {
                    preferences.edit().remove(PreferencesKeys.USER_ID).apply()
                    //other things like delete the remember saving the password and email
                    navController.navigate(Graph.authentication){

                    }
                },
                colors = ButtonDefaults.elevatedButtonColors(
                     containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(text = stringResource(id = R.string.logout))
            }
        }
    }
}