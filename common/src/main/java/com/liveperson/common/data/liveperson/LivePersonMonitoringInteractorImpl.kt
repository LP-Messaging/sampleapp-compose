package com.liveperson.common.data.liveperson

import android.content.Context
import com.liveperson.common.AppResult
import com.liveperson.common.data.liveperson.coroutines.executeGetEngagement
import com.liveperson.common.domain.interactor.LivePersonMonitoringInteractor
import com.liveperson.monitoring.model.LPMonitoringIdentity
import com.liveperson.monitoring.sdk.MonitoringParams
import com.liveperson.monitoring.sdk.api.LivepersonMonitoring
import com.liveperson.monitoring.sdk.responses.LPEngagementResponse

internal class LivePersonMonitoringInteractorImpl(
    private val context: Context
): LivePersonMonitoringInteractor {

    override suspend fun getEngagement(
        identities: List<Pair<String, String?>>,
        monitoringParams: MonitoringParams
    ): AppResult<LPEngagementResponse, Throwable> {
        val monitoringIdentities = identities.map { LPMonitoringIdentity(it.first, it.second) }
        return executeGetEngagement {
            LivepersonMonitoring.getEngagement(context, monitoringIdentities, monitoringParams, it)
        }
    }
}