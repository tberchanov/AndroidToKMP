package com.example.androidtokmp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "numbersDataStore")
private const val NUMBER_INFO_KEY = "NUMBER_INFO_KEY"
private const val SAVED_NUMBER_KEY = "SAVED_NUMBER_KEY"

class NumbersLocalDataSource(
    private val context: Context
) {

    suspend fun getSavedNumber(): Int? {
        return context.dataStore
            .data
            .firstOrNull()
            ?.get(intPreferencesKey(name = SAVED_NUMBER_KEY))
    }

    suspend fun saveNumber(number: Int) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(name = SAVED_NUMBER_KEY)] = number
        }
    }

    suspend fun getNumberInfo(number: Int): String? {
        return context.dataStore
            .data
            .firstOrNull()
            ?.get(stringPreferencesKey(name = NUMBER_INFO_KEY + number))
    }

    suspend fun saveNumberInfo(number: Int, info: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(name = NUMBER_INFO_KEY + number)] = info
        }
    }
}