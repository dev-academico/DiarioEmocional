package com.app.diario.ui.relatos

import com.app.diario.ui.mock.RelatoMock

sealed interface EstadoTela {
    data object Carregando : EstadoTela
    data object Vazio : EstadoTela
    data class Sucesso(val relatos: List<RelatoMock>) : EstadoTela
    data class Erro(val mensagem: String) : EstadoTela
}

data class RelatosUiState(
    val tela: EstadoTela = EstadoTela.Carregando
)