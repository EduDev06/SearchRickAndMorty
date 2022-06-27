package com.example.searchapp.data

import com.example.searchapp.util.UiText

sealed class Result<T>(val data: T? = null, val message: UiText? = null) {
    class Loading<T>(data: T? = null): Result<T>(data)
    class Success<T>(data: T?): Result<T>(data)
    class Error<T>(message: UiText, data: T? = null): Result<T>(data, message)
}