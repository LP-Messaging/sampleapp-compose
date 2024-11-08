package com.example.external_auth.presentation.effects

import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.ConsumerCampaignInfo

sealed interface SetupEffect {
    @JvmInline
    value class FailureEffect(val message: String): SetupEffect

    data class NavigateToConversationEffect(
        val brandId: String,
        val appId: String,
        val appInstallId: String,
        val authParams: AuthParams,
        val campaign: ConsumerCampaignInfo
    ): SetupEffect
}