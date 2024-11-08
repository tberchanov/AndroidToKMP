package com.example.androidtokmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtokmp.domain.LCE
import com.example.androidtokmp.domain.model.NumberInfo
import com.example.androidtokmp.domain.usecase.GetNumberInfoUseCase
import com.example.androidtokmp.domain.usecase.GetSavedNumberInfoUseCase
import com.example.androidtokmp.domain.usecase.SaveNumberUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NumbersInfoViewModel(
    private val getSavedNumberInfoUseCase: GetSavedNumberInfoUseCase,
    private val getNumberInfoUseCase: GetNumberInfoUseCase,
    private val saveNumberUseCase: SaveNumberUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<NumbersInfoState>(NumbersInfoState())
    val state: StateFlow<NumbersInfoState> = _state

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    fun loadNumberInfo() {
        viewModelScope.launch {
            getSavedNumberInfoUseCase.execute().collect {
                emitAsState(it)
            }
        }
    }

    fun generateNewNumber() {
        viewModelScope.launch {
            val currentNumberInfo = state.value.numberInfo
            if (currentNumberInfo != null) {
                val newNumber = currentNumberInfo.number + 1
                retrieveAndSaveNumberInfo(newNumber)
            }
        }
    }

    fun loadPreviousNumber() {
        viewModelScope.launch {
            val currentNumberInfo = state.value.numberInfo
            if (currentNumberInfo != null) {
                val previousNumber = currentNumberInfo.number - 1
                retrieveAndSaveNumberInfo(previousNumber)
            }
        }
    }

    private suspend fun retrieveAndSaveNumberInfo(number: Int) {
        getNumberInfoUseCase.execute(number)
            .onEach {
                if (it is LCE.Content) {
                    saveNumberUseCase.execute(it.data.number)
                }
            }
            .collect {
                emitAsState(it)
            }
    }

    private fun emitAsState(lce: LCE<NumberInfo>) {
        val newState = when (lce) {
            is LCE.Loading -> state.value.copy(
                loading = true,
                errorMessage = null,
            )

            is LCE.Content ->
                state.value.copy(
                    loading = false,
                    numberInfo = lce.data,
                    errorMessage = null,
                )

            is LCE.Error -> state.value.copy(
                loading = false,
                errorMessage = lce.message,
            )
        }
        if (newState.errorMessage != null) {
            viewModelScope.launch {
                _error.emit(newState.errorMessage)
            }
        } else {
            _state.value = newState
        }
    }
}