package com.liveperson.common.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.repository.AuthParamsRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthParamsRepositoryImpl(
    private val json: Json,
    private val sharedPreferences: SharedPreferences
) : AuthParamsRepository {

    companion object {
        private const val KEY_ACTIVE_BRAND_ID = "brand.id.active"
    }

    private val mutex = Mutex()
    override suspend fun setLatestBrandId(brandId: String?){
        mutex.withLock {
            sharedPreferences.edit().putString(KEY_ACTIVE_BRAND_ID, brandId).commit()
        }
    }

    override suspend fun getLatestBrandId(): String? = mutex.withLock {
        sharedPreferences.getString(KEY_ACTIVE_BRAND_ID, null)
    }

    override suspend fun clearData() {
        mutex.withLock {
            sharedPreferences.edit().clear().commit()
        }
    }

    override suspend fun saveCredentials(brandId: String, authParams: AuthParams) {
        mutex.withLock {
            sharedPreferences.edit().putString(brandId, json.encodeToString(authParams)).commit()
        }
    }

    override suspend fun getCredentialsForBrand(brandId: String): AuthParams? = mutex.withLock {
        try {
            json.decodeFromString(sharedPreferences.getString(brandId, "") ?: "")
        } catch (ex: Throwable) {
            Log.e("HERE", "ERROR", ex)
            null
        }
    }

    override suspend fun saveMonitoringIdForBrand(brandId: String, appInstallId: String) {
        if (brandId.isNotEmpty() && appInstallId.isNotEmpty()){
            mutex.withLock {
                appInstallId.let {
                    sharedPreferences.edit().putString("$brandId-appInstallId", appInstallId).commit()
                }
            }
        }
    }

    override suspend fun getMonitoringIdForBrand(brandId: String): String {
        return mutex.withLock {
            sharedPreferences.getString("$brandId-appInstallId", "") ?: ""
        }
    }
}