package com.liveperson.common.domain.interactor

import com.liveperson.common.AppResult
import com.liveperson.common.domain.AuthParams

interface LivePersonAuthInteractor {

    fun reconnect(authParams: AuthParams)

    suspend fun logout(brandId: String, appId: String): AppResult<Unit, Unit>

    suspend fun initialize(brandId: String, appId: String?, appInstallId: String): AppResult<Unit, Throwable>
}