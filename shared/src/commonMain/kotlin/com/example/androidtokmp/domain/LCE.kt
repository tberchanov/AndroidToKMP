package com.example.androidtokmp.domain

sealed class LCE<T> {
    class Loading<T> : LCE<T>()
    data class Content<T>(val data: T) : LCE<T>()
    data class Error<T>(val message: String) : LCE<T>()
}