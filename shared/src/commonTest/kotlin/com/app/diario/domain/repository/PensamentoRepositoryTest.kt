package com.app.diario.domain.repository

import com.app.diario.domain.data.remote.createHttpClient
import com.app.diario.domain.data.repository.PensamentoRepositoryImpl
import com.app.diario.domain.model.Falha
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PensamentoRepositoryTest {
    @Test
    fun testeBuscarPensamentos() =
        runTest {
            // campo_desconhecido para testar o ignoreUnknownKeys = true
            val jsonMock =
                """
                [
                    {
                        "id":"1",
                        "data_registro":"2026-11-20",
                        "situacao":"Apresentação de trabalho da faculdade",
                        "pensamentos_imagens":"Vou esquecer tudo na hora e vão me achar despreparado",
                        "emocoes_sentimentos":"Ansiedade, medo e nervosismo",
                        "comportamentos_reacoes":"Fiquei roendo as unhas e revisando os slides repetidamente",
                        "created_at":"2026-11-20 15:59:36.547946+00"
                    }
                ]
                """.trimIndent()

            val mockEngine =
                MockEngine { request ->
                    assertEquals("/rest/v1/pensamentos", request.url.encodedPath)

                    respond(
                        content = jsonMock,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = PensamentoRepositoryImpl(client = client)

            val pensamentos = repository.buscarPensamento()

            assertEquals(1, pensamentos.size)
            val primeiro = pensamentos.first()

            assertEquals("1", primeiro.id)
            assertEquals("Apresentação de trabalho da faculdade", primeiro.situacao)
            assertEquals("2026-11-20", primeiro.dataRegistro)
            assertEquals("Vou esquecer tudo na hora e vão me achar despreparado", primeiro.pensamentosImagens)
        }

    @Test
    fun testeCriarRelato() =
        runTest {
            val jsonRespostaCriacao =
                """
                [
                    {
                        "id":"1",
                        "data_registro":"2026-11-20",
                        "situacao":"Apresentação de trabalho da faculdade",
                        "pensamentos_imagens":"Vou esquecer tudo na hora e vão me achar despreparado",
                        "emocoes_sentimentos":"Ansiedade, medo e nervosismo",
                        "comportamentos_reacoes":"Fiquei roendo as unhas e revisando os slides repetidamente",
                        "created_at":"2026-11-20 15:59:36.547946+00"
                    }
                ]
                """.trimIndent()

            val mockEngine =
                MockEngine { request ->
                    assertEquals("/rest/v1/pensamentos", request.url.encodedPath)
                    assertEquals("POST", request.method.value)

                    respond(
                        content = jsonRespostaCriacao,
                        status = HttpStatusCode.Created,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = PensamentoRepositoryImpl(client = client)

            val primeiro =
                repository.criarPensamento(
                    situacao = "Apresentação de trabalho da faculdade",
                    dataRegistro = "2026-11-20",
                    pensamentosImagens = "Vou esquecer tudo na hora e vão me achar despreparado",
                    emocoesSentimentos = "Ansiedade, medo e nervosismo",
                    comportamentosReacoes = "Fiquei roendo as unhas e revisando os slides repetidamente",
                )

            assertEquals("1", primeiro.id)
            assertEquals("Apresentação de trabalho da faculdade", primeiro.situacao)
            assertEquals("2026-11-20", primeiro.dataRegistro)
            assertEquals("Vou esquecer tudo na hora e vão me achar despreparado", primeiro.pensamentosImagens)
        }

    @Test
    fun testeBuscarPensamentos404ExpectedSucess() =
        runTest {
            val mockEngine =
                MockEngine {
                    respond(
                        content = """{"code":"PGRST205","message":"Tabela não encontrada"}""",
                        status = HttpStatusCode.NotFound, // 404
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = PensamentoRepositoryImpl(client = client)

            // testar expectSuccess = true do ktor
            val falha =
                assertFailsWith<Falha.RespostaInvalida> {
                    repository.buscarPensamento()
                }
        }

    @Test
    fun testeBuscarPelato500ExpectedSucess() =
        runTest {
            val mockEngine =
                MockEngine {
                    respond(
                        content = "Erro interno do servidor",
                        status = HttpStatusCode.InternalServerError, // 500
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = PensamentoRepositoryImpl(client = client)

            // testar expectSuccess = true do ktor
            val falha =
                assertFailsWith<Falha.RespostaInvalida> {
                    repository.buscarPensamento()
                }
        }

    @Test
    fun testeSemInternet() =
        runTest {
            val mockEngine =
                MockEngine {
                        _ ->
                    throw Exception("Sem conexão com a internet (UnknownHostException / ConnectException)")
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = PensamentoRepositoryImpl(client = client)

            assertFailsWith<Falha.SemConexao> {
                repository.buscarPensamento()
            }
        }
}
