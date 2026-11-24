package com.app.diario.domain.model

data class Pensamento(
    val id: String,
    val dataRegistro: String,
    val situacao: String,
    val pensamentosImagens: String,
    val emocoesSentimentos: String,
    val comportamentosReacoes: String,
)
