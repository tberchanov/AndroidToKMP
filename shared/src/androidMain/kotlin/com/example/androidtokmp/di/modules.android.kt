package com.example.androidtokmp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.androidtokmp.util.Constants
import com.example.androidtokmp.util.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun dataStoreModule(): Module = module {
    single {
        createDataStore(androidContext())
    }
}

private fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = {
        context.filesDir.resolve(Constants.NUMBERS_DATA_STORE_FILE_NAME).absolutePath
    }
)
