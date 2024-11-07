package com.example.androidtokmp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NumberInfoResponse(
    @SerialName("text")
    val text: String,
    @SerialName("number")
    val number: Int,
    @SerialName("found")
    val found: Boolean,
    @SerialName("type")
    val type: String,
)