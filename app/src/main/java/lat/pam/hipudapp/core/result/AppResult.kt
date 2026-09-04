package lat.pam.hipudapp.core.result

import kotlinx.coroutines.CancellationException

sealed interface AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>
    data class Error(val error: AppError) : AppResult<Nothing>
}

inline fun <T> AppResult<T>.onSuccess(action: (T) -> Unit): AppResult<T> {
    if (this is AppResult.Success) action(data)
    return this
}

inline fun <T> AppResult<T>.onError(action: (AppError) -> Unit): AppResult<T> {
    if (this is AppResult.Error) action(error)
    return this
}

suspend fun <T> safeCall(
    mapError: (Throwable) -> AppError = { AppError.Unknown(it) },
    block: suspend () -> T,
): AppResult<T> = try {
    AppResult.Success(block())
} catch (c: CancellationException) {
    throw c
} catch (t: Throwable) {
    AppResult.Error(mapError(t))
}
