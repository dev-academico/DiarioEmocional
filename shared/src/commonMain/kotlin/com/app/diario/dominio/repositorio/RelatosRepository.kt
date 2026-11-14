package com.app.diario.dominio.repositorio

import com.app.diario.ui.mock.RelatoMock

interface RelatosRepository {
    fun getRelatos(): List<RelatoMock>
    fun adicionarRelato(relato: RelatoMock)
}