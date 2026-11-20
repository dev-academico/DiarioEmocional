package com.app.diario.model

data class RegistroSono(
    val dataRegistro: String, // "2026-10-15"
    val horarioDormiu: String, // "HH:mm"
    val horarioAcordou: String, // "HH:mm"
    val qualidade: QualidadeSono,
    val disposicao: DisposicaoAoAcordar,
    val usouCelularAntesDeDormir: Boolean,
    val tevePesadelos: Boolean,
    val comentarioSonhos: String,
)

enum class QualidadeSono(val rotulo: String) {
    PESSIMA("Péssima"),
    RUIM("Ruim"),
    REGULAR("Regular"),
    BOA("Boa"),
    OTIMA("Ótima"),
}

enum class DisposicaoAoAcordar(val rotulo: String) {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta"),
}


