package com.example.pokedog.data.remote.dto.dog

import com.example.pokedog.domain.model.Dog

class DogDTOMapper {
    fun toDomain(dto: DogDTO): Dog = Dog(
        id = dto.id,
        index = dto.index,
        name = dto.name,
        type = dto.type,
        heightFemale = dto.heightFemale,
        heightMale = dto.heightMale,
        imageUrl = dto.imageUrl,
        lifeExpectancy = dto.lifeExpectancy,
        temperament = dto.temperament,
        weightFemale = dto.weightFemale,
        weightMale = dto.weightMale
    )

    fun toDomainList(dtos: List<DogDTO>): List<Dog> = dtos.map { toDomain(it) }
}