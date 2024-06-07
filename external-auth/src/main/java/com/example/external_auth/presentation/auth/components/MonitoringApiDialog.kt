package com.example.external_auth.presentation.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.external_auth.presentation.auth.dto.UserMonitoringParams
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun MonitoringApiDialog(
    state: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    params: UserMonitoringParams,
    onMonitoringParamsChanged: (UserMonitoringParams) -> Unit,
    onDismissRequest: () -> Unit,
    onGetEngagementClicked: () -> Unit,
    onSendSdeClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = state) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    WindowInsets.systemBars
                        .add(WindowInsets(left = 24.dp, right = 24.dp))
                        .asPaddingValues()
                )
        ) {
            AuthField(
                value = params.consumerId,
                onValueChange = { onMonitoringParamsChanged(params.copy(consumerId = it)) },
                label = "Consumer id"
            )
            AuthField(
                value = params.pageId,
                onValueChange = { onMonitoringParamsChanged(params.copy(pageId = it)) },
                label = "Page id"
            )
            AuthField(
                value = params.entryPoints,
                onValueChange = { onMonitoringParamsChanged(params.copy(entryPoints = it)) },
                label = "Entry points"
            )
            AuthField(
                value = params.engagementAttrs,
                onValueChange = { onMonitoringParamsChanged(params.copy(engagementAttrs = it)) },
                label = "Engagement attrs"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        scope.launch { state.hide() }
                            .invokeOnCompletion {
                                onGetEngagementClicked()
                                onDismissRequest()
                            }
                    }
                ) {
                    Text(text = "Get Engagement")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        scope.launch { state.hide() }
                            .invokeOnCompletion {
                                onSendSdeClicked()
                                onDismissRequest()
                            }
                    }
                ) {
                    Text(text = "Send SDE")
                }
            }
        }
    }
}