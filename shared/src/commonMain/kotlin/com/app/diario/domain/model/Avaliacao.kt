package com.app.diario.model

data class Avaliacao(
    val dataRegistro: String,
    val intensity: Number,
    val descricao: String,
    val gatilhos: List<String>,
)
