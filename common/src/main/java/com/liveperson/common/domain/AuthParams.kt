package com.liveperson.common.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ConsumerCampaignInfo(
    val campaignId: String,
    val engagementId: String,
    val sessionId: String,
    val visitorId: String,
    val contextId: String,
    val connectorId: String? = null
): Parcelable

@Parcelize
@Serializable
sealed interface AuthParams: Parcelable

@Parcelize
sealed interface Authenticated: AuthParams {

    val credentials: String
}

@Serializable
@Parcelize
data class CodeParams(
    @SerialName("code") override val credentials: String
) : Authenticated

sealed interface ImplicitParams: Authenticated

@Parcelize
@Serializable
data class ImplicitJWTParams(
    @SerialName("jwt") override val credentials: String
) : ImplicitParams

@Parcelize
@Serializable
data class ImplicitJWEParams(
    @SerialName("jwe") override val credentials: String
) : ImplicitParams

@Parcelize
@Serializable
data object UnAuthParams: AuthParams

@Parcelize
@Serializable
data class PKCEParams(
    @SerialName("token") val token: String,
    @SerialName("verifier") val codeVerifier: String,
) : AuthParams

@Parcelize
@Serializable
data class StepUpParams<Params : AuthParams>(@SerialName("stepUpParams") val auth: Params): Parcelable, AuthParams

@Parcelize
@Serializable
data class MultipleConsumerIdpParams<Params : AuthParams>(
    @SerialName("params") val auth: Params,
    @SerialName("issuer") val issuer: String
) : AuthParams

@Parcelize
@Serializable
data object SignUp : AuthParams
