package com.app.diario.ui.pensamentos

import com.app.diario.ui.mock.PensamentoMock

sealed interface EstadoPensamentoTela {
    data object Carregando : EstadoPensamentoTela
    data object Vazio : EstadoPensamentoTela
    data class Sucesso(val pensamentos: List<PensamentoMock>) : EstadoPensamentoTela
    data class Erro(val mensagem: String) : EstadoPensamentoTela
}

data class PensamentoUiState(
    val tela: EstadoPensamentoTela = EstadoPensamentoTela.Carregando,
)
