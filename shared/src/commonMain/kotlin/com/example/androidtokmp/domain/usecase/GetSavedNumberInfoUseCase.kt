package com.example.androidtokmp.domain.usecase

import com.example.androidtokmp.domain.LCE
import com.example.androidtokmp.domain.NumbersRepository
import com.example.androidtokmp.domain.model.NumberInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSavedNumberInfoUseCase(
    private val numbersRepository: NumbersRepository,
) {

    suspend fun execute(): Flow<LCE<NumberInfo>> = flow {
        emit(LCE.Loading())
        delay(500) // Delay to imitate long running task.
        try {
            val savedNumber = numbersRepository.getSavedNumber() ?: 0
            val numberInfo = numbersRepository.getNumberInfo(savedNumber)
            if (numberInfo != null) {
                emit(LCE.Content(NumberInfo(savedNumber, numberInfo)))
            } else {
                emit(LCE.Error("No info for number $savedNumber"))
            }
        } catch (e: Exception) {
            emit(LCE.Error(e.message ?: "Unknown error"))
        }
    }
}