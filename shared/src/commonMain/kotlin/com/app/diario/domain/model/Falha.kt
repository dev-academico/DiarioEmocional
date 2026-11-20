package com.app.diario.domain.model

sealed class Falha(mensagem: String) : Exception(mensagem) {
    data object SemConexao : Falha("Sem conexão com a internet. Verifique sua rede.")
    data class RespostaInvalida(val codigoHttp: Int? = null) :
        Falha("Resposta inválida do servidor${codigoHttp?.let { " (código $it)" } ?: ""}.")
}
