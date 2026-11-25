package com.app.diario.domain.model.repository

import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.Relato

interface RelatoRepository {
    suspend fun buscarRelatos(): Listagem<Relato>

    suspend fun criarRelato(
        titulo: String,
        dataRegistro: String,
        conteudo: String,
    ): Relato
}
