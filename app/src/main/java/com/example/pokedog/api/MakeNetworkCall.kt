package com.example.pokedog.api

import com.example.pokedog.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend ()-> T
): ApiResponseStatus<T>{
    return withContext(Dispatchers.IO) {
        try{
            ApiResponseStatus.Success(call())
        }catch(e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.unknow_host_exception_error)

        } catch(e:Exception) {
            ApiResponseStatus.Error(R.string.unknow_error)
        }
    }
}