package com.example.pokedog.presentation.dogList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.data.repository.DogRepo
import com.example.pokedog.domain.model.Dog
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {

    private val repository = DogRepo()

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>> get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<List<Dog>>>()
    val status: LiveData<ApiResponseStatus<List<Dog>>> get() = _status

    private val _addDogStatus = MutableLiveData<ApiResponseStatus<Any>>()
    val addDogStatus: LiveData<ApiResponseStatus<Any>> get() = _addDogStatus

    private val _selectedDog = MutableLiveData<Dog?>()
    val selectedDog: LiveData<Dog?> get() = _selectedDog

    private val _userDogList = MutableLiveData<List<Dog>>()
    val userDogList: LiveData<List<Dog>> get() = _userDogList

    fun downloadDogs() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            val result = repository.downloadDogs()
            if (result is ApiResponseStatus.Success) {
                _dogList.value = result.data
            }
            _status.value = result
        }
    }

    fun selectDog(index: Int) {
        _selectedDog.value = _dogList.value?.getOrNull(index)
    }

    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            _addDogStatus.value = ApiResponseStatus.Loading()
            _addDogStatus.value = repository.addDogToUser(dogId)
        }
    }

    fun getUserDogs() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            val result = repository.getUserDogs()
            if (result is ApiResponseStatus.Success) {
                _userDogList.value = result.data
            }
            _status.value = result
        }
    }
}