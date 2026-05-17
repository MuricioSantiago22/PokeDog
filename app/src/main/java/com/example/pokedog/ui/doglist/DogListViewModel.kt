package com.example.pokedog.ui.doglist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedog.Dog
import kotlinx.coroutines.launch

class DogListViewModel: ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val  dogList : LiveData<List<Dog>>
        get()= _dogList

    private val dogRepo = DogRepo()

    init {
        downloadDogs()
    }
    private fun downloadDogs() {
        viewModelScope.launch {
            try {
                val dogs  = dogRepo.downloadDogs()
                Log.d("DogListViewModel", "Sin filtrar: ${dogs.size}")
                Log.d("DogListViewModel", "Primer perro: ${dogs.firstOrNull()?.name}")
                _dogList.value = dogs
            } catch (e: Exception) {
                Log.e("DogListViewModel", "Error: ${e.message}")
                _dogList.value = emptyList()
            }
        }
    }


}