package com.app.diario.dominio.repositorio

import com.app.diario.ui.mock.AvaliacaoMock

interface AvaliacaoRepository {
    fun getAvaliacoes(): List<AvaliacaoMock>
    fun adicionarAvaliacao(avaliacao: AvaliacaoMock)
}
