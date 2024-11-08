package com.example.external_auth.di

import com.example.external_auth.presentation.SetupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val externalAuthVmModule = module {
    viewModel {
        SetupViewModel(get(), get())
    }
}