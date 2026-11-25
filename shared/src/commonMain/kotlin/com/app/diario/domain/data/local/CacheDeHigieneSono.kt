package com.app.diario.domain.data.local

import com.app.diario.domain.model.HigieneSono

interface CacheDeHigieneSono {
    suspend fun salvar(registros: List<HigieneSono>)

    suspend fun ler(): List<HigieneSono>?
}
