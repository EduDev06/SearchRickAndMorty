package com.example.searchapp.util

sealed class Result<T> {
    data class Success<T>(val data: T? = null): Result<T>()
    data class Error<T>(val error: ErrorEntity): Result<T>()
}