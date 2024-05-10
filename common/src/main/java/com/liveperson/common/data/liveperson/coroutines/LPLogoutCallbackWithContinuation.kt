package com.liveperson.common.data.liveperson.coroutines

import com.liveperson.common.AppResult
import com.liveperson.common.Failure
import com.liveperson.common.Success
import com.liveperson.messaging.sdk.api.callbacks.LogoutLivePersonCallback
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

internal class LPLogoutCallbackWithContinuation constructor(
    private val continuation: Continuation<AppResult<Unit, Unit>>
): LogoutLivePersonCallback {

    override fun onLogoutSucceed() {
        continuation.resume(Success(Unit))
    }

    override fun onLogoutFailed() {
        continuation.resume(Failure(Unit))
    }
}