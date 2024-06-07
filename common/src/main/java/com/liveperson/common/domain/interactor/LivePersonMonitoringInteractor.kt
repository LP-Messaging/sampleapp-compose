package com.liveperson.common.domain.interactor

import com.liveperson.common.AppResult
import com.liveperson.monitoring.sdk.MonitoringParams
import com.liveperson.monitoring.sdk.responses.LPEngagementResponse

interface LivePersonMonitoringInteractor {

    suspend fun getEngagement(
        identities: List<Pair<String, String?>>,
        monitoringParams: MonitoringParams
    ): AppResult<LPEngagementResponse, Throwable>
}