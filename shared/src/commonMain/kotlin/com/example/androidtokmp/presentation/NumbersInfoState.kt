package com.example.androidtokmp.presentation

import com.example.androidtokmp.domain.model.NumberInfo

data class NumbersInfoState(
    val loading: Boolean = false,
    val numberInfo: NumberInfo? = null,
    val errorMessage: String? = null,
)