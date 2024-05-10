package com.liveperson.common.domain.interactor

import kotlinx.coroutines.flow.Flow

interface LivePersonEventsInteractor {

    fun observeEvents(vararg event: String): Flow<LivePersonEvent>
}