package com.example.external_auth.presentation.auth.dto

data class UserMonitoringParams(
    val pageId: String,
    val consumerId: String,
    val entryPoints: String,
    val engagementAttrs: String
)