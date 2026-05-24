package com.example.pokedog.data.repository

import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.data.remote.api.DogsApi
import com.example.pokedog.data.remote.api.makeNetworkCall
import com.example.pokedog.data.remote.dto.user.UserDTOMapper
import com.example.pokedog.data.remote.requests.LoginRequest
import com.example.pokedog.data.remote.requests.SignUpRequest
import com.example.pokedog.domain.model.User

class AuthRepo {

    private val mapper = UserDTOMapper()

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val response = DogsApi.retrofitService.signUp(
            SignUpRequest(email, password, passwordConfirmation)
        )
        val errorBody = response.errorBody()?.string()
        when {
            !response.isSuccessful -> throw Exception(errorBody ?: "sign_up_error")
            response.body()?.data?.user == null -> throw Exception("sign_up_error")
            else -> mapper.toDomain(response.body()!!.data!!.user!!)
        }
    }

    suspend fun signIn(
        email: String,
        password: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val response = DogsApi.retrofitService.signIn(
            LoginRequest(email, password)
        )
        val errorBody = response.errorBody()?.string()
        when {
            !response.isSuccessful -> throw Exception(errorBody ?: "sign_in_error")
            response.body()?.data?.user == null -> throw Exception("sign_in_error")
            else -> mapper.toDomain(response.body()!!.data!!.user!!)
        }
    }
}