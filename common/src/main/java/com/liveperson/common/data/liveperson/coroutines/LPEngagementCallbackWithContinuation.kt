package com.liveperson.common.data.liveperson.coroutines

import com.liveperson.common.AppResult
import com.liveperson.common.Failure
import com.liveperson.common.Success
import com.liveperson.monitoring.sdk.callbacks.EngagementCallback
import com.liveperson.monitoring.sdk.callbacks.MonitoringErrorType
import com.liveperson.monitoring.sdk.responses.LPEngagementResponse
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

internal class LPEngagementCallbackWithContinuation constructor(
    private val continuation: Continuation<AppResult<LPEngagementResponse, Throwable>>
): EngagementCallback {

    override fun onError(errorType: MonitoringErrorType, exception: Exception?) {
        continuation.resume(Failure(exception ?: IllegalStateException("Error occurred: $errorType")))
    }

    override fun onSuccess(lpEngagementResponse: LPEngagementResponse) {
        continuation.resume(Success(lpEngagementResponse))
    }


}