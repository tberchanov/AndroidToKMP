package com.example.androidtokmp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.androidtokmp.data.remote.NumbersRemoteDataSource
import com.example.androidtokmp.data.remote.createNumbersApi
import com.example.androidtokmp.data.remote.getApiClient
import com.example.androidtokmp.domain.NumbersRepository
import de.jensklingenberg.ktorfit.Ktorfit

class NumbersRepositoryImpl(
    private val localDataSource: NumbersLocalDataSource,
    private val remoteDataSource: NumbersRemoteDataSource,
) : NumbersRepository {

    override suspend fun getNumberInfo(number: Int): String? {
        val localNumberInfo = localDataSource.getNumberInfo(number)
        if (localNumberInfo != null) {
            return localNumberInfo
        }

        return remoteDataSource.getNumberInfo(number)
    }

    override suspend fun saveNumberInfo(number: Int, info: String) {
        localDataSource.saveNumberInfo(number, info)
    }

    override suspend fun getSavedNumber(): Int? {
        return localDataSource.getSavedNumber()
    }

    override suspend fun saveNumber(number: Int) {
        localDataSource.saveNumber(number)
    }

    companion object {
        // TODO use DI
        fun create(dataStore: DataStore<Preferences>): NumbersRepository {
            val localDataSource = NumbersLocalDataSource(dataStore)
            val remoteDataSource = NumbersRemoteDataSource(getApiClient("http://numbersapi.com/", Ktorfit::createNumbersApi))
            return NumbersRepositoryImpl(localDataSource, remoteDataSource)
        }
    }
}