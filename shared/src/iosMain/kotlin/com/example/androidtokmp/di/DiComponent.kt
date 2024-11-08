package com.example.androidtokmp.di

import com.example.androidtokmp.presentation.NumbersInfoViewModel
import org.koin.core.component.KoinComponent

class DiComponent : KoinComponent {

    fun numbersInfoViewModel(): NumbersInfoViewModel = getKoin().get()
}