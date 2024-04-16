package com.example.legodataapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    var userIsAuthenticated by mutableStateOf(false)

    fun login(userAccount: User) {
        userIsAuthenticated = true
        _user.value = userAccount
    }

    fun logout() {
        userIsAuthenticated = false
        _user.value = User()
    }
}