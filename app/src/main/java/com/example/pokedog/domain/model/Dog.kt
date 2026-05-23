package com.example.pokedog.domain.model

data class Dog(
    val id: Long? = null,
    val index: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val heightFemale: String? = null,
    val heightMale: String? = null,
    val imageUrl: String? = null,
    val lifeExpectancy: String? = null,
    val temperament: String? = null,
    val weightFemale: String? = null,
    val weightMale: String? = null
)