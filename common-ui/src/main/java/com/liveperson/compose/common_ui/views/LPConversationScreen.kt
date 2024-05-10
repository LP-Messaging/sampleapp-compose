package com.liveperson.compose.common_ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import com.liveperson.compose.common_ui.wrapper.LPArguments
import com.liveperson.compose.common_ui.wrapper.LivepersonWrapperFragment
import com.liveperson.compose.common_ui.wrapper.toBundle

@Composable
fun LPConversationScreen(modifier: Modifier, arguments: LPArguments) {
    AndroidFragment<LivepersonWrapperFragment>(
        modifier = modifier,
        arguments = arguments.toBundle()
    )
}