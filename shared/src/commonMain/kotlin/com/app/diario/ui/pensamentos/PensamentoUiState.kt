package com.app.diario.ui.pensamentos

import com.app.diario.domain.model.Pensamento

sealed interface EstadoTela {
    data object Carregando : EstadoTela

    data object Vazio : EstadoTela

    data class Sucesso(
        val pensamentos: List<Pensamento>,
        val doCache: Boolean = false,
    ) : EstadoTela

    data class Erro(val mensagem: String) : EstadoTela
}

data class PensamentoUiState(
    val tela: EstadoTela = EstadoTela.Carregando,
)
