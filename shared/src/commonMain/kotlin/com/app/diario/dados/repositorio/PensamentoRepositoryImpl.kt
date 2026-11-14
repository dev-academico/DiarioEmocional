package com.app.diario.dados.repositorio

import com.app.diario.dominio.repositorio.PensamentoRepository
import com.app.diario.ui.mock.PensamentoMock
import com.app.diario.ui.mock.pensamentosDisfuncionaisMock

class PensamentoRepositoryImpl : PensamentoRepository {
    private val listaPensamentos = pensamentosDisfuncionaisMock.toMutableList()

    override fun getPensamentos(): List<PensamentoMock> {
        return listaPensamentos
    }

    override fun adicionarPensamento(pensamento: PensamentoMock) {
        listaPensamentos.add(0, pensamento)
    }
}
