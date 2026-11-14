package com.app.diario.dominio.repositorio

import com.app.diario.ui.mock.PensamentoMock

interface PensamentoRepository {
    fun getPensamentos(): List<PensamentoMock>
    fun adicionarPensamento(pensamento: PensamentoMock)
}
