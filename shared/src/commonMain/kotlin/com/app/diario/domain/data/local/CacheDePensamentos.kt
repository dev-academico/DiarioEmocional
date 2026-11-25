package com.app.diario.domain.data.local

import com.app.diario.domain.model.Pensamento

interface CacheDePensamentos {
    suspend fun salvar(pensamentos: List<Pensamento>)

    suspend fun ler(): List<Pensamento>?
}
