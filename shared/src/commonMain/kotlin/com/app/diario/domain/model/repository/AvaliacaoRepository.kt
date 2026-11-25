package com.app.diario.domain.model.repository

import com.app.diario.domain.model.Avaliacao
import com.app.diario.domain.model.Listagem

interface AvaliacaoRepository {
    suspend fun buscarAvaliacoes(): Listagem<Avaliacao>

    suspend fun criarAvaliacao(
        dataRegistro: String,
        intensidade: Int,
        descricao: String,
        gatilhos: List<String>,
    ): Avaliacao
}
