package com.shokot.todo.screen.main

import android.content.Context
import android.graphics.Bitmap
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.shokot.todo.R
import com.shokot.todo.ThemeViewModel
import com.shokot.todo.domain.entity.User
import com.shokot.todo.presentation.RegistrationViewModel
import com.shokot.todo.screen.main.components.profile.ChangeTheme
import com.shokot.todo.screen.main.components.profile.Logout
import com.shokot.todo.screen.main.components.profile.UserInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel,
    registrationViewModel: RegistrationViewModel,
    user: User
) {

    val profileViewModel: ProfileViewModel = viewModel()
    val userImage by profileViewModel.userImageBitmap.collectAsState()
    if(user.image !== null){
        profileViewModel.updateUserImageBitmap(user.image)
    }

    val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        //Log here
        profileViewModel.updateUserImageBitmap(bitmap)
        registrationViewModel.updateUser(user.copy(image = bitmap))
    }

    val preferences = LocalContext.current.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)

    //TODO ask for permission


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val modifier = Modifier.fillMaxWidth()
        userAvatar(takePicture, userImage,user)
        Spacer(modifier = Modifier.height(20.dp))
        UserInformation(modifier,user)
        Spacer(modifier = Modifier.height(20.dp))
        ChangeTheme(modifier,registrationViewModel,user,themeViewModel)
        Spacer(modifier = Modifier.height(20.dp))
        Logout(navController,modifier,preferences)
    }
}

@Composable
fun userAvatar(
    takePicture: ManagedActivityResultLauncher<Void?, Bitmap?>,
    userImage: Bitmap?,
    user: User
){
    val painter = if (userImage != null) {
        rememberAsyncImagePainter(model = userImage)
    } else {
        // Provide a placeholder image or default image if userImage is null
        rememberAsyncImagePainter(model = R.drawable.yao)
    }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box{
                Image(painter = painter, contentDescription = "",
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
                            takePicture.launch(null)
                        }
                        .align(Alignment.BottomEnd)
                        .padding(6.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = user.username,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

// ProfileViewModel

class ProfileViewModel : ViewModel() {

    private val _userImageBitmap = MutableStateFlow<Bitmap?>(null)
    val userImageBitmap = _userImageBitmap.asStateFlow()

    fun updateUserImageBitmap(bitmap: Bitmap?) {
        viewModelScope.launch {
            _userImageBitmap.value = bitmap
        }
    }
}





