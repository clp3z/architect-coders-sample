package com.devexperto.architectcoders.model

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
