package com.example.pokedog.data.remote.dto.user

import com.squareup.moshi.Json

data class UserDTO(
    val id: Long,
    val email: String,
    @Json(name = "authentication_token") val authenticationToken: String
)