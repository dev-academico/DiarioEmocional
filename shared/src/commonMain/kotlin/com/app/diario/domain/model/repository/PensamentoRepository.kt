package com.app.diario.domain.model.repository

import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.Pensamento

interface PensamentoRepository {
    suspend fun buscarPensamento(): Listagem<Pensamento>

    suspend fun criarPensamento(
        dataRegistro: String,
        situacao: String,
        pensamentosImagens: String,
        emocoesSentimentos: String,
        comportamentosReacoes: String,
    ): Pensamento
}
