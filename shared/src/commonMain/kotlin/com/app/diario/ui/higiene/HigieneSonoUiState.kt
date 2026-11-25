package com.app.diario.ui.higiene

import com.app.diario.domain.model.HigieneSono

sealed interface EstadoSonoTela {
    data object Carregando : EstadoSonoTela

    data object Vazio : EstadoSonoTela

    data class Sucesso(
        val registros: List<HigieneSono>,
        val doCache: Boolean = false,
    ) : EstadoSonoTela

    data class Erro(val mensagem: String) : EstadoSonoTela
}

data class HigieneSonoUiState(
    val tela: EstadoSonoTela = EstadoSonoTela.Carregando,
)
