package com.example.searchapp.domain

sealed class Result<T>(val data: T? = null) {
    class Success<T>(data: T?): Result<T>()
    class Error<T>(error: ErrorEntity): Result<T>()
}