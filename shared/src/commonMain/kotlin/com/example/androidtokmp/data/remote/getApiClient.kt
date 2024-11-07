package com.example.androidtokmp.data.remote

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

inline fun <T> getApiClient(baseUrl: String, createApi: Ktorfit.() -> T): T {
    val ktorfit = ktorfit {
        baseUrl(baseUrl)
        httpClient(HttpClient {

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        })
    }
    return ktorfit.createApi()
}
