package com.liveperson.compose.common_ui

import com.liveperson.compose.common_ui.wrapper.LivePersonWrapperViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val lpPresentationModule = module {

    viewModel {
        LivePersonWrapperViewModel(get(), get(), get())
    }
}