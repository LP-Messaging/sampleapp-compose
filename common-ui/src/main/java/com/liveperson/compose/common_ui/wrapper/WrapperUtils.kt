package com.liveperson.compose.common_ui.wrapper

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import com.liveperson.common.data.liveperson.toAuthParams
import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.ConsumerCampaignInfo
import com.liveperson.infra.CampaignInfo
import com.liveperson.infra.ConversationViewParams
import com.liveperson.infra.auth.LPAuthenticationParams

private const val KEY_BRAND_ID = "brand_id"
private const val KEY_APP_INSTALL_ID = "app_install_id"
private const val KEY_AUTH_PARAMS = "auth_params"
private const val KEY_APP_ID = "app_id"
private const val KEY_CONVERSATION_PARAMS = "conversation_params"

@Stable
data class LPArguments(
    val brandId: String,
    val appId: String,
    val appInstallId: String?,
    val authParams: AuthParams,
    val campaignInfo: ConsumerCampaignInfo
)

internal fun LPArguments.toBundle() = bundleOf(
    KEY_BRAND_ID to brandId,
    KEY_APP_ID to appId,
    KEY_APP_INSTALL_ID  to appInstallId,
    KEY_AUTH_PARAMS to authParams.toAuthParams(),
    KEY_CONVERSATION_PARAMS to campaignInfo.toConversationViewParams(true)
)

internal fun ConsumerCampaignInfo.toConversationViewParams(
    viewOnlyMode: Boolean
): ConversationViewParams {
    val conversationViewParams = ConversationViewParams(viewOnlyMode)
    val campaignInfo = try {
        CampaignInfo(campaignId.toLong(), engagementId.toLong(), contextId, connectorId, sessionId, visitorId)
    } catch (ex: Exception) {
        Log.e("WrapperUtils", "Failed to parse campaign", ex)
        null
    }
    conversationViewParams.campaignInfo = campaignInfo
    return conversationViewParams
}

internal val SavedStateHandle.brandId: String
    get() = requireNotNull(get(KEY_BRAND_ID))

internal val SavedStateHandle.appId: String
    get() = requireNotNull(get(KEY_APP_ID))

internal val SavedStateHandle.appInstallId: String
    get() = get(KEY_APP_INSTALL_ID) ?: ""


internal val SavedStateHandle.authParams: LPAuthenticationParams
    get() = requireNotNull(get(KEY_AUTH_PARAMS))

internal val SavedStateHandle.conversationParams: ConversationViewParams
    get() = requireNotNull(get(KEY_CONVERSATION_PARAMS))


