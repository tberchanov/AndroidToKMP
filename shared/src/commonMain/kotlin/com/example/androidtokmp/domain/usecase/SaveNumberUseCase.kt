package com.example.androidtokmp.domain.usecase

import com.example.androidtokmp.domain.NumbersRepository

class SaveNumberUseCase(
    private val numbersRepository: NumbersRepository,
) {

    suspend fun execute(number: Int) {
        numbersRepository.saveNumber(number)
    }
}