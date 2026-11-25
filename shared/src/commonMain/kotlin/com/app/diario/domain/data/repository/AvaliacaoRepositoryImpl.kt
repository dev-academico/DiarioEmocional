package com.app.diario.domain.data.repository

import com.app.diario.domain.data.remote.ApiConfig
import com.app.diario.domain.data.remote.dto.AvaliacaoDto
import com.app.diario.domain.data.remote.dto.CriarAvaliacaoDto
import com.app.diario.domain.data.remote.dto.paraDominio
import com.app.diario.domain.model.Avaliacao
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.repository.AvaliacaoRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.SerializationException

class AvaliacaoRepositoryImpl(
    private val client: HttpClient,
    private val baseUrl: String = ApiConfig.supabaseUrl,
) : AvaliacaoRepository {
    override suspend fun buscarAvaliacoes(): Listagem<Avaliacao> {
        try {
            val avaliacoes =
                client
                    .get("$baseUrl/rest/v1/avaliacoes?select=*")
                    .body<List<AvaliacaoDto>>()
                    .map { it.paraDominio() }
            return Listagem(avaliacoes, doCache = false)
        } catch (e: ResponseException) {
            throw Falha.RespostaInvalida(e.response.status.value.toString())
        } catch (e: SerializationException) {
            throw Falha.RespostaInvalida(e.message.toString())
        } catch (_: Exception) {
            throw Falha.SemConexao()
        }
    }

    override suspend fun criarAvaliacao(
        dataRegistro: String,
        intensidade: Int,
        descricao: String,
        gatilhos: List<String>,
    ): Avaliacao {
        try {
            return client
                .post("$baseUrl/rest/v1/avaliacoes") {
                    header("Prefer", "return=representation")
                    setBody(
                        CriarAvaliacaoDto(
                            dataRegistro = dataRegistro,
                            intensidade = intensidade,
                            descricao = descricao,
                            gatilhos = gatilhos,
                        ),
                    )
                }
                .body<List<AvaliacaoDto>>()
                .first()
                .paraDominio()
        } catch (e: ResponseException) {
            throw Falha.RespostaInvalida(e.response.status.value.toString())
        } catch (e: SerializationException) {
            throw Falha.RespostaInvalida(e.message.toString())
        } catch (_: Exception) {
            throw Falha.SemConexao()
        }
    }
}
