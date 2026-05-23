package com.example.pokedog.domain.model

data class User(
    val id: Long,
    val email: String,
    val authenticationToken: String
)