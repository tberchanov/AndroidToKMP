package com.example.androidtokmp.di

import com.example.androidtokmp.data.remote.NumbersApi
import com.example.androidtokmp.data.NumbersLocalDataSource
import com.example.androidtokmp.data.remote.NumbersRemoteDataSource
import com.example.androidtokmp.data.NumbersRepositoryImpl
import com.example.androidtokmp.domain.NumbersRepository
import com.example.androidtokmp.domain.usecase.GetNumberInfoUseCase
import com.example.androidtokmp.domain.usecase.GetSavedNumberInfoUseCase
import com.example.androidtokmp.domain.usecase.SaveNumberUseCase
import com.example.androidtokmp.presentation.NumbersInfoViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        getApiClient("http://numbersapi.com", NumbersApi::class.java)
    }
    single { NumbersLocalDataSource(androidContext()) }
    single { NumbersRemoteDataSource(get()) }
    single<NumbersRepository> { NumbersRepositoryImpl(get(), get()) }
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

private fun <T> getApiClient(baseUrl: String, api: Class<T>): T {
    val httpClient = getOkHttpClient()
    return Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
        .create(api)
}

private fun getOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    val httpClient = OkHttpClient.Builder()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    if (BuildConfig.DEBUG) {
        httpClient.addInterceptor(interceptor)
    }
    return httpClient.build()
}
