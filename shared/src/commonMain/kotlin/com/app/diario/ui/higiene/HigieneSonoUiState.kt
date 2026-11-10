package com.app.diario.ui.higiene

import com.app.diario.ui.mock.RegistroSonoMock

sealed interface EstadoSonoTela {
    data object Carregando : EstadoSonoTela
    data object Vazio : EstadoSonoTela
    data class Sucesso(val registros: List<RegistroSonoMock>) : EstadoSonoTela
    data class Erro(val mensagem: String) : EstadoSonoTela
}

data class HigieneSonoUiState(
    val tela: EstadoSonoTela = EstadoSonoTela.Carregando,
)
