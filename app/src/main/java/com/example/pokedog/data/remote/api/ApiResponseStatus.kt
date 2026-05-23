package com.example.pokedog.data.remote.api

sealed class ApiResponseStatus<T>(){
    class Success<T>(val data: T ): ApiResponseStatus<T>()
    class Loading<T>(): ApiResponseStatus<T>()
    class Error<T>(val messageId: Int): ApiResponseStatus<T>()
    class None<T> : ApiResponseStatus<T>()
}