package com.app.diario.domain.data.remote.dto

import com.app.diario.domain.model.DisposicaoAoAcordar
import com.app.diario.domain.model.HigieneSono
import com.app.diario.domain.model.QualidadeSono
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HigieneSonoDto(
    @SerialName("id") val id: String? = null,
    @SerialName("data_registro") val dataRegistro: String,
    @SerialName("horario_dormiu") val horarioDormiu: String,
    @SerialName("horario_acordou") val horarioAcordou: String,
    @SerialName("qualidade") val qualidade: String,
    @SerialName("disposicao") val disposicao: String,
    @SerialName("usou_celular_antes_de_dormir") val usouCelularAntesDeDormir: Boolean,
    @SerialName("teve_pesadelos") val tevePesadelos: Boolean,
    @SerialName("comentario_sonhos") val comentarioSonhos: String = ""
)

fun HigieneSonoDto.paraDominio(): HigieneSono {
    val qualidadeEnum = try {
        QualidadeSono.valueOf(qualidade.uppercase())
    } catch (_: Exception) {
        QualidadeSono.REGULAR
    }

    val disposicaoEnum = try {
        DisposicaoAoAcordar.valueOf(disposicao.uppercase())
    } catch (_: Exception) {
        DisposicaoAoAcordar.MEDIA
    }

    return HigieneSono(
        id = id ?: "",
        dataRegistro = dataRegistro,
        horarioDormiu = horarioDormiu,
        horarioAcordou = horarioAcordou,
        qualidade = qualidadeEnum,
        disposicao = disposicaoEnum,
        usouCelularAntesDeDormir = usouCelularAntesDeDormir,
        tevePesadelos = tevePesadelos,
        comentarioSonhos = comentarioSonhos
    )
}
