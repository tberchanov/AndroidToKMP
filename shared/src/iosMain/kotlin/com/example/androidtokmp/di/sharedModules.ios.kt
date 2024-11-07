package com.example.androidtokmp.di

import com.example.androidtokmp.presentation.NumbersInfoViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun viewModelModule(): Module = module {
    /*
    * TODO ViewModel should not be a singleton. This is just a workaround for now.
    *  Instead ViewModel resources should be cleared when the ViewModel is destroyed.
    * */
    single { NumbersInfoViewModel(get(), get(), get()) }
}

fun setupDI() {
    startKoin {
        modules(appModule)
    }
}