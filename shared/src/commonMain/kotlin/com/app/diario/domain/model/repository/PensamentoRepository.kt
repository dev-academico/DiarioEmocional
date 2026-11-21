package com.app.diario.domain.model.repository

import com.app.diario.domain.model.Pensamento
import com.app.diario.domain.model.Relato

interface PensamentoRepository {
    suspend fun buscarPensamento(): List<Pensamento>
    suspend fun criarPensamento(dataRegistro: String,
                            situacao: String,
                            pensamentosImagens: String,
                            emocoesSentimentos: String,
                            comportamentosReacoes: String): Pensamento
}
