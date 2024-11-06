package com.example.androidtokmp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApi {

    @GET("/{number}?json")
    suspend fun getNumberInfo(@Path("number") number: Int): NumberInfoResponse
}