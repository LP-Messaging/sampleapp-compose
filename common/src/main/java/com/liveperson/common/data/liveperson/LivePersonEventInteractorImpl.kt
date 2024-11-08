package com.liveperson.common.data.liveperson

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.liveperson.api.LivePersonIntents
import com.liveperson.common.domain.interactor.LivePersonEvent
import com.liveperson.common.domain.interactor.LivePersonEventsInteractor
import com.liveperson.common.domain.interactor.TokenExpiredEvent
import com.liveperson.common.domain.interactor.UnhandledEvent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

@Suppress("DEPRECATION")
internal class LivePersonEventInteractorImpl(
    private val localBroadcastManager: LocalBroadcastManager
): LivePersonEventsInteractor {

    override fun observeEvents(vararg event: String): Flow<LivePersonEvent> = channelFlow {
        val receiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context?, intent: Intent?) {
                val receivedEvent: LivePersonEvent = intent?.asLivePersonEvent() ?: return
                trySend(receivedEvent)
            }
        }
        localBroadcastManager.registerReceiver(receiver, intentFilterOf(*event))
        awaitClose {
            localBroadcastManager.unregisterReceiver(receiver)
        }
    }

    private fun intentFilterOf(vararg event: String): IntentFilter {
        return event.fold(IntentFilter()) { filter, type ->
            filter.apply { addAction(type) }
        }
    }

    private fun Intent.asLivePersonEvent(): LivePersonEvent {
        return when (val action = action) {
            LivePersonIntents.ILivePersonIntentAction.LP_ON_TOKEN_EXPIRED_INTENT_ACTION -> TokenExpiredEvent
            else -> UnhandledEvent(action ?: "Unknown event")
        }
    }
}