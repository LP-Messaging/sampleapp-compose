package com.example.external_auth.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
sealed interface AuthType {

    val isEmpty: Boolean
}

@JvmInline
@Immutable
value class CodeType(val code: String): AuthType {

    override val isEmpty: Boolean get() = code.isBlank()
}

@Immutable
data class ImplicitType(val token: String, val isEncrypted: Boolean): AuthType {

    override val isEmpty: Boolean get() = token.isBlank()
}

@Immutable
data object UnauthType: AuthType {

    override val isEmpty: Boolean = false
}