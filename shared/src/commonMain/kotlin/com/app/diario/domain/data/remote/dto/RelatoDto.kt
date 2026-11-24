package com.app.diario.domain.data.remote.dto

import com.app.diario.domain.model.Relato
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelatoDto(
    @SerialName("id") val id: String? = null,
    @SerialName("titulo") val titulo: String,
    @SerialName("data_registro") val dataRegistro: String,
    @SerialName("conteudo") val conteudo: String,
)

fun RelatoDto.paraDominio(): Relato {
    return Relato(
        id = id ?: "",
        titulo = titulo,
        dataRegistro = dataRegistro,
        conteudo = conteudo,
    )
}
