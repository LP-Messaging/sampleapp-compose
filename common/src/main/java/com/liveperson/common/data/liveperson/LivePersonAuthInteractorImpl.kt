package com.liveperson.common.data.liveperson

import android.content.Context
import com.liveperson.common.AppResult
import com.liveperson.common.data.liveperson.coroutines.executeLogin
import com.liveperson.common.data.liveperson.coroutines.executeLogout
import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.interactor.LivePersonAuthInteractor
import com.liveperson.infra.InitLivePersonProperties
import com.liveperson.infra.MonitoringInitParams
import com.liveperson.messaging.sdk.api.LivePerson

internal class LivePersonAuthInteractorImpl(private val context: Context):
    LivePersonAuthInteractor {

    override fun reconnect(authParams: AuthParams) {
        LivePerson.reconnect(authParams.toAuthParams())
    }

    override suspend fun logout(brandId: String, appId: String): AppResult<Unit, Unit> {
        return executeLogout {
            LivePerson.logOut(context, brandId, appId, it)
        }
    }

    override suspend fun initialize(
        brandId: String,
        appId: String?,
        appInstallId: String
    ): AppResult<Unit, Throwable> {
        return executeLogin {
            val monitoringInitParams = MonitoringInitParams(appInstallId);
            val properties = InitLivePersonProperties(brandId, appId, monitoringInitParams, it)
            LivePerson.initialize(context, properties)
        }
    }
}