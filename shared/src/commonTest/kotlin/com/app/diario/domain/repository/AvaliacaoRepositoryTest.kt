package com.app.diario.domain.repository

import com.app.diario.domain.data.local.AvaliacaoRepositoryComCache
import com.app.diario.domain.data.local.CacheDeAvaliacoes
import com.app.diario.domain.data.remote.createHttpClient
import com.app.diario.domain.data.repository.AvaliacaoRepositoryImpl
import com.app.diario.domain.model.Avaliacao
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
import kotlin.test.assertNotNull

class AvaliacaoRepositoryTest {
    private fun cacheEmMemoria(inicial: List<Avaliacao>? = null) =
        object : CacheDeAvaliacoes {
            var dados: List<Avaliacao>? = inicial

            override suspend fun salvar(avaliacoes: List<Avaliacao>) {
                dados = avaliacoes
            }

            override suspend fun ler(): List<Avaliacao>? = dados
        }

    @Test
    fun testeBuscarAvaliacoes() =
        runTest {
            // campo_desconhecido para testar o ignoreUnknownKeys = true
            val jsonMock =
                """
                [
                    {
                        "id": "1",
                        "data_registro": "2026-11-24",
                        "intensidade": 7,
                        "descricao": "Dia produtivo com reuniões",
                        "gatilhos": ["Trabalho", "Prazo apertado"],
                        "campo_desconhecido": "deve_ser_ignorado"
                    }
                ]
                """.trimIndent()

            val mockEngine =
                MockEngine { request ->
                    assertEquals("/rest/v1/avaliacoes", request.url.encodedPath)

                    respond(
                        content = jsonMock,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = AvaliacaoRepositoryImpl(client = client)

            val listagem = repository.buscarAvaliacoes()

            assertEquals(false, listagem.doCache)
            assertEquals(1, listagem.itens.size)
            val primeira = listagem.itens.first()
            assertEquals("1", primeira.id)
            assertEquals("2026-11-24", primeira.dataRegistro)
            assertEquals(7, primeira.intensity)
            assertEquals("Dia produtivo com reuniões", primeira.descricao)
            assertEquals(listOf("Trabalho", "Prazo apertado"), primeira.gatilhos)
        }

    @Test
    fun testeCriarAvaliacao() =
        runTest {
            val jsonRespostaCriacao =
                """
                [
                    {
                        "id": "1",
                        "data_registro": "2026-11-24",
                        "intensidade": 8,
                        "descricao": "Fiquei ansioso antes da apresentação",
                        "gatilhos": ["Trabalho"]
                    }
                ]
                """.trimIndent()

            val mockEngine =
                MockEngine { request ->
                    assertEquals("/rest/v1/avaliacoes", request.url.encodedPath)
                    assertEquals("POST", request.method.value)

                    respond(
                        content = jsonRespostaCriacao,
                        status = HttpStatusCode.Created,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = AvaliacaoRepositoryImpl(client = client)

            val avaliacaoCriada =
                repository.criarAvaliacao(
                    dataRegistro = "2026-11-24",
                    intensidade = 8,
                    descricao = "Fiquei ansioso antes da apresentação",
                    gatilhos = listOf("Trabalho"),
                )

            assertNotNull(avaliacaoCriada)
            assertEquals("1", avaliacaoCriada.id)
            assertEquals("2026-11-24", avaliacaoCriada.dataRegistro)
            assertEquals(8, avaliacaoCriada.intensity)
            assertEquals("Fiquei ansioso antes da apresentação", avaliacaoCriada.descricao)
            assertEquals(listOf("Trabalho"), avaliacaoCriada.gatilhos)
        }

    @Test
    fun testeBuscarAvaliacao404ExpectedSuccess() =
        runTest {
            val mockEngine =
                MockEngine {
                    respond(
                        content = """{"code":"PGRST205","message":"Tabela não encontrada"}""",
                        status = HttpStatusCode.NotFound,
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = AvaliacaoRepositoryImpl(client = client)

            assertFailsWith<Falha.RespostaInvalida> {
                repository.buscarAvaliacoes()
            }
        }

    @Test
    fun testeBuscarAvaliacao500ExpectedSuccess() =
        runTest {
            val mockEngine =
                MockEngine {
                    respond(
                        content = "Erro interno do servidor",
                        status = HttpStatusCode.InternalServerError,
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = AvaliacaoRepositoryImpl(client = client)

            assertFailsWith<Falha.RespostaInvalida> {
                repository.buscarAvaliacoes()
            }
        }

    @Test
    fun testeSemInternet() =
        runTest {
            val mockEngine =
                MockEngine {
                    throw Exception("Sem conexão com a internet (UnknownHostException / ConnectException)")
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = AvaliacaoRepositoryImpl(client = client)

            assertFailsWith<Falha.SemConexao> {
                repository.buscarAvaliacoes()
            }
        }

    @Test
    fun testeComConexaoGuardaNoCache() =
        runTest {
            val jsonMock =
                """[{"id":"1","data_registro":"2026-11-24","intensidade":7,"descricao":"Dia calmo","gatilhos":["Trabalho"]}]"""
            val mockEngine =
                MockEngine {
                    respond(
                        content = jsonMock,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }
            val cache = cacheEmMemoria()
            val repository = AvaliacaoRepositoryComCache(AvaliacaoRepositoryImpl(createHttpClient(mockEngine)), cache)

            val listagem = repository.buscarAvaliacoes()

            assertEquals(false, listagem.doCache)
            assertEquals(1, listagem.itens.size)
            assertEquals(listagem.itens, cache.ler())
        }

    @Test
    fun testeSemConexaoDevolveDoCache() =
        runTest {
            val avaliacoesSalvas = listOf(Avaliacao("1", "2026-11-24", 7, "Dia calmo", listOf("Trabalho")))
            val mockEngine = MockEngine { throw Exception("Sem internet") }
            val cache = cacheEmMemoria(inicial = avaliacoesSalvas)
            val repository = AvaliacaoRepositoryComCache(AvaliacaoRepositoryImpl(createHttpClient(mockEngine)), cache)

            val listagem = repository.buscarAvaliacoes()

            assertEquals(true, listagem.doCache)
            assertEquals(avaliacoesSalvas, listagem.itens)
        }

    @Test
    fun testeSemConexaoESemCacheLancaErro() =
        runTest {
            val mockEngine = MockEngine { throw Exception("Sem internet") }
            val cache = cacheEmMemoria(inicial = null)
            val repository = AvaliacaoRepositoryComCache(AvaliacaoRepositoryImpl(createHttpClient(mockEngine)), cache)

            assertFailsWith<Falha.SemConexao> {
                repository.buscarAvaliacoes()
            }
        }
}
