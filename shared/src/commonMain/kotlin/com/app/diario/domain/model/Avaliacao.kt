package com.app.diario.domain.model

data class Avaliacao(
    val dataRegistro: String,
    val intensity: Number,
    val descricao: String,
    val gatilhos: List<String>,
)
