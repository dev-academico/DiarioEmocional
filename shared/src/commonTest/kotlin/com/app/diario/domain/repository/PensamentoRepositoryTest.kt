package com.app.diario.domain.repository

import com.app.diario.domain.data.local.CacheDePensamentos
import com.app.diario.domain.data.local.PensamentoRepositoryComCache
import com.app.diario.domain.data.remote.createHttpClient
import com.app.diario.domain.data.repository.PensamentoRepositoryImpl
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.Pensamento
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
    private fun cacheEmMemoria(inicial: List<Pensamento>? = null) =
        object : CacheDePensamentos {
            var dados: List<Pensamento>? = inicial

            override suspend fun salvar(pensamentos: List<Pensamento>) {
                dados = pensamentos
            }

            override suspend fun ler(): List<Pensamento>? = dados
        }

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

            val listagem = repository.buscarPensamento()

            assertEquals(false, listagem.doCache)
            assertEquals(1, listagem.itens.size)
            val primeiro = listagem.itens.first()

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

    @Test
    fun testeComConexaoGuardaNoCache() =
        runTest {
            val jsonMock =
                """[{"id":"1","data_registro":"2026-11-20","situacao":"Prova","pensamentos_imagens":"Vou errar","emocoes_sentimentos":"Ansiedade","comportamentos_reacoes":"Estudei mais"}]"""
            val mockEngine =
                MockEngine {
                    respond(
                        content = jsonMock,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }
            val cache = cacheEmMemoria()
            val repository = PensamentoRepositoryComCache(PensamentoRepositoryImpl(createHttpClient(mockEngine)), cache)

            val listagem = repository.buscarPensamento()

            assertEquals(false, listagem.doCache)
            assertEquals(1, listagem.itens.size)
            assertEquals(listagem.itens, cache.ler())
        }

    @Test
    fun testeSemConexaoDevolveDoCache() =
        runTest {
            val salvos =
                listOf(
                    Pensamento("1", "2026-11-20", "Prova", "Vou errar", "Ansiedade", "Estudei mais"),
                )
            val mockEngine = MockEngine { throw Exception("Sem internet") }
            val cache = cacheEmMemoria(inicial = salvos)
            val repository = PensamentoRepositoryComCache(PensamentoRepositoryImpl(createHttpClient(mockEngine)), cache)

            val listagem = repository.buscarPensamento()

            assertEquals(true, listagem.doCache)
            assertEquals(salvos, listagem.itens)
        }

    @Test
    fun testeSemConexaoESemCacheLancaErro() =
        runTest {
            val mockEngine = MockEngine { throw Exception("Sem internet") }
            val cache = cacheEmMemoria(inicial = null)
            val repository = PensamentoRepositoryComCache(PensamentoRepositoryImpl(createHttpClient(mockEngine)), cache)

            assertFailsWith<Falha.SemConexao> {
                repository.buscarPensamento()
            }
        }
}
