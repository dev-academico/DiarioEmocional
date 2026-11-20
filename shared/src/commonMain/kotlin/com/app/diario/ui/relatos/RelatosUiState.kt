package com.app.diario.ui.relatos

import com.app.diario.domain.model.Relato

sealed interface EstadoTela {
    data object Carregando : EstadoTela
    data object Vazio : EstadoTela
    data class Sucesso(val relatos: List<Relato>) : EstadoTela
    data class ErroConexao(val mensagem: String = "Sem conexão com a internet. Verifique sua rede e tente novamente.") : EstadoTela
    data class ErroRespostaInvalida(val mensagem: String = "Resposta inesperada ou indisponível do servidor.") : EstadoTela
}

data class RelatosUiState(
    val tela: EstadoTela = EstadoTela.Carregando
)
