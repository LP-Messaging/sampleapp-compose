package com.liveperson.compose.sample.presentation.navigation.types

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.liveperson.common.domain.AuthParams
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthNavType: NavType<AuthParams>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): AuthParams? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, AuthParams::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): AuthParams {
        return Json.decodeFromString<AuthParams>(value)
    }

    override fun serializeAsValue(value: AuthParams): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: AuthParams) {
        bundle.putParcelable(key, value)
    }
}