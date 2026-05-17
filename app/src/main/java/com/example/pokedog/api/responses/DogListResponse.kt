package com.example.pokedog.api.responses

import com.example.pokedog.api.dto.DogDTO

data class DogListResponse(val dogs : List<DogDTO>? = null)