package com.example.androidtokmp.di

import com.example.androidtokmp.data.NumbersLocalDataSource
import com.example.androidtokmp.data.NumbersRepositoryImpl
import com.example.androidtokmp.data.remote.NumbersRemoteDataSource
import com.example.androidtokmp.data.remote.createNumbersApi
import com.example.androidtokmp.data.remote.getApiClient
import com.example.androidtokmp.domain.NumbersRepository
import com.example.androidtokmp.domain.usecase.GetNumberInfoUseCase
import com.example.androidtokmp.domain.usecase.GetSavedNumberInfoUseCase
import com.example.androidtokmp.domain.usecase.SaveNumberUseCase
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun dataStoreModule(): Module

expect fun viewModelModule(): Module

val dataModule = module {
    includes(dataStoreModule())
    single { NumbersLocalDataSource(get()) }
    single { getApiClient("http://numbersapi.com/", Ktorfit::createNumbersApi) }
    single { NumbersRemoteDataSource(get()) }
    single<NumbersRepository> { NumbersRepositoryImpl(get(), get()) }
}

val domainModule = module {
    factory { GetNumberInfoUseCase(get()) }
    factory { GetSavedNumberInfoUseCase(get()) }
    factory { SaveNumberUseCase(get()) }
}

val presentationModule = module {
    includes(viewModelModule())
}

val appModule = module {
    includes(dataModule, domainModule, presentationModule)
}