package com.learnenglish.grammargames.core.common.error

sealed interface AppError {
    data class DatabaseError(val message: String, val cause: Throwable? = null) : AppError
    data class StorageError(val message: String, val cause: Throwable? = null) : AppError
    data class ContentNotFound(val id: String) : AppError
    data class Unknown(val message: String, val cause: Throwable? = null) : AppError
}
