package com.app.diario.ui.relatos

import com.app.diario.domain.model.Relato

sealed interface EstadoTela {
    data object Carregando : EstadoTela

    data object Vazio : EstadoTela

    data class Sucesso(
        val relatos: List<Relato>,
        val doCache: Boolean = false,
    ) : EstadoTela

    data class Erro(val mensagem: String) : EstadoTela
}

data class RelatosUiState(
    val tela: EstadoTela = EstadoTela.Carregando,
)
