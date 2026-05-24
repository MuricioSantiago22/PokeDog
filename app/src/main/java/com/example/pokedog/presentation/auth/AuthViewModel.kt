package com.example.pokedog.presentation.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.data.remote.api.NetworkModule
import com.example.pokedog.data.repository.AuthRepo
import com.example.pokedog.domain.model.User
import com.example.pokedog.utils.SecurePreferences
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepo()

    private val _status = MutableLiveData<ApiResponseStatus<User>>()
    val status: LiveData<ApiResponseStatus<User>> get() = _status

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    init {

        val savedToken = SecurePreferences.getAuthToken(application)
        if (savedToken.isNotEmpty()) {
            NetworkModule.setAuthToken(savedToken)
        }
    }

    fun signUp(email: String, password: String, passwordConfirmation: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            val result = repository.signUp(email, password, passwordConfirmation)
            if (result is ApiResponseStatus.Success) {
                _user.value = result.data
                saveToken(result.data.authenticationToken)
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
                saveToken(result.data.authenticationToken)
            }
            _status.value = result
        }
    }

    fun logout() {
        SecurePreferences.clearAuthToken(getApplication())
        NetworkModule.setAuthToken("")
        _user.value = null
        _status.value = ApiResponseStatus.None()
    }

    private fun saveToken(token: String) {
        SecurePreferences.saveAuthToken(getApplication(), token)
        NetworkModule.setAuthToken(token)
    }
}