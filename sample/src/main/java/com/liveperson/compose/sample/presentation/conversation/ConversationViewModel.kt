package com.liveperson.compose.sample.presentation.conversation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liveperson.common.AppResult
import com.liveperson.common.Failure
import com.liveperson.common.Success
import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.ConsumerCampaignInfo
import com.liveperson.common.domain.interactor.LPHybridCommandsInteractor
import com.liveperson.common.utils.getMessageOrDefault
import com.liveperson.compose.common_ui.wrapper.LPArguments
import com.liveperson.compose.sample.presentation.conversation.effects.ConversationScreenEffect
import com.liveperson.compose.sample.presentation.conversation.effects.ShowToastMessageEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConversationViewModel(
    brandId: String,
    appId: String,
    appInstallId: String,
    authParams: AuthParams,
    campaignInfo: ConsumerCampaignInfo,
    private val hybridCommandsInteractor: LPHybridCommandsInteractor
) : ViewModel() {

    companion object {
        private const val TAG = "ConversationViewModel"
    }

    private val _effects = Channel<ConversationScreenEffect>()

    internal val effects: Flow<ConversationScreenEffect> = _effects.receiveAsFlow()

    internal val lpArguments = flowOf(
        LPArguments(brandId, appId, appInstallId, authParams, campaignInfo)
    ).stateIn(viewModelScope, SharingStarted.Lazily, null)

    internal fun sendMessage(message: String) = performCommand {
        hybridCommandsInteractor.sendMessage(message)
    }

    internal fun openCamera() = performCommand {
        hybridCommandsInteractor.openCamera()
    }

    internal fun openGallery() = performCommand {
        hybridCommandsInteractor.openGallery()
    }

    internal fun shareFile() = performCommand {
        hybridCommandsInteractor.fileSharingOpenFileChooser()
    }

    internal fun changeReadOnlyMode(isReadOnly: Boolean) = performCommand {
        hybridCommandsInteractor.changeReadOnlyMode(isReadOnly = isReadOnly)
    }

    private fun <T, E : Exception> performCommand(block: suspend () -> AppResult<T, E>) {
        viewModelScope.launch {
            when (val result = block()) {
                is Success<T> -> Log.e(TAG, "Finished successfully")
                is Failure<E> -> _effects.send(
                    ShowToastMessageEffect(
                        result.data.getMessageOrDefault("Failed to perform hybrid command")
                    )
                )
            }
        }
    }

    override fun onCleared() {
        _effects.close()
        super.onCleared()
    }
}