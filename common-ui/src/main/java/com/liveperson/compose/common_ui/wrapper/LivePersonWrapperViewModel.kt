package com.liveperson.compose.common_ui.wrapper

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liveperson.common.Failure
import com.liveperson.common.Success
import com.liveperson.common.domain.interactor.LivePersonAuthInteractor
import com.liveperson.common.domain.interactor.LivePersonEventsInteractor
import com.liveperson.common.domain.interactor.TokenExpiredEvent
import com.liveperson.infra.ConversationViewParams
import com.liveperson.infra.auth.LPAuthenticationParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class LivePersonWrapperViewModel(
    savedStateHandle: SavedStateHandle,
    livePersonAuthInteractor: LivePersonAuthInteractor,
    livePersonEventsInteractor: LivePersonEventsInteractor,
) : ViewModel() {

    private val _livePersonEventJob: Job = livePersonEventsInteractor.observeEvents("")
        .filterIsInstance<TokenExpiredEvent>()
        .onEach { }
        .launchIn(viewModelScope)

    val initState: Flow<LPShowConversationData> = flow {
        val brandId: String = savedStateHandle.brandId
        val appId: String = savedStateHandle.appId
        emit(livePersonAuthInteractor.initialize(brandId = brandId, appId = appId))
    }.flowOn(Dispatchers.IO).map { result ->
        when (result) {
            is Success<Unit> -> {
                val brandId: String = savedStateHandle.brandId
                val authParams: LPAuthenticationParams = savedStateHandle.authParams
                val conversationParams: ConversationViewParams = savedStateHandle.conversationParams
                LPShowConversationData(brandId, authParams, conversationParams)
            }
            is Failure<Throwable> -> null
        }
    }.catch {
        emit(null)
    }.filterNotNull()


}