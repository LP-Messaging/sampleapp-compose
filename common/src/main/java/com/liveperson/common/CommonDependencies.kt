package com.liveperson.common

import android.content.Context
import com.liveperson.common.data.liveperson.LPHybridCommandsInteractorImpl
import com.liveperson.common.data.liveperson.LivePersonAuthInteractorImpl
import com.liveperson.common.data.liveperson.LivePersonEventInteractorImpl
import com.liveperson.common.data.liveperson.LivePersonMonitoringInteractorImpl
import com.liveperson.common.data.persistance.createAuthPreferences
import com.liveperson.common.domain.interactor.LPHybridCommandsInteractor
import com.liveperson.common.domain.interactor.LivePersonAuthInteractor
import com.liveperson.common.domain.interactor.LivePersonEventsInteractor
import com.liveperson.common.domain.interactor.LivePersonMonitoringInteractor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

private const val AUTH_SERIALIZATION_JSON = "seiralization.json"

@OptIn(ExperimentalSerializationApi::class)
val commonDataModule = module {

    single {
        get<Context>().createAuthPreferences()
    }

    single(qualifier = qualifier(AUTH_SERIALIZATION_JSON)) {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }
}

val commonDomainModule = module {
    single<LivePersonAuthInteractor> {
        LivePersonAuthInteractorImpl(get())
    }

    single<LivePersonEventsInteractor> {
        LivePersonEventInteractorImpl(get())
    }

    single<LPHybridCommandsInteractor> {
        LPHybridCommandsInteractorImpl()
    }

    single<LivePersonMonitoringInteractor> {
        LivePersonMonitoringInteractorImpl(get())
    }
}