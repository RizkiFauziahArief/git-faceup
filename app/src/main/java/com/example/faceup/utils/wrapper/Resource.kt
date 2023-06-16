package com.example.faceup.utils.wrapper

import com.example.faceup.network.models.error.ResponseError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException

sealed class Resource <T> private constructor(
    val payload: T? = null,
    val message: String?= null,
    val exception: Exception? = null
){
    class Success<T>(val data: T?) : Resource<T>(data)
    class Error<T>(exception: Exception?, message: String?) :
        Resource<T>(message = message, exception = exception)

    class Loading<T>(data: T? = null) : Resource<T>(data)

}
suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(coroutine.invoke())
    } catch (exception: Exception) {
        Resource.Error(exception, exception.message)

        }
}
