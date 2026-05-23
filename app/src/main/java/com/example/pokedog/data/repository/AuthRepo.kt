package com.example.pokedog.data.repository

import com.example.pokedog.data.remote.ApiResponseStatus
import com.example.pokedog.data.remote.DogsApi
import com.example.pokedog.data.remote.makeNetworkCall
import com.example.pokedog.data.remote.requests.SignUpRequest

class AuthRepo {

    suspend fun signUp(
        name: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<Unit> = makeNetworkCall {
        val response = DogsApi.retrofitService.signUp(
            SignUpRequest(name, password, passwordConfirmation)
        )
        if (!response.isSuccessful || response.body()?.isSuccess == false) {
            throw Exception("Sign up failed")
        }
    }
}