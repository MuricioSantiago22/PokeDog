package com.example.pokedog.data.repository

import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.data.remote.api.DogsApi
import com.example.pokedog.data.remote.dto.dog.DogDTOMapper
import com.example.pokedog.data.remote.api.makeNetworkCall
import com.example.pokedog.domain.model.Dog

class DogRepo {

    private val mapper = DogDTOMapper()

    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val response = DogsApi.retrofitService.getAllDogs()
        val dogDTOList = response.body()?.data?.dogs
            ?.filter { it.name != null }
            ?: emptyList()
        mapper.fromDogDTOListToDomainList(dogDTOList)
    }
}