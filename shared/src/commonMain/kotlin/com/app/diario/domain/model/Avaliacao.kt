package com.app.diario.domain.model

data class Avaliacao(
    val id: String,
    val dataRegistro: String,
    val intensity: Int,
    val descricao: String,
    val gatilhos: List<String>,
)
