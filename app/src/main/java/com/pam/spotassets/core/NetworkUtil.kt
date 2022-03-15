package com.pam.spotassets.core

import com.pam.spotassets.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): Resource<T> {
    return withContext(dispatcher) {
        try {
            Resource.success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Resource.error(throwable, null)
                is HttpException -> {
                    Resource.error(throwable, null, throwable.response()?.errorBody()?.string() ?: "")
                }
                is Exception -> Resource.error(throwable, null)
                else -> Resource.error(throwable, null)
            }
        }
    }
}