package no.joharei.flixr.network.framework

import no.joharei.flixr.network.framework.Result.Success

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Loading<out T>(val data: T? = null) : Result<T>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val exception: Exception, val data: T? = null) : Result<Nothing>()

    override fun toString(): String = when (this) {
        is Success<*> -> "Success[data=$data]"
        is Error<*> -> "Error[exception=$exception]"
        is Loading -> data?.let { "Loading[data=$it]" } ?: "Loading"
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Success && data != null