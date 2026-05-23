package com.example.pokedog.data.remote.api

import android.util.Log
import com.example.pokedog.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend ()-> T
): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try{
            ApiResponseStatus.Success(call())
        }catch(e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.unknow_host_exception_error)

        } catch(e:Exception) {
            Log.e("makeNetworkCall", "Error: ${e.message}")
            val errorMessageId = when(e.message) {
            "sign_up_error" -> R.string.error_sign_up
                "sign_in_error" -> R.string.error_sign_in
                "user_already_exists" -> R.string.user_already_exists
                else -> R.string.unknow_error
            }
            ApiResponseStatus.Error(errorMessageId)
        }
    }
}