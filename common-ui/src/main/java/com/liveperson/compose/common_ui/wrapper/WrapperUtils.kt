package com.liveperson.compose.common_ui.wrapper

import androidx.compose.runtime.Stable
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import com.liveperson.infra.ConversationViewParams
import com.liveperson.infra.auth.LPAuthenticationParams

private const val KEY_BRAND_ID = "brand_id"
private const val KEY_AUTH_PARAMS = "auth_params"
private const val KEY_APP_ID = "app_id"
private const val KEY_CONVERSATION_PARAMS = "conversation_params"

@Stable
data class LPArguments(
    val brandId: String,
    val appId: String,
    val authParams: LPAuthenticationParams,
    val conversationViewParams: ConversationViewParams
)

internal fun LPArguments.toBundle() = bundleOf(
    KEY_BRAND_ID to brandId,
    KEY_APP_ID to appId,
    KEY_AUTH_PARAMS to authParams,
    KEY_CONVERSATION_PARAMS to conversationViewParams
)

internal val SavedStateHandle.brandId: String
    get() = requireNotNull(get(KEY_BRAND_ID))

internal val SavedStateHandle.appId: String
    get() = requireNotNull(get(KEY_APP_ID))

internal val SavedStateHandle.authParams: LPAuthenticationParams
    get() = requireNotNull(get(KEY_AUTH_PARAMS))

internal val SavedStateHandle.conversationParams: ConversationViewParams
    get() = requireNotNull(get(KEY_CONVERSATION_PARAMS))


