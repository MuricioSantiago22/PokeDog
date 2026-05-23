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

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    fun signUp(email: String, password: String, passwordConfirmation: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            val result = repository.signUp(email, password, passwordConfirmation)
            if (result is ApiResponseStatus.Success) {
                _user.value = result.data
            }
            _status.value = result
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            val result = repository.signIn(email, password)
            if (result is ApiResponseStatus.Success) {
                _user.value = result.data
            }
            _status.value = result
        }
    }

    fun logout() {
        _user.value = null
        _status.value = ApiResponseStatus.None()
    }
}