package com.example.androidtokmp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull

private const val NUMBER_INFO_KEY = "NUMBER_INFO_KEY"
private const val SAVED_NUMBER_KEY = "SAVED_NUMBER_KEY"

class NumbersLocalDataSource(
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun getSavedNumber(): Int? {
        return dataStore
            .data
            .firstOrNull()
            ?.get(intPreferencesKey(name = SAVED_NUMBER_KEY))
    }

    suspend fun saveNumber(number: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(name = SAVED_NUMBER_KEY)] = number
        }
    }

    suspend fun getNumberInfo(number: Int): String? {
        return dataStore
            .data
            .firstOrNull()
            ?.get(stringPreferencesKey(name = NUMBER_INFO_KEY + number))
    }

    suspend fun saveNumberInfo(number: Int, info: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(name = NUMBER_INFO_KEY + number)] = info
        }
    }
}