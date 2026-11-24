package com.app.diario.domain.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CriarAvaliacaoDto(
    @SerialName("data_registro") val dataRegistro: String,
    @SerialName("intensidade") val intensidade: Int,
    @SerialName("descricao") val descricao: String,
    @SerialName("gatilhos") val gatilhos: List<String>,
)
