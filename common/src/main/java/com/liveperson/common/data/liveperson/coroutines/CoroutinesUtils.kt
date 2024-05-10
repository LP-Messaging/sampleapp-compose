package com.liveperson.common.data.liveperson.coroutines

import com.liveperson.common.AppResult
import com.liveperson.infra.ICallback
import com.liveperson.infra.callbacks.InitLivePersonCallBack
import com.liveperson.messaging.sdk.api.callbacks.LogoutLivePersonCallback
import kotlinx.coroutines.suspendCancellableCoroutine

internal suspend fun<T, E: Throwable> executeCommand(block: (ICallback<T, E>) -> Unit): AppResult<T, E> {
    return suspendCancellableCoroutine {
        block(LPCallbackWithContinuation(it))
    }
}

internal suspend fun executeLogin(block: (InitLivePersonCallBack) -> Unit): AppResult<Unit, Throwable> {
    return suspendCancellableCoroutine {
        block(LPInitCallbackWithContinuation(it))
    }
}

internal suspend fun executeLogout(block: (LogoutLivePersonCallback) -> Unit): AppResult<Unit, Unit> {
    return suspendCancellableCoroutine {
        block(LPLogoutCallbackWithContinuation(it))
    }
}