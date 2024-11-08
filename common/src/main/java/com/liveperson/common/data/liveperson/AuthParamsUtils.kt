package com.liveperson.common.data.liveperson

import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.CodeParams
import com.liveperson.common.domain.ConsumerCampaignInfo
import com.liveperson.common.domain.ImplicitJWEParams
import com.liveperson.common.domain.ImplicitJWTParams
import com.liveperson.common.domain.MultipleConsumerIdpParams
import com.liveperson.common.domain.PKCEParams
import com.liveperson.common.domain.SignUp
import com.liveperson.common.domain.StepUpParams
import com.liveperson.common.domain.UnAuthParams
import com.liveperson.infra.CampaignInfo
import com.liveperson.infra.auth.LPAuthenticationParams
import com.liveperson.infra.auth.LPAuthenticationType

fun AuthParams.toAuthParams(): LPAuthenticationParams {
    return when (this) {
        is SignUp -> LPAuthenticationParams(LPAuthenticationType.SIGN_UP)
        is CodeParams -> LPAuthenticationParams(LPAuthenticationType.AUTH).setAuthKey(credentials)
        is ImplicitJWTParams -> LPAuthenticationParams(LPAuthenticationType.AUTH).setHostAppJWT(
            credentials
        )

        is ImplicitJWEParams -> LPAuthenticationParams(LPAuthenticationType.AUTH).setHostAppJWT(
            credentials
        )

        is UnAuthParams -> LPAuthenticationParams(LPAuthenticationType.UN_AUTH)
        is PKCEParams -> LPAuthenticationParams(LPAuthenticationType.AUTH).setCodeVerifier(
            codeVerifier
        ).setAuthKey(token)

        is StepUpParams<*> -> auth.toAuthParams().also { it.performStepUp = true }
        is MultipleConsumerIdpParams<*> -> auth.toAuthParams().setIssuerDisplayName(issuer)
    }
}

fun ConsumerCampaignInfo.asCampaignInfo(): CampaignInfo {
    return CampaignInfo(
        campaignId.toLongOrNull() ?: 0,
        engagementId.toLongOrNull() ?: 0,
        contextId,
        connectorId,
        sessionId,
        visitorId
    )
}