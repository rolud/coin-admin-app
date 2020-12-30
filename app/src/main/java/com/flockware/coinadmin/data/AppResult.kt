package com.flockware.coinadmin.data

sealed class AppResult<out T> {
    data class Success<out T>(val successData: T) : AppResult<T>()
    data class Error(val exception: java.lang.Exception, val message: String? = exception.localizedMessage) : AppResult<Nothing>()
}

// todo handle functions for api response success/error
