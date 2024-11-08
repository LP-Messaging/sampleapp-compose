package com.liveperson.common.utils

fun Throwable.getMessageOrDefault(defaultMessage: String): String {
    return message ?: defaultMessage
}