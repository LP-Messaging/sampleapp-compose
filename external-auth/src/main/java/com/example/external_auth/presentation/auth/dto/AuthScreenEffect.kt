package com.example.external_auth.presentation.auth.dto

internal sealed interface AuthScreenEffect

internal data class OpenConversationScreenEffect(val brandId: String) : AuthScreenEffect

internal data class ShowToastMessageEffect(val resId: Int): AuthScreenEffect