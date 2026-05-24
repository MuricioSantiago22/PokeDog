package com.example.pokedog.data.remote.dto

import com.squareup.moshi.Json

data class AddDogToUserRequest(
    @Json(name ="dog_id") val dogId: Long)
