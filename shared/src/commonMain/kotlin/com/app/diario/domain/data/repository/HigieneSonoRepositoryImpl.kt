package com.app.diario.domain.data.repository

import com.app.diario.domain.data.remote.ApiConfig
import com.app.diario.domain.data.remote.dto.CriarHigieneSonoDto
import com.app.diario.domain.data.remote.dto.HigieneSonoDto
import com.app.diario.domain.data.remote.dto.paraDominio
import com.app.diario.domain.model.DisposicaoAoAcordar
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.HigieneSono
import com.app.diario.domain.model.QualidadeSono
import com.app.diario.domain.model.repository.HigieneSonoRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.SerializationException

class HigieneSonoRepositoryImpl(
    private val client: HttpClient,
    private val baseUrl: String = ApiConfig.supabaseUrl,
) : HigieneSonoRepository {
    override suspend fun buscarHigieneSono(): List<HigieneSono> {
        try {
            return client
                .get("$baseUrl/rest/v1/higiene_sono?select=*")
                .body<List<HigieneSonoDto>>()
                .map { it.paraDominio() }
        } catch (e: ResponseException) {
            throw Falha.RespostaInvalida(e.response.status.value.toString())
        } catch (e: SerializationException) {
            throw Falha.RespostaInvalida(e.message.toString())
        } catch (_: Exception) {
            throw Falha.SemConexao()
        }
    }

    override suspend fun criarHigieneSono(
        dataRegistro: String,
        horarioDormiu: String,
        horarioAcordou: String,
        qualidade: QualidadeSono,
        disposicao: DisposicaoAoAcordar,
        usouCelularAntesDeDormir: Boolean,
        tevePesadelos: Boolean,
        comentarioSonhos: String,
    ): HigieneSono {
        try {
            return client
                .post("$baseUrl/rest/v1/higiene_sono") {
                    header("Prefer", "return=representation")
                    setBody(
                        CriarHigieneSonoDto(
                            dataRegistro = dataRegistro,
                            horarioDormiu = horarioDormiu,
                            horarioAcordou = horarioAcordou,
                            qualidade = qualidade.name,
                            disposicao = disposicao.name,
                            usouCelularAntesDeDormir = usouCelularAntesDeDormir,
                            tevePesadelos = tevePesadelos,
                            comentarioSonhos = comentarioSonhos,
                        ),
                    )
                }
                .body<List<HigieneSonoDto>>()
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
