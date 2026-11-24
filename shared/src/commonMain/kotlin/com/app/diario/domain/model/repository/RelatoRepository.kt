package com.app.diario.domain.model.repository

import com.app.diario.domain.model.Relato

interface RelatoRepository {
    suspend fun buscarRelatos(): List<Relato>

    suspend fun criarRelato(
        titulo: String,
        dataRegistro: String,
        conteudo: String,
    ): Relato
}
