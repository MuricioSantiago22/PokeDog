package com.example.pokedog.data.repository

import com.example.pokedog.data.remote.ApiResponseStatus
import com.example.pokedog.data.remote.DogsApi
import com.example.pokedog.data.remote.dto.DogDTOMapper
import com.example.pokedog.data.remote.makeNetworkCall
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