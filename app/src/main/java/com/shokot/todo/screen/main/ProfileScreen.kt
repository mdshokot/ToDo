package com.shokot.todo.screen.main

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.FusedLocationProviderClient
import com.shokot.todo.R
import com.shokot.todo.domain.entity.User
import com.shokot.todo.presentation.UserViewModel
import com.shokot.todo.screen.main.components.profile.Logout
import com.shokot.todo.screen.main.components.profile.UserInformation
import com.shokot.todo.utility.PreferencesKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    fusedLocationClient: FusedLocationProviderClient,
    profileViewModel: ProfileViewModel,
) {

    val context = LocalContext.current
    val preferences: SharedPreferences =
        context.getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)
    val user = userViewModel.getUserbyIds(preferences.getInt(PreferencesKeys.USER_ID, 0))
    var userImage by rememberSaveable { mutableStateOf(user.image) }
    var cameraPermissionGranted by remember { mutableStateOf(false) }

    val cameraPermissionLauncher: ActivityResultLauncher<String> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            cameraPermissionGranted = isGranted
        }

    val modifier = Modifier.fillMaxWidth()

    val takePicture =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            userImage = bitmap
            //Log here
            userViewModel.updateUser(user.copy(image = bitmap))
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        userAvatar(
            takePicture,
            user,
            userImage,
            cameraPermissionLauncher,
            cameraPermissionGranted
        )
        Spacer(modifier = Modifier.height(20.dp))
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
                Text(text = stringResource(id = R.string.check_your_position))
                Button(onClick = {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                    } else {
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                if (location != null) {
                                    val myLocation =
                                        "lat: ${location.latitude} long: ${location.longitude}"
                                    Toast.makeText(context, myLocation, Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                    }
                }) {
                    Text(text = stringResource(id = R.string.position))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        UserInformation(modifier, user)

        Spacer(modifier = Modifier.height(20.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Logout(navController, modifier, preferences)
    }

}

@Composable
fun userAvatar(
    takePicture: ManagedActivityResultLauncher<Void?, Bitmap?>,
    user: User?,
    userImage: Bitmap?,
    cameraPermissionLauncher: ActivityResultLauncher<String>,
    cameraPermissionGranted: Boolean
) {
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
            Box {
                if (userImage != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = userImage),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .border(3.dp, Color.Gray, CircleShape)
                    )
                } else {
                    Image(
                        painterResource(R.drawable.baseline_account_circle_35),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .border(3.dp, Color.Gray, CircleShape)
                    )
                }
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White, CircleShape)
                        .clickable {
                            if (cameraPermissionGranted) {
                                takePicture.launch(null)
                            } else {
                                cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                            }
                        }
                        .align(Alignment.BottomEnd)
                        .padding(6.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            if (user != null) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}


// ProfileViewModel

class ProfileViewModel : ViewModel() {
    private val _userImageBitmap = MutableStateFlow<Bitmap?>(null)
    val userImageBitmap = _userImageBitmap.value

    fun updateUserImageBitmap(bitmap: Bitmap?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (bitmap !== null) {
                _userImageBitmap.value = bitmap
            }
        }
    }
}






