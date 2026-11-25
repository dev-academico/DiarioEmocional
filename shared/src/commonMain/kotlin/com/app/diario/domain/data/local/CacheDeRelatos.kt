package com.app.diario.domain.data.local

import com.app.diario.domain.model.Relato

interface CacheDeRelatos {
    suspend fun salvar(relatos: List<Relato>)

    suspend fun ler(): List<Relato>?
}
