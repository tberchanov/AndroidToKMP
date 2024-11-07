package com.example.androidtokmp.domain.usecase

import com.example.androidtokmp.domain.LCE
import com.example.androidtokmp.domain.NumbersRepository
import com.example.androidtokmp.domain.model.NumberInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNumberInfoUseCase(
    private val numbersRepository: NumbersRepository,
) {

    suspend fun execute(number: Int): Flow<LCE<NumberInfo>> = flow {
        emit(LCE.Loading())
        delay(500) // Delay to imitate long running task.
        try {
            val numberInfo = numbersRepository.getNumberInfo(number)
            if (numberInfo != null) {
                numbersRepository.saveNumberInfo(number, numberInfo)
                emit(LCE.Content(NumberInfo(number, numberInfo)))
            } else {
                emit(LCE.Error("No info for number $number"))
            }
        } catch (e: Exception) {
            emit(LCE.Error(e.message ?: "Unknown error"))
        }
    }
}
