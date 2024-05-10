package com.liveperson.common.domain.interactor

import com.liveperson.common.AppResult
import com.liveperson.messaging.hybrid.commands.messaging.UrlData

interface LPHybridCommandsInteractor {

    suspend fun sendMessage(message: String): AppResult<Unit, Throwable>

    suspend fun sendMessageWithUrl(message: String, urlData: UrlData): AppResult<Unit, Throwable>

    suspend fun openCamera(): AppResult<Unit, Throwable>

    suspend fun openGallery(): AppResult<Unit, Throwable>

    suspend fun fileSharingOpenFileChooser(): AppResult<Unit, Throwable>

    suspend fun changeReadOnlyMode(isReadOnly: Boolean): AppResult<Unit, Throwable>
}