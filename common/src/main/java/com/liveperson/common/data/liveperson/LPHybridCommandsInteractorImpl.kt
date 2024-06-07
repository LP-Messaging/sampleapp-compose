package com.liveperson.common.data.liveperson

import com.liveperson.common.AppResult
import com.liveperson.common.data.liveperson.coroutines.executeCommand
import com.liveperson.common.domain.interactor.LPHybridCommandsInteractor
import com.liveperson.messaging.hybrid.commands.exceptions.HybridSDKException
import com.liveperson.messaging.sdk.api.LivePerson

internal class LPHybridCommandsInteractorImpl : LPHybridCommandsInteractor {


    override suspend fun sendMessage(message: String): AppResult<Unit, HybridSDKException> {
        return executeCommand { LivePerson.sendTextMessage(message, it) }
    }

    override suspend fun openCamera(): AppResult<Unit, HybridSDKException> {
        return executeCommand { LivePerson.fileSharingOpenCamera(it) }
    }

    override suspend fun openGallery(): AppResult<Unit, HybridSDKException> {
        return executeCommand { LivePerson.fileSharingOpenGallery(it) }
    }

    override suspend fun fileSharingOpenFileChooser(): AppResult<Unit, HybridSDKException> {
        return executeCommand { LivePerson.fileSharingOpenFile(it) }
    }

    override suspend fun changeReadOnlyMode(isReadOnly: Boolean): AppResult<Unit, HybridSDKException> {
        return executeCommand { LivePerson.changeReadOnlyMode(isReadOnly, it) }
    }
}