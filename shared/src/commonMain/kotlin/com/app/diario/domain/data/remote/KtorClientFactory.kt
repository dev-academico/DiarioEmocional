package com.app.diario.domain.remote

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(
    engine: HttpClientEngine? = null,
    baseUrl: String = ApiConfig.supabaseUrl,
    apiKey: String = ApiConfig.supabaseAnonKey
): HttpClient {
    val block: HttpClientConfig<*>.() -> Unit = {
        // Exigência: expectSuccess = true (faz o Ktor lançar exceção em status 4xx/5xx)
        expectSuccess = true

        // Exigência: ignoreUnknownKeys = true na negociação JSON
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
            )
        }

        // Headers padrão do Supabase PostgREST
        defaultRequest {
            header("apikey", apiKey)
            header("Authorization", "Bearer $apiKey")
            contentType(ContentType.Application.Json)
        }
    }

    return if (engine != null) {
        HttpClient(engine) { block() }
    } else {
        HttpClient { block() }
    }
}
