package com.example.pokedog.ui.doglist

import com.example.pokedog.Dog
import com.example.pokedog.api.DogsApi
import com.example.pokedog.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepo {

    private val mapper = DogDTOMapper()

    suspend fun downloadDogs(): List<Dog> {
        return withContext(Dispatchers.IO) {
            val response = DogsApi.retrofitService.getAllDogs()
            if (response.isSuccessful) {
                val dogDTOList = response.body()?.data?.dogs
                    ?.filter { it.name != null }
                    ?: emptyList()
                mapper.fromDogDTOListToDomainList(dogDTOList)
            } else {
                emptyList()
            }
        }
    }
}