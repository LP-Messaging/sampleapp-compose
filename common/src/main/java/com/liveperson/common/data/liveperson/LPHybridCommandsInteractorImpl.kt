package com.liveperson.common.data.liveperson

import com.liveperson.common.AppResult
import com.liveperson.common.data.liveperson.coroutines.executeCommand
import com.liveperson.common.domain.interactor.LPHybridCommandsInteractor
import com.liveperson.messaging.hybrid.commands.messaging.UrlData
import com.liveperson.messaging.sdk.api.LivePerson

internal class LPHybridCommandsInteractorImpl : LPHybridCommandsInteractor {


    override suspend fun sendMessage(message: String): AppResult<Unit, Throwable> {
        return executeCommand { LivePerson.sendTextMessage(message, it) }
    }

    override suspend fun sendMessageWithUrl(
        message: String,
        urlData: UrlData
    ): AppResult<Unit, Throwable> {
        return executeCommand { LivePerson.sendTextMessageWithUrl(message, urlData, it) }
    }

    override suspend fun openCamera(): AppResult<Unit, Throwable> {
        return executeCommand { LivePerson.fileSharingOpenCamera(it) }
    }

    override suspend fun openGallery(): AppResult<Unit, Throwable> {
        return executeCommand { LivePerson.fileSharingOpenGallery(it) }
    }

    override suspend fun fileSharingOpenFileChooser(): AppResult<Unit, Throwable> {
        return executeCommand { LivePerson.fileSharingOpenFile(it) }
    }

    override suspend fun changeReadOnlyMode(isReadOnly: Boolean): AppResult<Unit, Throwable> {
        return executeCommand { LivePerson.changeReadOnlyMode(isReadOnly, it) }
    }
}