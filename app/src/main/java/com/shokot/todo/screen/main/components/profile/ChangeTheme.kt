package com.shokot.todo.screen.main.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shokot.todo.R
import com.shokot.todo.ThemeViewModel
import com.shokot.todo.domain.entity.User
import com.shokot.todo.presentation.RegistrationViewModel

@Composable
fun ChangeTheme(
    modifier: Modifier,
    registrationViewModel: RegistrationViewModel,
    user: User,
    themeViewModel: ThemeViewModel
){
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
            Text(text = stringResource(id = R.string.before_theme_switch_text))
            Switch(
                checked = themeViewModel.isDarkTheme,
                onCheckedChange = {
                    themeViewModel.isDarkTheme
                })
        }
    }
}