package com.example.androidtokmp.di

import com.example.androidtokmp.presentation.NumbersInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { NumbersInfoViewModel(get(), get(), get()) }
}

val appModule = module {
    includes(dataModule, domainModule, presentationModule)
}
