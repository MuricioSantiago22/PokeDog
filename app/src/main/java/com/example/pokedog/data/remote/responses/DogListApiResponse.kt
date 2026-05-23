package com.example.pokedog.data.remote.responses

import com.example.pokedog.data.remote.responses.DogListResponse
import com.squareup.moshi.Json

data class DogListApiResponse(
    val message: String? = null,
    @Json(name = "is_success") val isSuccess: Boolean = false,
    val data: DogListResponse? = null
)