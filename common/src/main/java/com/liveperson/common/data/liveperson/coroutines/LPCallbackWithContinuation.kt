package com.liveperson.common.data.liveperson.coroutines

import com.liveperson.common.AppResult
import com.liveperson.common.Failure
import com.liveperson.common.Success
import com.liveperson.infra.ICallback
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

internal class LPCallbackWithContinuation<T, E: Throwable>(
    private val continuation: Continuation<AppResult<T, E>>
): ICallback<T, E> {
    override fun onSuccess(value: T) {
        continuation.resume(Success(value))
    }

    override fun onError(exception: E) {
        continuation.resume(Failure(exception))
    }
}