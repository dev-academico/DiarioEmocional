package com.app.diario.ui.avaliacao

import com.app.diario.domain.model.Avaliacao

sealed interface EstadoAvaliacaoTela {
    data object Carregando : EstadoAvaliacaoTela

    data object Vazio : EstadoAvaliacaoTela

    data class Sucesso(val avaliacoes: List<Avaliacao>) : EstadoAvaliacaoTela

    data class Erro(val mensagem: String) : EstadoAvaliacaoTela
}

data class AvaliacaoUiState(
    val tela: EstadoAvaliacaoTela = EstadoAvaliacaoTela.Carregando,
)
