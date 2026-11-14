package com.app.diario.dados.repositorio

import com.app.diario.dominio.repositorio.RelatosRepository
import com.app.diario.ui.mock.RelatoMock
import com.app.diario.ui.mock.relatosMock

class RelatosRepositoryImpl : RelatosRepository {
    private val listaRelatos = relatosMock.toMutableList()

    override fun getRelatos(): List<RelatoMock> {
        return listaRelatos
    }

    override fun adicionarRelato(relato: RelatoMock) {
        listaRelatos.add(0, relato)
    }
}