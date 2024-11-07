package com.example.androidtokmp.di

import com.example.androidtokmp.presentation.NumbersInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun viewModelModule(): Module = module {
    viewModel { NumbersInfoViewModel(get(), get(), get()) }
}