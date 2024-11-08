package com.example.external_auth.presentation.views

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.external_auth.R
import com.example.external_auth.presentation.state.AuthType
import com.example.external_auth.presentation.state.CodeType
import com.example.external_auth.presentation.state.ImplicitType
import com.example.external_auth.presentation.state.UnauthType

@Composable
fun AuthTypeView(
    authType: AuthType,
    onAuthTypeChanged:(AuthType) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(text = stringResource(R.string.title_choose_authentication_flow))
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
            onClick = { if (authType !is CodeType) onAuthTypeChanged(CodeType("")) },
            selected = authType is CodeType
        ) {
            Text(
                text = stringResource(R.string.text_code)
            )
        }
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
            onClick = { if (authType !is ImplicitType) onAuthTypeChanged(ImplicitType("", false)) },
            selected = authType is ImplicitType
        ) {
            Text(
                text = stringResource(R.string.text_implicit)
            )
        }
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
            onClick = { if (authType != UnauthType) onAuthTypeChanged(UnauthType) },
            selected = authType == UnauthType
        ) {
            Text(
                text = stringResource(id = R.string.text_unauth)
            )
        }
    }
}