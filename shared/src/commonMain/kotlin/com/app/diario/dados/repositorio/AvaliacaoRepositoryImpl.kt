package com.app.diario.dados.repositorio

import com.app.diario.dominio.repositorio.AvaliacaoRepository
import com.app.diario.ui.mock.AvaliacaoMock
import com.app.diario.ui.mock.avaliacaoMock

class AvaliacaoRepositoryImpl : AvaliacaoRepository {
    private val listaAvaliacoes = avaliacaoMock.toMutableList()

    override fun getAvaliacoes(): List<AvaliacaoMock> {
        return listaAvaliacoes
    }

    override fun adicionarAvaliacao(avaliacao: AvaliacaoMock) {
        listaAvaliacoes.add(0, avaliacao)
    }
}
