package com.liveperson.compose.sample.presentation.conversation.effects

internal sealed interface ConversationScreenEffect

internal class ShowToastMessageEffect(val message: String): ConversationScreenEffect