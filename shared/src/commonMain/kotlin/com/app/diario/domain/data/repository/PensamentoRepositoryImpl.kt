package com.app.diario.domain.data.repository

import com.app.diario.domain.data.remote.ApiConfig
import com.app.diario.domain.data.remote.dto.CriarPensamentoDto
import com.app.diario.domain.data.remote.dto.PensamentoDto
import com.app.diario.domain.data.remote.dto.toDomain
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.Pensamento
import com.app.diario.domain.model.repository.PensamentoRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.SerializationException

class PensamentoRepositoryImpl(
    private val client: HttpClient,
    private val baseUrl: String = ApiConfig.supabaseUrl,
) : PensamentoRepository {
    override suspend fun buscarPensamento(): Listagem<Pensamento> {
        return try {
            val response = client.get("$baseUrl/rest/v1/pensamentos?select=*")
            val dtos: List<PensamentoDto> = response.body()

            Listagem(dtos.map { it.toDomain() }, doCache = false)
        } catch (e: ResponseException) {
            throw Falha.RespostaInvalida(e.response.status.value.toString())
        } catch (e: SerializationException) {
            throw Falha.RespostaInvalida(e.message.toString())
        } catch (e: Exception) {
            throw Falha.SemConexao()
        }
    }

    override suspend fun criarPensamento(
        dataRegistro: String,
        situacao: String,
        pensamentosImagens: String,
        emocoesSentimentos: String,
        comportamentosReacoes: String,
    ): Pensamento {
        try {
            val dto =
                CriarPensamentoDto(
                    null,
                    dataRegistro,
                    situacao,
                    pensamentosImagens,
                    emocoesSentimentos,
                    comportamentosReacoes,
                )
            val response =
                client.post("$baseUrl/rest/v1/pensamentos") {
                    header("Prefer", "return=representation")
                    setBody(dto)
                }
            val resposta: List<PensamentoDto> = response.body()

            return resposta.first().toDomain()
        } catch (e: ResponseException) {
            throw Falha.RespostaInvalida(e.response.status.value.toString())
        } catch (e: SerializationException) {
            throw Falha.RespostaInvalida(e.message.toString())
        } catch (e: Exception) {
            throw Falha.SemConexao()
        }
    }
}
