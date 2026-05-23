package com.example.pokedog.data.remote.requests

import com.squareup.moshi.Json

data class SignUpRequest(
    val email: String,
    val password: String,
    @Json(name = "password_confirmation") val passwordConfirmation: String
)
