package com.liveperson.compose.sample.presentation.conversation.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.liveperson.compose.common_ui.views.LPConversationScreen
import com.liveperson.compose.common_ui.wrapper.LPArguments
import com.liveperson.compose.sample.R
import com.liveperson.compose.sample.presentation.conversation.ConversationViewModel
import com.liveperson.compose.sample.presentation.conversation.dto.ShowToastMessageEffect
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

const val ConversationScreenEndPoint = "conversation"
const val KEY_BRAND_ID = "brand.id"

@Composable
fun ConversationScreen(modifier: Modifier = Modifier, brandId: String) {
    val viewModel: ConversationViewModel = koinViewModel()
    val lpArguments: LPArguments? by viewModel.lpArguments.collectAsState()

    var textState by rememberSaveable { mutableStateOf("") }
    var isReadOnlyMode by rememberSaveable { mutableStateOf(true) }
    Column(modifier) {

        LaunchedEffect(key1 = brandId) {
            viewModel.showConversation(brandId)
        }

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            visible = lpArguments != null
        ) {
            LPConversationScreen(modifier = Modifier.fillMaxSize(), arguments = lpArguments!!)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 4.dp)
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionButton(
                onSendMessageClick = { viewModel.openCamera() },
                buttonText = stringResource(id = R.string.text_open_camera)
            )
            ActionButton(
                onSendMessageClick = { viewModel.openGallery() },
                buttonText = stringResource(id = R.string.text_open_gallery)
            )
            ActionButton(
                onSendMessageClick = { viewModel.shareFile() },
                buttonText = stringResource(id = R.string.text_open_filechooser)
            )
            ReadOnlyModeSwitch(isReadOnlyMode = isReadOnlyMode) {
                viewModel.changeReadOnlyMode(it)
                isReadOnlyMode = it
            }
        }
        AnimatedVisibility(visible = isReadOnlyMode) {
            MessageBox(
                input = textState,
                onInputChanged = { textState = it },
                onSendMessageClick = {
                    viewModel.sendMessage(it)
                    textState = ""
                })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionButton(
                onSendMessageClick = {
                    viewModel.sendMessage(it)
                },
                buttonText = "Send Text1"
            )
            ActionButton(
                onSendMessageClick = {
                    viewModel.sendMessage(it)
                },
                buttonText = "Send Text2"
            )
            ActionButton(
                onSendMessageClick = {
                    viewModel.sendMessage(it)
                },
                buttonText = "Send Text3"
            )
        }
    }
    val content = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.effects.collectLatest {
            when (it) {
                is ShowToastMessageEffect -> {
                    Toast.makeText(content, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}