package com.example.external_auth.di

import com.example.external_auth.presentation.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val externalAuthVmModule = module {
    viewModel {
        AuthViewModel(get(), get(), get())
    }
}