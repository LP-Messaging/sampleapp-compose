package com.example.external_auth.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.external_auth.R
import com.example.external_auth.presentation.state.UserCampaignInfo
import com.example.external_auth.presentation.views.DefaultInput

@Composable
fun CampaignInfoView(
    campaignInfo: UserCampaignInfo,
    onCampaignInfoChanged: (UserCampaignInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

        Text(text = stringResource(id = R.string.title_monitoring_info))

        Spacer(modifier = Modifier.height(8.dp))

        DefaultInput(
            value = campaignInfo.campaignId,
            onValueChanged = {
                onCampaignInfoChanged(campaignInfo.copy(campaignId = it))
            },
            hintRes = R.string.hint_campaing_id,
            modifier = Modifier.fillMaxWidth()
        )

        DefaultInput(
            value = campaignInfo.contextId,
            onValueChanged = {
                onCampaignInfoChanged(campaignInfo.copy(contextId = it))
            },
            hintRes = R.string.hint_context_id,
            modifier = Modifier.fillMaxWidth()
        )

        DefaultInput(
            value = campaignInfo.sessionId,
            onValueChanged = {
                onCampaignInfoChanged(campaignInfo.copy(sessionId = it))
            },
            hintRes = R.string.hint_session_id,
            modifier = Modifier.fillMaxWidth()
        )

        DefaultInput(
            value = campaignInfo.engagementId,
            onValueChanged = {
                onCampaignInfoChanged(campaignInfo.copy(engagementId = it))
            },
            hintRes = R.string.hint_engagement_id,
            modifier = Modifier.fillMaxWidth()
        )

        DefaultInput(
            value = campaignInfo.visitorId,
            onValueChanged = {
                onCampaignInfoChanged(campaignInfo.copy(visitorId = it))
            },
            hintRes = R.string.hint_visitor_id,
            modifier = Modifier.fillMaxWidth()
        )
    }
}