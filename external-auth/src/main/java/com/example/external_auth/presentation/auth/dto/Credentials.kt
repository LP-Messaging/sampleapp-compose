package com.example.external_auth.presentation.auth.dto

import androidx.compose.runtime.Stable

@Stable
internal sealed interface Credentials

@Stable
internal data class CodeCredentials(
    val code: String
) : Credentials

@Stable
internal data class ImplicitCredentials(
    val token: String
) : Credentials

@Stable
internal data class UnAuthCredentials(
    val appInstallId: String,
    val engagementDetails: UserEngagementDetails
) : Credentials

@Stable
internal data class UserEngagementDetails(
    val engagementId: String,
    val sessionId: String,
    val campaignId: String,
    val contextId: String,
    val visitorId: String
)
