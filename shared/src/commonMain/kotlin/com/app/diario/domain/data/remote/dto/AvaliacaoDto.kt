package com.app.diario.domain.data.remote.dto

import com.app.diario.domain.model.Avaliacao
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvaliacaoDto(
    @SerialName("id") val id: String? = null,
    @SerialName("data_registro") val dataRegistro: String,
    @SerialName("intensidade") val intensidade: Int,
    @SerialName("descricao") val descricao: String,
    @SerialName("gatilhos") val gatilhos: List<String> = emptyList(),
)

fun AvaliacaoDto.paraDominio(): Avaliacao {
    return Avaliacao(
        id = id ?: "",
        dataRegistro = dataRegistro,
        intensity = intensidade,
        descricao = descricao,
        gatilhos = gatilhos,
    )
}

fun Avaliacao.paraDto(): AvaliacaoDto =
    AvaliacaoDto(
        id = id,
        dataRegistro = dataRegistro,
        intensidade = intensity,
        descricao = descricao,
        gatilhos = gatilhos,
    )
