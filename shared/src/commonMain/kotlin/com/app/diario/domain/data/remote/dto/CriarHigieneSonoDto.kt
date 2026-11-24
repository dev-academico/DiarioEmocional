package com.app.diario.domain.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CriarHigieneSonoDto(
    @SerialName("data_registro") val dataRegistro: String,
    @SerialName("horario_dormiu") val horarioDormiu: String,
    @SerialName("horario_acordou") val horarioAcordou: String,
    @SerialName("qualidade") val qualidade: String,
    @SerialName("disposicao") val disposicao: String,
    @SerialName("usou_celular_antes_de_dormir") val usouCelularAntesDeDormir: Boolean,
    @SerialName("teve_pesadelos") val tevePesadelos: Boolean,
    @SerialName("comentario_sonhos") val comentarioSonhos: String,
)
