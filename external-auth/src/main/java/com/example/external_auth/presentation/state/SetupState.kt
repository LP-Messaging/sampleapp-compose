package com.example.external_auth.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
data class SetupState(
    val brandId: String = "",
    val appInstallId: String = "",
    val authType: AuthType? = null,
    val userCampaignInfo: UserCampaignInfo = UserCampaignInfo(),
    val showSDEDialog: Boolean = false
)