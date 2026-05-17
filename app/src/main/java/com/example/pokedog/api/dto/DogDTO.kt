package com.example.pokedog.api.dto

import com.squareup.moshi.Json

data class DogDTO(val id: Long? = null,
                  val index: Int? = null,
                  @Json(name = "name_en") val name: String? = null,
                  @Json(name = "dog_type") val type: String? = null,
                  @Json(name = "height_female") val heightFemale: String? = null,
                  @Json(name = "height_male") val heightMale: String? = null,
                  @Json(name = "image_url") val imageUrl: String? = null,
                  @Json(name = "life_expectancy") val lifeExpectancy: String? = null,
                  val temperament: String? = null,
                  @Json(name = "weight_female") val weightFemale: String? = null,
                  @Json(name = "weight_male") val weightMale: String? = null)
