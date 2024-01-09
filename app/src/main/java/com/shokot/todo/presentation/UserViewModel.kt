package com.shokot.todo.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shokot.todo.domain.entity.User
import com.shokot.todo.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var profileImage = MutableStateFlow<Bitmap?>(null)
    private val _user = MutableStateFlow(User(id = 0, "prova", "prova@gmail.com", "123456"))

    val user: StateFlow<User>
        get() = _user

    fun setMyUser(user: User) {
        _user.value = user
    }

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteUser(user)
        }
    }

    fun getUserById(id: Int): Flow<User?> {

        return userRepository.getUserById(id)
    }

    fun getUserbyIds(userId:Int):User{
        return userRepository.getUserByIds(userId)
    }

    fun getUserByEmail(email: String): Flow<User> {
        return userRepository.getUserByEmail(email)
    }

    fun doesEmailExists(email : String): Flow<Boolean>{
        return userRepository.doesEmailExists(email)
    }
}