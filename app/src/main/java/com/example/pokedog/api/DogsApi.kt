package com.example.pokedog.api

object DogsApi {
    val retrofitService: ApiService by lazy {
        NetworkModule.retrofit.create(ApiService::class.java)
    }
}