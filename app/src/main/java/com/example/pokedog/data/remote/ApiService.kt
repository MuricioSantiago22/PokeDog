package com.example.pokedog.data.remote

import com.example.pokedog.data.remote.requests.SignUpRequest
import com.example.pokedog.data.remote.responses.DogListApiResponse
import com.example.pokedog.data.remote.responses.SignUpApiResponse
import com.example.pokedog.utils.GET_ALL_DOGS_URL
import com.example.pokedog.utils.SIGN_UP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): Response<DogListApiResponse>

    @POST(SIGN_UP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpApiResponse>
}