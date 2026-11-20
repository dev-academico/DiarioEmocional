package com.app.diario.domain.data.repository

import com.app.diario.domain.model.Relato
import com.app.diario.domain.data.remote.ApiConfig
import com.app.diario.domain.data.remote.dto.CriarRelatoDto
import com.app.diario.domain.data.remote.dto.RelatoDto
import com.app.diario.domain.data.remote.dto.toDomain
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.repository.RelatoRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.SerializationException

class RelatoRepositoryImpl(
    private val client: HttpClient,
    private val baseUrl: String = ApiConfig.supabaseUrl
) : RelatoRepository {
    override suspend fun buscarRelatos(): List<Relato> {
        return try {
            val response = client.get("$baseUrl/rest/v1/relatos?select=*")
            val dtos: List<RelatoDto> = response.body()

            dtos.map { it.toDomain() }

        } catch (e: ResponseException) {
            throw Falha.RespostaInvalida(e.response.status.value)
        } catch (e: SerializationException) {
            throw Falha.RespostaInvalida()
        } catch (e: Exception) {
            throw Falha.SemConexao
        }
    }
    override suspend fun criarRelato(titulo: String, dataRegistro: String, conteudo: String): Relato {
        try {
            val dto = CriarRelatoDto(
                titulo = titulo,
                dataRegistro = dataRegistro,
                conteudo = conteudo
            )
            val response = client.post("$baseUrl/rest/v1/relatos") {
                header("Prefer", "return=representation")
                setBody(dto)
            }
            val resposta: List<RelatoDto> = response.body()

            return resposta.first().toDomain()

        } catch (e: ResponseException) {
            throw Falha.RespostaInvalida(e.response.status.value)
        } catch (e: SerializationException) {
            throw Falha.RespostaInvalida()
        } catch (e: Exception) {
            throw Falha.SemConexao
        }
    }

}
