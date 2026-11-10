package com.app.diario.ui.avaliacao

import com.app.diario.ui.mock.AvaliacaoMock

sealed interface EstadoAvaliacaoTela {
    data object Carregando : EstadoAvaliacaoTela
    data object Vazio : EstadoAvaliacaoTela
    data class Sucesso(val avaliacoes: List<AvaliacaoMock>) : EstadoAvaliacaoTela
    data class Erro(val mensagem: String) : EstadoAvaliacaoTela
}

data class AvaliacaoUiState(
    val tela: EstadoAvaliacaoTela = EstadoAvaliacaoTela.Carregando,
)
