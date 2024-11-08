package com.liveperson.compose.sample.presentation.navigation

import android.os.Parcelable
import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.ConsumerCampaignInfo
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
sealed interface AppNavigation: Parcelable {

    @Serializable
    @Parcelize
    data object Setup : AppNavigation

    @Serializable
    @Parcelize
    data class Conversation(
        val brandId: String,
        val appId: String,
        val appInstallId: String,
        val authParams: AuthParams,
        val campaign: ConsumerCampaignInfo
    ): AppNavigation
}