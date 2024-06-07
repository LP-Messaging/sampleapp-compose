package com.liveperson.common.domain.interactor

import com.liveperson.common.AppResult
import com.liveperson.messaging.hybrid.commands.exceptions.HybridSDKException

interface LPHybridCommandsInteractor {

    suspend fun sendMessage(message: String): AppResult<Unit, HybridSDKException>

    suspend fun openCamera(): AppResult<Unit, HybridSDKException>

    suspend fun openGallery(): AppResult<Unit, HybridSDKException>

    suspend fun fileSharingOpenFileChooser(): AppResult<Unit, HybridSDKException>

    suspend fun changeReadOnlyMode(isReadOnly: Boolean): AppResult<Unit, HybridSDKException>
}