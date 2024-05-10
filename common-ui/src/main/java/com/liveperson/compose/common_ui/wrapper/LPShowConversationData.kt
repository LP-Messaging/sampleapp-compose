package com.liveperson.compose.common_ui.wrapper

import com.liveperson.infra.ConversationViewParams
import com.liveperson.infra.auth.LPAuthenticationParams

internal data class LPShowConversationData(
    val brandId: String,
    val lpAuthenticationParams: LPAuthenticationParams,
    val conversationViewParams: ConversationViewParams
)