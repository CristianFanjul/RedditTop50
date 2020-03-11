package com.example.reddittop50.domain

import android.accounts.NetworkErrorException
import retrofit2.Call

import java.io.IOException

fun <T> callAndResult(call: () -> Call<T>, default: () -> T): Result<T> {
    return try {
        val result = call().execute()
        return if (result.isSuccessful) {
            Result.Success(result.body() ?: default())
        } else {
            Result.Error(NetworkErrorException("Error: ${result.code()}"))
        }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        Result.Error(NetworkErrorException("Connection problem."))
    } catch (runtimeException: RuntimeException) {
        runtimeException.printStackTrace()
        Result.Error(NetworkErrorException("General exception."))
    }
}