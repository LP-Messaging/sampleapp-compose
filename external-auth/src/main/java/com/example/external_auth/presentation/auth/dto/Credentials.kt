package com.example.external_auth.presentation.auth.dto

import androidx.compose.runtime.Stable

@Stable
internal sealed interface Credentials

@Stable
internal data class CodeCredentials(val code: String) : Credentials

@Stable
internal data class ImplicitCredentials(val token: String) : Credentials
