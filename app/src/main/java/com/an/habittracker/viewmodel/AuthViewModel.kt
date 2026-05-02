package com.an.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel:ViewModel() {
    val authSuccessLD = MutableLiveData<Boolean>()
    val authErrorLD = MutableLiveData<String>()

    fun login(username: String, password: String) {
        if (username == "student" && password == "123") {
            authSuccessLD.value = true
        } else {
            authErrorLD.value = "Invalid credentials"
            authSuccessLD.value = false
        }
    }

    fun reset() {
        authSuccessLD.value = false
        authErrorLD.value = ""
    }
}