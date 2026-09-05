package com.learnenglish.grammargames.core.common

import com.learnenglish.grammargames.core.common.error.AppError

sealed interface AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>
    data class Error(val error: AppError) : AppResult<Nothing>
}
