package com.example.androidtokmp.data.remote

class NumbersRemoteDataSource(
    private val numbersApi: NumbersApi,
) {

    suspend fun getNumberInfo(number: Int): String {
        return numbersApi.getNumberInfo(number).text
    }
}