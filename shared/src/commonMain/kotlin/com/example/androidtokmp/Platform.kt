package com.example.androidtokmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform