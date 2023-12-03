package com.devexperto.architectcoders.model

import android.content.Context
import com.devexperto.architectcoders.R
import retrofit2.HttpException
import java.io.IOException

interface Error {
    class Sever(val code: Int) : Error
    object Connectivity : Error
    class Unknown(val message: String) : Error
}

fun Throwable.toError(): Error = when (this) {
    is HttpException -> Error.Sever(code())
    is IOException -> Error.Connectivity
    else -> Error.Unknown(message ?: "Unknown error")
}

inline fun <T> tryCall(function: () -> T): Error? = try {
    function()
    null
} catch (error: Throwable) {
    error.toError()
}

// Added to avoid creating a DetailState class
fun toErrorMessage(context: Context, error: Error?): String = error?.let {
    when (it) {
        is Error.Sever -> context.getString(R.string.sever_error_code, it.code)
        is Error.Unknown -> context.getString(R.string.unknown_error, it.message)
        else -> context.getString(R.string.connectivity_error)
    }
} ?: ""
