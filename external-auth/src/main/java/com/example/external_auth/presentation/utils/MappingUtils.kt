package com.example.external_auth.presentation.utils

import com.example.external_auth.presentation.state.AuthType
import com.example.external_auth.presentation.state.CodeType
import com.example.external_auth.presentation.state.ImplicitType
import com.example.external_auth.presentation.state.UserCampaignInfo
import com.example.external_auth.presentation.state.dialog.SDEDialogParams
import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.CodeParams
import com.liveperson.common.domain.ConsumerCampaignInfo
import com.liveperson.common.domain.ImplicitJWEParams
import com.liveperson.common.domain.ImplicitJWTParams
import com.liveperson.common.domain.UnAuthParams
import com.liveperson.monitoring.sdk.MonitoringParams
import com.liveperson.monitoring.sdk.responses.LPEngagementResponse
import org.json.JSONArray

fun LPEngagementResponse.asUserCampaign(): UserCampaignInfo {
    val campaign = engagementDetailsList?.firstOrNull()
    return UserCampaignInfo(
        engagementId = campaign?.engagementId ?: "",
        sessionId = sessionId ?: "",
        campaignId = campaign?.campaignId ?: "",
        contextId = campaign?.contextId ?: "",
        visitorId = visitorId ?: ""
    )
}

fun AuthType.asAuthParams(): AuthParams {
    return when (this) {
        is ImplicitType -> if (isEncrypted) {
            ImplicitJWEParams(token)
        } else {
            ImplicitJWTParams(token)
        }

        is CodeType -> {
            CodeParams(code)
        }

        else -> {
            UnAuthParams
        }
    }
}

fun UserCampaignInfo.asConsumerCampaignInfo(): ConsumerCampaignInfo {
    return ConsumerCampaignInfo(
        campaignId = campaignId,
        engagementId = engagementId,
        sessionId = sessionId,
        visitorId = visitorId,
        contextId = contextId
    )
}

fun SDEDialogParams.asMonitoringParams(): MonitoringParams {
    val entryPoints = runCatching { JSONArray(entryPoints) }
    val engagementAttributes = runCatching { JSONArray(engagementAttrs) }
    return MonitoringParams(
        pageId = pageId,
        entryPoints = entryPoints.getOrNull(),
        engagementAttributes = engagementAttributes.getOrNull()
    )
}