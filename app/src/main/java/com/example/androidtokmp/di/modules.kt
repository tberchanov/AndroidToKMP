package com.example.androidtokmp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidtokmp.data.NumbersRepositoryImpl
import com.example.androidtokmp.domain.NumbersRepository
import com.example.androidtokmp.domain.usecase.GetNumberInfoUseCase
import com.example.androidtokmp.domain.usecase.GetSavedNumberInfoUseCase
import com.example.androidtokmp.domain.usecase.SaveNumberUseCase
import com.example.androidtokmp.presentation.NumbersInfoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "numbersDataStore")

val dataModule = module {
    single<NumbersRepository> { NumbersRepositoryImpl.create(androidContext().dataStore) }
}

val domainModule = module {
    factory { GetNumberInfoUseCase(get()) }
    factory { GetSavedNumberInfoUseCase(get()) }
    factory { SaveNumberUseCase(get()) }
}

val presentationModule = module {
    viewModel { NumbersInfoViewModel(get(), get(), get()) }
}

val appModule = module {
    includes(dataModule, domainModule, presentationModule)
}
