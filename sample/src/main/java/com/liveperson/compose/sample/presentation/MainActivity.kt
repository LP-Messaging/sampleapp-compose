package com.liveperson.compose.sample.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.external_auth.presentation.auth.AuthScreen
import com.example.external_auth.presentation.auth.AuthScreenPath
import com.liveperson.compose.common_ui.utils.defaultPaddings
import com.liveperson.compose.sample.presentation.conversation.components.ConversationScreen
import com.liveperson.compose.sample.presentation.conversation.components.ConversationScreenEndPoint
import com.liveperson.compose.sample.presentation.conversation.components.KEY_BRAND_ID
import com.liveperson.compose.sample.presentation.ui.theme.SampleAppTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleAppTheme {
                // A surface container using the 'background' color from the theme
                val navHostController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        modifier = Modifier.defaultPaddings(),
                        navController = navHostController,
                        startDestination = AuthScreenPath
                    ) {
                        composable(route = AuthScreenPath) {
                            AuthScreen(
                                modifier = Modifier,
                                onOpenConversationScreen = {
                                    navHostController.navigate("$ConversationScreenEndPoint/$it")
                                }
                            )
                        }
                        composable(
                            route = "$ConversationScreenEndPoint/{$KEY_BRAND_ID}",
                            arguments = listOf(
                                navArgument(KEY_BRAND_ID) { type = NavType.StringType }
                            )
                        ) {
                            ConversationScreen(
                                modifier = Modifier.fillMaxSize(),
                                brandId = it.arguments!!.getString(KEY_BRAND_ID)!!
                            )
                        }
                    }
                }
            }
        }
    }
}