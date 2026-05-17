package com.example.pokedog.api

import android.util.Log
import com.example.pokedog.utils.BASE_URL
import com.example.pokedog.utils.GET_ALL_DOGS_URL
import com.example.pokedog.api.responses.DogListApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val response = chain.proceed(chain.request())
        val bodyString = response.peekBody(Long.MAX_VALUE).string()
        Log.d("DogRepo_RAW", bodyString.take(500)) // primeros 500 caracteres
        response
    }
    .build()

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): Response<DogListApiResponse>
}

object DogsApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}