package com.example.androidtokmp.presentation

import com.example.androidtokmp.domain.model.NumberInfo

sealed class NumbersInfoState {
    data object Initial : NumbersInfoState()
    data object Loading : NumbersInfoState()
    data class Success(val numberInfo: NumberInfo) : NumbersInfoState()
    data class Error(val message: String) : NumbersInfoState()
}