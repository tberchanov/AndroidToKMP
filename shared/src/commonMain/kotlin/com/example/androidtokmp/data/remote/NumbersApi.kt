package com.example.androidtokmp.data.remote

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface NumbersApi {

    @GET("{number}?json")
    suspend fun getNumberInfo(@Path("number") number: Int): NumberInfoResponse
}