package com.example.pokedog.data.remote.responses

import com.example.pokedog.data.remote.dto.DogDTO

data class DogListResponse(val dogs : List<DogDTO>? = null)