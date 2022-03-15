package com.pam.spotassets.model

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val throwable: Throwable?,
    val errorResponse: String?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null,
                null
            )
        }

        fun <T> error(msg: Throwable? = null, data: T? = null, response: String? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg,
                response
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null,
                null
            )
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}
