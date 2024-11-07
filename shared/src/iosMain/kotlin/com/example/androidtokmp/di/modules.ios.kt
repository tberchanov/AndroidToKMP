package com.example.androidtokmp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.androidtokmp.util.Constants
import org.koin.core.module.Module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import com.example.androidtokmp.util.createDataStore
import org.koin.dsl.module

actual fun dataStoreModule(): Module = module {
    single {
        createDataStore()
    }
}

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
fun createDataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/${Constants.NUMBERS_DATA_STORE_FILE_NAME}"
    }
)