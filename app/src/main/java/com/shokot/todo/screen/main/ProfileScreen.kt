package com.shokot.todo.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.shokot.todo.R
import com.shokot.todo.ThemeViewModel
import com.shokot.todo.screen.main.components.profile.ChangeTheme
import com.shokot.todo.screen.main.components.profile.Logout
import com.shokot.todo.screen.main.components.profile.UserInformation

@Composable
fun ProfileScreen(navController: NavController, themeViewModel: ThemeViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val modifier = Modifier.fillMaxWidth()
        //user profile with image and name
        userAvatar()
        Spacer(modifier = Modifier.height(20.dp))
        //user email for login
        UserInformation(modifier)
        Spacer(modifier = Modifier.height(20.dp))
        ChangeTheme(themeViewModel,modifier)
        Spacer(modifier = Modifier.height(20.dp))
        Logout(navController,modifier)
    }
}

@Composable
fun userAvatar(){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box{
                Image(painter = painterResource(id = R.drawable.yao), contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color.Gray, CircleShape)
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White, CircleShape)
                        .clickable {

                        }
                        .align(Alignment.BottomEnd)
                        .padding(6.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Yao",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}






