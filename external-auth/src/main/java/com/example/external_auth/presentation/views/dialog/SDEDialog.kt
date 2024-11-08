package com.example.external_auth.presentation.views.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.external_auth.R
import com.example.external_auth.presentation.state.dialog.SDEDialogParams
import com.example.external_auth.presentation.views.DefaultButton
import com.example.external_auth.presentation.views.DefaultInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDEDialog(
    onSDESubmitted: (SDEDialogParams) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState, modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = stringResource(id = R.string.title_sde_dialog))

            Spacer(modifier = Modifier.height(16.dp))

            var sdeDialogParams by remember { mutableStateOf(SDEDialogParams()) }
            DefaultInput(
                value = sdeDialogParams.consumerId,
                onValueChanged = {
                    sdeDialogParams = sdeDialogParams.copy(consumerId = it)
                },
                hintRes = R.string.hint_consumer_id,
                modifier = Modifier.fillMaxWidth()
            )

            DefaultInput(
                value = sdeDialogParams.pageId,
                onValueChanged = {
                    sdeDialogParams = sdeDialogParams.copy(pageId = it)
                },
                hintRes = R.string.hint_page_id,
                modifier = Modifier.fillMaxWidth()
            )

            DefaultInput(
                value = sdeDialogParams.entryPoints,
                onValueChanged = {
                    sdeDialogParams = sdeDialogParams.copy(entryPoints = it)
                },
                hintRes = R.string.hint_entry_points,
                modifier = Modifier.fillMaxWidth()
            )

            DefaultInput(
                value = sdeDialogParams.engagementAttrs,
                onValueChanged = {
                    sdeDialogParams = sdeDialogParams.copy(engagementAttrs = it)
                },
                hintRes = R.string.hint_engagement_attrs,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            DefaultButton(
                title = stringResource(id = R.string.button_get_egagement),
                onClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onSDESubmitted(sdeDialogParams)
                        onDismissRequest()
                    }
                }
            )
        }
    }

}