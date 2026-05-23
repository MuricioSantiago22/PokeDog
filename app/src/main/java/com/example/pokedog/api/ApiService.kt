package com.example.pokedog.api

import com.example.pokedog.api.responses.DogListApiResponse
import com.example.pokedog.utils.GET_ALL_DOGS_URL
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): Response<DogListApiResponse>
}