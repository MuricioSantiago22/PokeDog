package com.example.pokedog.data.remote.responses

import com.squareup.moshi.Json

data class SignUpApiResponse(
    val message: String? = null,
    @Json(name = "is_success") val isSuccess: Boolean = false,
    val data: UserResponse? = null,
)