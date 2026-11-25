package com.app.diario.domain.data.local

import com.app.diario.domain.model.Avaliacao

interface CacheDeAvaliacoes {
    suspend fun salvar(avaliacoes: List<Avaliacao>)

    suspend fun ler(): List<Avaliacao>?
}
