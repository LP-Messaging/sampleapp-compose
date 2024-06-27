package com.liveperson.compose.sample.presentation.conversation.dto

internal sealed interface ConversationScreenEffect

internal class ShowToastMessageEffect(val message: String): ConversationScreenEffect