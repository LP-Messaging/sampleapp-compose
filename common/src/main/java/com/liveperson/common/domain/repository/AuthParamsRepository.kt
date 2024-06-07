package com.liveperson.common.domain.repository

import com.liveperson.common.domain.AuthParams

interface AuthParamsRepository {

    suspend fun setLatestBrandId(brandId: String?)

    suspend fun getLatestBrandId(): String?

    suspend fun clearData()

    suspend fun saveCredentials(key: String, value: AuthParams)

    suspend fun saveMonitoringIdForBrand(key: String, value: String)

    suspend fun getCredentialsForBrand(key: String): AuthParams?

    suspend fun getMonitoringIdForBrand(key: String): String
}