package com.app.diario.domain.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CriarRelatoDto(
    @SerialName("titulo") val titulo: String,
    @SerialName("data_registro") val dataRegistro: String,
    @SerialName("conteudo") val conteudo: String,
)
