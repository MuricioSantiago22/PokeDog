package com.example.pokedog.data.remote.dto.user

import com.example.pokedog.domain.model.User

class UserDTOMapper {
    fun toDomain(dto: UserDTO): User = User(
        id = dto.id,
        email = dto.email,
        authenticationToken = dto.authenticationToken
    )
}