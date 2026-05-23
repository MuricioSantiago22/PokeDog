package com.example.pokedog.data.remote

object DogsApi {
    val retrofitService: ApiService by lazy {
        NetworkModule.retrofit.create(ApiService::class.java)
    }
}