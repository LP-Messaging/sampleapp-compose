package com.liveperson.common.domain.interactor


sealed interface LivePersonEvent

data object ConversationStartedEvent : LivePersonEvent

data object ConversationResolvedEvent : LivePersonEvent

data class ConnectionChangedEvent(val isConnected: Boolean) : LivePersonEvent

data object TokenExpiredEvent : LivePersonEvent

data object UnauthenticatedUserExpired : LivePersonEvent

data object ErrorEvent : LivePersonEvent

data class UnhandledEvent(val event: String) : LivePersonEvent