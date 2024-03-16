package com.example.legodataapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {
    var userIsAuthenticated by mutableStateOf(false)
    var user = User()

    fun login(userAccount: User) {
        userIsAuthenticated = true
        user = userAccount
    }

    fun logout() {
        userIsAuthenticated = false
        user = User()
    }

}