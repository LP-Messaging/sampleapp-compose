package com.example.external_auth.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
data class UserCampaignInfo(
    val engagementId: String = "",
    val sessionId: String = "",
    val campaignId: String = "",
    val contextId: String = "",
    val visitorId: String = ""
)