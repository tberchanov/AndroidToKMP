package com.example.androidtokmp.domain

interface NumbersRepository {

    suspend fun getNumberInfo(number: Int): String?

    suspend fun saveNumberInfo(number: Int, info: String)

    suspend fun getSavedNumber(): Int?

    suspend fun saveNumber(number: Int)
}