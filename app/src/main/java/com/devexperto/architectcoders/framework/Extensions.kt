package com.devexperto.architectcoders.framework

import androidx.fragment.app.Fragment
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.domain.Error
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toError() = when (this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "Unknown error")
}

inline fun <T> tryCall(function: () -> T): Either<Error, T> = try {
    function().right()
} catch (error: Throwable) {
    error.toError().left()
}

fun Fragment.toErrorMessage(error: Error?): String = error?.let {
    when (it) {
        is Error.Server -> requireContext().getString(R.string.server_error_code, it.code)
        is Error.Unknown -> requireContext().getString(R.string.unknown_error, it.message)
        else -> requireContext().getString(R.string.connectivity_error)
    }
} ?: ""
