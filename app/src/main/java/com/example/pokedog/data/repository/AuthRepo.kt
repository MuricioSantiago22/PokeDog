package com.example.pokedog.data.repository

import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.data.remote.api.DogsApi
import com.example.pokedog.data.remote.dto.user.UserDTOMapper
import com.example.pokedog.data.remote.api.makeNetworkCall
import com.example.pokedog.data.remote.requests.SignUpRequest
import com.example.pokedog.domain.model.User

class AuthRepo {

    private val mapper = UserDTOMapper()

    suspend fun signUp(
        name: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val response = DogsApi.retrofitService.signUp(
            SignUpRequest(name, password, passwordConfirmation)
        )
        val userDTO = response.body()?.data?.user
            ?: throw Exception(response.message())
        mapper.toDomain(userDTO)
    }
}