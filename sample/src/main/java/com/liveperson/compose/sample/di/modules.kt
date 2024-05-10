package com.liveperson.compose.sample.di

import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.external_auth.di.externalAuthVmModule
import com.liveperson.common.commonDataModule
import com.liveperson.common.commonDomainModule
import com.liveperson.compose.common_ui.lpPresentationModule
import com.liveperson.compose.sample.presentation.conversation.ConversationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

    single {
        LocalBroadcastManager.getInstance(get())
    }

    includes(commonDataModule)
}

val domainModule = module {
    includes(commonDomainModule)
}

val presentationModule = module {

    includes(lpPresentationModule, externalAuthVmModule)

    viewModel { ConversationViewModel(get(), get()) }
}
