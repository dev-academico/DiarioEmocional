package com.app.diario.domain.data.remote.dto

import com.app.diario.domain.model.Pensamento
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PensamentoDto(
    @SerialName("id") val id: String? = null,
    @SerialName("data_registro") val dataRegistro: String,
    @SerialName("situacao") val situacao: String,
    @SerialName("pensamentos_imagens") val pensamentosImagens: String,
    @SerialName("emocoes_sentimentos") val emocoesSentimentos: String,
    @SerialName("comportamentos_reacoes") val comportamentosReacoes: String,
)

fun PensamentoDto.toDomain(): Pensamento {
    return Pensamento(
        id = id ?: "",
        dataRegistro = dataRegistro,
        situacao = situacao,
        pensamentosImagens = pensamentosImagens,
        emocoesSentimentos = emocoesSentimentos,
        comportamentosReacoes = comportamentosReacoes,
    )
}
