package com.liveperson.common.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EngagementDetails(
    val campaignId: String,
    val engagementId: String,
    val sessionId: String,
    val visitorId: String,
    val contextId: String,
)

sealed interface MonitoringParamsHolder {

    val engagementDetails: EngagementDetails?
}


@Serializable
sealed interface AuthParams {
    val appInstallId: String?
}

sealed interface Authenticated: AuthParams {

    val credentials: String
}

@Serializable
data class CodeParams(
    @SerialName("code") override val credentials: String,
    @SerialName("appInstallId") override val appInstallId: String? = null,
    @SerialName("engagementDetails") override val engagementDetails: EngagementDetails? = null,

) : Authenticated, MonitoringParamsHolder

sealed interface ImplicitParams: Authenticated, MonitoringParamsHolder

@Serializable
data class ImplicitJWTParams(
    @SerialName("jwt") override val credentials: String,
    @SerialName("appInstallId") override val appInstallId: String? = null,
    @SerialName("engagementDetails") override val engagementDetails: EngagementDetails? = null,
) : ImplicitParams

@Serializable
data class ImplicitJWEParams(
    @SerialName("jwe") override val credentials: String,
    @SerialName("appInstallId") override val appInstallId: String? = null,
    @SerialName("engagementDetails") override val engagementDetails: EngagementDetails? = null,
) : ImplicitParams

@Serializable
data class UnAuthParams(
    @SerialName("appInstallId") override val appInstallId: String,
    @SerialName("engagementDetails") override val engagementDetails: EngagementDetails
) : AuthParams, MonitoringParamsHolder

@Serializable
data class PKCEParams(
    @SerialName("token") val token: String,
    @SerialName("verifier") val codeVerifier: String,
    @SerialName("appInstallId") override val appInstallId: String? = null
) : AuthParams

@Serializable
data class StepUpParams<Params : AuthParams>(@SerialName("stepUpParams") val auth: Params) : AuthParams by auth

@Serializable
data class MultipleConsumerIdpParams<Params : AuthParams>(
    @SerialName("params") val auth: Params,
    @SerialName("issuer") val issuer: String
) : AuthParams by auth

@Serializable
data class SignUp(
    @SerialName("appInstallId") override val appInstallId: String? = null
) : AuthParams
