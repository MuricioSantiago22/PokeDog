package com.example.pokedog.ui.doglist

import com.example.pokedog.Dog
import com.example.pokedog.api.ApiResponseStatus
import com.example.pokedog.api.DogsApi
import com.example.pokedog.api.dto.DogDTOMapper
import com.example.pokedog.api.makeNetworkCall

class DogRepo {

    private val mapper = DogDTOMapper()

    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall{
            val response = DogsApi.retrofitService.getAllDogs()
            val dogDTOList = response.body()?.data?.dogs
                ?.filter { it.name != null }
                ?: emptyList()
            mapper.fromDogDTOListToDomainList(dogDTOList)
    }
}