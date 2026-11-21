package com.app.diario.domain.model

sealed class Falha(mensagem: String) : Exception(mensagem) {
    class SemConexao : Falha("sem conexão com o servidor")
    class RespostaInvalida(detalhe: String) : Falha("resposta inválida: $detalhe")
}
