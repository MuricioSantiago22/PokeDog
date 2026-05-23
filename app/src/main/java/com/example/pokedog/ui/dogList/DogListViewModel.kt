package com.example.pokedog.ui.dogList

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

    private val _selectedDog = MutableLiveData<Dog?>()
    val selectedDog: LiveData<Dog?> get() = _selectedDog

    init {
        downloadDogs()
    }

    private fun downloadDogs() {
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
}