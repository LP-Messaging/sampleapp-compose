package com.example.external_auth.presentation.state.dialog

import androidx.compose.runtime.Immutable

@Immutable
data class SDEDialogParams(
    val pageId: String = "",
    val consumerId: String = "",
    val entryPoints: String = "",
    val engagementAttrs: String = ""
)