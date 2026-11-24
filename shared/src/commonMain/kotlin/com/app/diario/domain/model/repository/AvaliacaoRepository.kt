package com.app.diario.domain.model.repository

import com.app.diario.domain.model.Avaliacao

interface AvaliacaoRepository {
    suspend fun buscarAvaliacoes(): List<Avaliacao>
    suspend fun criarAvaliacao(
        dataRegistro: String,
        intensidade: Int,
        descricao: String,
        gatilhos: List<String>
    ): Avaliacao
}
