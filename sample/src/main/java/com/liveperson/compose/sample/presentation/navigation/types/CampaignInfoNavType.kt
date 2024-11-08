package com.liveperson.compose.sample.presentation.navigation.types

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.liveperson.common.domain.ConsumerCampaignInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CampaignInfoNavType: NavType<ConsumerCampaignInfo>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ConsumerCampaignInfo? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, ConsumerCampaignInfo::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): ConsumerCampaignInfo {
        return Json.decodeFromString<ConsumerCampaignInfo>(value)
    }

    override fun serializeAsValue(value: ConsumerCampaignInfo): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: ConsumerCampaignInfo) {
        bundle.putParcelable(key, value)
    }
}