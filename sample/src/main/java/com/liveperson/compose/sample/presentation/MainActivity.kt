package com.liveperson.compose.sample.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.external_auth.presentation.SetupScreen
import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.ConsumerCampaignInfo
import com.liveperson.compose.common_ui.utils.plus
import com.liveperson.compose.sample.presentation.conversation.ConversationScreen
import com.liveperson.compose.sample.presentation.navigation.AppNavigation
import com.liveperson.compose.sample.presentation.navigation.types.AuthNavType
import com.liveperson.compose.sample.presentation.navigation.types.CampaignInfoNavType
import com.liveperson.compose.sample.presentation.ui.theme.SampleAppTheme
import kotlin.reflect.typeOf

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SampleAppTheme {
                // A surface container using the 'background' color from the theme
                val navHostController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navHostController,
                        startDestination = AppNavigation.Setup
                    ) {
                        composable<AppNavigation.Setup> {
                            Scaffold { paddings ->
                                SetupScreen(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(paddings + PaddingValues(horizontal = 16.dp))
                                        .verticalScroll(rememberScrollState()),
                                    onShowConversation = { brandId, appId, appInstallId, authParams, campaignInfo ->
                                        val route = AppNavigation.Conversation(brandId, appId, appInstallId, authParams, campaignInfo)
                                        navHostController.navigate(route)
                                    }
                                )
                            }
                        }
                        composable<AppNavigation.Conversation>(
                            typeMap = mapOf(
                                typeOf<AuthParams>() to AuthNavType(),
                                typeOf<ConsumerCampaignInfo>() to CampaignInfoNavType(),
                            )
                        ) {
                            val setup: AppNavigation.Conversation = it.toRoute()
                            Scaffold { paddings ->
                                ConversationScreen(
                                    brandId = setup.brandId,
                                    appId = setup.appId,
                                    appInstallId = setup.appInstallId,
                                    authParams = setup.authParams,
                                    campaignInfo = setup.campaign,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(paddings + WindowInsets.ime.asPaddingValues())
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}