package com.example.androidtokmp.data.remote

import com.google.gson.annotations.SerializedName

data class NumberInfoResponse(
    @SerializedName("text")
    val text: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("found")
    val found: Boolean,
    @SerializedName("type")
    val type: String,
)