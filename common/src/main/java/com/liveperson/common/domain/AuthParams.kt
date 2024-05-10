package com.liveperson.common.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface Authenticated {

    val credentials: String
}

@Serializable
sealed interface AuthParams

@Serializable
data class CodeParams(@SerialName("code") override val credentials: String) : AuthParams, Authenticated

@Serializable
data class ImplicitParams(@SerialName("jwt") override val credentials: String) : AuthParams, Authenticated

@Serializable
data class ImplicitJWEParams(@SerialName("jwe") override val credentials: String) : AuthParams, Authenticated

@Serializable
data class UnAuth(@SerialName("windowId") val windowId: String) : AuthParams

@Serializable
data class PKCEParams(@SerialName("token") val token: String, @SerialName("verifier") val codeVerifier: String) : AuthParams

@Serializable
data class StepUpParams<Params : AuthParams>(@SerialName("stepUpParams") val auth: Params) : AuthParams

@Serializable
data class MultipleConsumerIdpParams<Params : AuthParams>(
    @SerialName("params") val auth: Params,
    @SerialName("issuer") val issuer: String
) : AuthParams

@Serializable
class SignUp : AuthParams
