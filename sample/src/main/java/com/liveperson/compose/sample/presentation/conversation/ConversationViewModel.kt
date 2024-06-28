package com.liveperson.compose.sample.presentation.conversation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liveperson.common.AppResult
import com.liveperson.common.Failure
import com.liveperson.common.Success
import com.liveperson.common.data.liveperson.toAuthParams
import com.liveperson.common.domain.interactor.LPHybridCommandsInteractor
import com.liveperson.common.domain.repository.AuthParamsRepository
import com.liveperson.compose.common_ui.wrapper.LPArguments
import com.liveperson.compose.sample.presentation.conversation.dto.ConversationScreenEffect
import com.liveperson.compose.sample.presentation.conversation.dto.ShowToastMessageEffect
import com.liveperson.infra.ConversationViewParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConversationViewModel(
    private val hybridCommandsInteractor: LPHybridCommandsInteractor,
    private val authParamsRepository: AuthParamsRepository
) : ViewModel() {

    companion object {
        private const val SDK_MAKER_FCM_APP_ID: String = "com.liveperson.messaging.test"
        private const val TAG = "HYBRID"
    }

    private val _currentBrandId: MutableStateFlow<String> = MutableStateFlow("")
    private val _effects = Channel<ConversationScreenEffect>()

    internal val effects: Flow<ConversationScreenEffect> = _effects.receiveAsFlow()

    internal val lpArguments = _currentBrandId.filter { it.isNotBlank() }
        .map { brandId ->
            val appId = SDK_MAKER_FCM_APP_ID
            val credentials = authParamsRepository.getCredentialsForBrand(brandId) ?: return@map null
            val conversationParams = ConversationViewParams(true)
            LPArguments(brandId, appId, credentials.appInstallId, credentials.toAuthParams(), conversationParams)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    internal fun sendMessage(message: String) = performCommand{
        hybridCommandsInteractor.sendMessage(message)
    }

    internal fun openCamera() = performCommand{
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

    internal fun showConversation(brandId: String) {
        _currentBrandId.value = brandId
    }

    private fun<T, E: Exception> performCommand(block: suspend () -> AppResult<T, E>) {
        viewModelScope.launch {
            when (val result = block()) {
                is Success<T> -> Log.e(TAG, "Finished successfully")
                is Failure<E> -> _effects.send(ShowToastMessageEffect(result.data.message ?: result.data.toString()))
            }
        }
    }

    override fun onCleared() {
        _effects.close()
        super.onCleared()
    }
}