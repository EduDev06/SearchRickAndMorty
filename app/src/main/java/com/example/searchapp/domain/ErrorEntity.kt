package com.example.searchapp.domain

sealed class ErrorEntity {
    sealed class ApiError: ErrorEntity() {
        object NotFound : ApiError()
        object UnKnown: ApiError()
    }
    sealed class InputError: ErrorEntity() {
        object EmailError: InputError()
    }
}
