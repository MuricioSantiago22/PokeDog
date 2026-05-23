package com.example.pokedog.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.data.repository.AuthRepo
import com.example.pokedog.domain.model.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepo()

    private val _status = MutableLiveData<ApiResponseStatus<User>>()
    val status: LiveData<ApiResponseStatus<User>> get() = _status

    fun signUp(name: String, password: String, passwordConfirmation: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            _status.value = repository.signUp(name, password, passwordConfirmation)
        }
    }
}