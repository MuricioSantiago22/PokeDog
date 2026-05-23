package com.example.pokedog.data.remote.api

import com.example.pokedog.data.remote.api.NetworkModule

object DogsApi {
    val retrofitService: ApiService by lazy {
        NetworkModule.retrofit.create(ApiService::class.java)
    }
}