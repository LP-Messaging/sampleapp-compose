package com.liveperson.common.data.liveperson.coroutines

import com.liveperson.common.AppResult
import com.liveperson.common.Failure
import com.liveperson.common.Success
import com.liveperson.infra.callbacks.InitLivePersonCallBack
import com.liveperson.messaging.sdk.api.exceptions.SdkNotInitializedException
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

internal class LPInitCallbackWithContinuation(
    private val continuation: Continuation<AppResult<Unit, Throwable>>
): InitLivePersonCallBack {

    override fun onInitSucceed() {
        continuation.resume(Success(Unit))
    }

    override fun onInitFailed(e: Exception?) {
        continuation.resume(Failure(e ?: SdkNotInitializedException()))
    }

}