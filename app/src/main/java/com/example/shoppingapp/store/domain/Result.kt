package com.example.shoppingapp.store.domain

sealed class Result<T>(
    val value: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(data: T? = null, message: String) : Result<T>(data, message)
}