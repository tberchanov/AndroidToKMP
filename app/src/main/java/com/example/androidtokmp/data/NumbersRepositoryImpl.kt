package com.example.androidtokmp.data

import com.example.androidtokmp.data.remote.NumbersRemoteDataSource
import com.example.androidtokmp.domain.NumbersRepository

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
}