package com.example.pokedog.ui.dogList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedog.Dog
import com.example.pokedog.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel: ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val  dogList : LiveData<List<Dog>>
        get()= _dogList

    private val dogRepo = DogRepo()

    private val _status = MutableLiveData<ApiResponseStatus<List<Dog>>>()
    val status: LiveData<ApiResponseStatus<List<Dog>>>
        get() = _status

    private val _selectedDog = MutableLiveData<Dog?>()
    val selectedDog: LiveData<Dog?> get() = _selectedDog

    init {
        downloadDogs()
    }
    private fun downloadDogs() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepo.downloadDogs())
        }
    }

    private fun handleResponseStatus(downloadDogs: ApiResponseStatus<List<Dog>>) {
         if (downloadDogs is ApiResponseStatus.Success){
             _dogList.value = downloadDogs.data
         }
        _status.value = downloadDogs
    }

    fun getDogByIndex(index: Int): Dog? {
        return dogList.value?.getOrNull(index)
    }

    fun selectDog(index: Int) {
        _selectedDog.value = dogList.value?.getOrNull(index)
    }
}