package com.example.pokedog.data.remote.api

import com.example.pokedog.data.remote.dto.AddDogToUserRequest
import com.example.pokedog.data.remote.requests.LoginRequest
import com.example.pokedog.data.remote.requests.SignUpRequest
import com.example.pokedog.data.remote.responses.AddDogToUserResponse
import com.example.pokedog.data.remote.responses.DogListApiResponse
import com.example.pokedog.data.remote.responses.AuthApiResponse
import com.example.pokedog.utils.ADD_DOG_TO_USER
import com.example.pokedog.utils.GET_ALL_DOGS_URL
import com.example.pokedog.utils.GET_USER_DOGS
import com.example.pokedog.utils.SIGN_IN
import com.example.pokedog.utils.SIGN_UP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): Response<DogListApiResponse>

    @POST(SIGN_UP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<AuthApiResponse>

    @POST(SIGN_IN)
    suspend fun signIn(@Body loginRequest: LoginRequest): Response<AuthApiResponse>

    @POST(ADD_DOG_TO_USER)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserRequest): Response<AddDogToUserResponse>

    @GET(GET_USER_DOGS)
    suspend fun getUserDogs(): Response<DogListApiResponse>
}