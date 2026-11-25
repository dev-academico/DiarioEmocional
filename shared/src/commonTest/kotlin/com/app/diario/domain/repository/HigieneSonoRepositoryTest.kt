package com.app.diario.domain.repository

import com.app.diario.domain.data.local.CacheDeHigieneSono
import com.app.diario.domain.data.local.HigieneSonoRepositoryComCache
import com.app.diario.domain.data.remote.createHttpClient
import com.app.diario.domain.data.repository.HigieneSonoRepositoryImpl
import com.app.diario.domain.model.DisposicaoAoAcordar
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.HigieneSono
import com.app.diario.domain.model.QualidadeSono
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

class HigieneSonoRepositoryTest {
    private fun cacheEmMemoria(inicial: List<HigieneSono>? = null) =
        object : CacheDeHigieneSono {
            var dados: List<HigieneSono>? = inicial

            override suspend fun salvar(registros: List<HigieneSono>) {
                dados = registros
            }

            override suspend fun ler(): List<HigieneSono>? = dados
        }

    @Test
    fun testeBuscarHigieneSono() =
        runTest {
            // campo_desconhecido para testar ignoreUnknownKeys = true
            val jsonMock =
                """
                [
                    {
                        "id": "1",
                        "data_registro": "2026-10-15",
                        "horario_dormiu": "23:30",
                        "horario_acordou": "06:45",
                        "qualidade": "BOA",
                        "disposicao": "ALTA",
                        "usou_celular_antes_de_dormir": false,
                        "teve_pesadelos": false,
                        "comentario_sonhos": "Dormi muito bem.",
                        "campo_desconhecido": "deve_ignorar"
                    }
                ]
                """.trimIndent()

            val mockEngine =
                MockEngine { request ->
                    assertEquals("/rest/v1/higiene_sono", request.url.encodedPath)

                    respond(
                        content = jsonMock,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = HigieneSonoRepositoryImpl(client = client)

            val listagem = repository.buscarHigieneSono()

            assertEquals(false, listagem.doCache)
            assertEquals(1, listagem.itens.size)
            val primeiro = listagem.itens.first()
            assertEquals("1", primeiro.id)
            assertEquals("2026-10-15", primeiro.dataRegistro)
            assertEquals("23:30", primeiro.horarioDormiu)
            assertEquals("06:45", primeiro.horarioAcordou)
            assertEquals(QualidadeSono.BOA, primeiro.qualidade)
            assertEquals(DisposicaoAoAcordar.ALTA, primeiro.disposicao)
            assertEquals(false, primeiro.usouCelularAntesDeDormir)
            assertEquals(false, primeiro.tevePesadelos)
            assertEquals("Dormi muito bem.", primeiro.comentarioSonhos)
        }

    @Test
    fun testeCriarHigieneSono() =
        runTest {
            val jsonRespostaCriacao =
                """
                [
                    {
                        "id": "1",
                        "data_registro": "2026-10-15",
                        "horario_dormiu": "23:00",
                        "horario_acordou": "07:00",
                        "qualidade": "OTIMA",
                        "disposicao": "ALTA",
                        "usou_celular_antes_de_dormir": false,
                        "teve_pesadelos": false,
                        "comentario_sonhos": "Noite tranquila."
                    }
                ]
                """.trimIndent()

            val mockEngine =
                MockEngine { request ->
                    assertEquals("/rest/v1/higiene_sono", request.url.encodedPath)
                    assertEquals("POST", request.method.value)

                    respond(
                        content = jsonRespostaCriacao,
                        status = HttpStatusCode.Created,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = HigieneSonoRepositoryImpl(client = client)

            val criado =
                repository.criarHigieneSono(
                    dataRegistro = "2026-10-15",
                    horarioDormiu = "23:00",
                    horarioAcordou = "07:00",
                    qualidade = QualidadeSono.OTIMA,
                    disposicao = DisposicaoAoAcordar.ALTA,
                    usouCelularAntesDeDormir = false,
                    tevePesadelos = false,
                    comentarioSonhos = "Noite tranquila.",
                )

            assertNotNull(criado)
            assertEquals("1", criado.id)
            assertEquals("2026-10-15", criado.dataRegistro)
            assertEquals("23:00", criado.horarioDormiu)
            assertEquals("07:00", criado.horarioAcordou)
            assertEquals(QualidadeSono.OTIMA, criado.qualidade)
            assertEquals(DisposicaoAoAcordar.ALTA, criado.disposicao)
        }

    @Test
    fun testeBuscarHigieneSono404ExpectedSucess() =
        runTest {
            val mockEngine =
                MockEngine {
                    respond(
                        content = """{"code":"PGRST205","message":"Tabela não encontrada"}""",
                        status = HttpStatusCode.NotFound, // 404
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = HigieneSonoRepositoryImpl(client = client)

            assertFailsWith<Falha.RespostaInvalida> {
                repository.buscarHigieneSono()
            }
        }

    @Test
    fun testeBuscarHigieneSono500ExpectedSucess() =
        runTest {
            val mockEngine =
                MockEngine {
                    respond(
                        content = "Erro interno do servidor",
                        status = HttpStatusCode.InternalServerError, // 500
                    )
                }

            val client = createHttpClient(engine = mockEngine)
            val repository = HigieneSonoRepositoryImpl(client = client)

            assertFailsWith<Falha.RespostaInvalida> {
                repository.buscarHigieneSono()
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
            val repository = HigieneSonoRepositoryImpl(client = client)

            assertFailsWith<Falha.SemConexao> {
                repository.buscarHigieneSono()
            }
        }

    @Test
    fun testeComConexaoGuardaNoCache() =
        runTest {
            val jsonMock =
                """[{"id":"1","data_registro":"2026-10-15","horario_dormiu":"23:30","horario_acordou":"06:45","qualidade":"BOA","disposicao":"ALTA","usou_celular_antes_de_dormir":false,"teve_pesadelos":false,"comentario_sonhos":"Dormi bem"}]"""
            val mockEngine =
                MockEngine {
                    respond(
                        content = jsonMock,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }
            val cache = cacheEmMemoria()
            val repository = HigieneSonoRepositoryComCache(HigieneSonoRepositoryImpl(createHttpClient(mockEngine)), cache)

            val listagem = repository.buscarHigieneSono()

            assertEquals(false, listagem.doCache)
            assertEquals(1, listagem.itens.size)
            assertEquals(listagem.itens, cache.ler())
        }

    @Test
    fun testeSemConexaoDevolveDoCache() =
        runTest {
            val salvos =
                listOf(
                    HigieneSono(
                        id = "1",
                        dataRegistro = "2026-10-15",
                        horarioDormiu = "23:30",
                        horarioAcordou = "06:45",
                        qualidade = QualidadeSono.BOA,
                        disposicao = DisposicaoAoAcordar.ALTA,
                        usouCelularAntesDeDormir = false,
                        tevePesadelos = false,
                        comentarioSonhos = "Dormi bem",
                    ),
                )
            val mockEngine = MockEngine { throw Exception("Sem internet") }
            val cache = cacheEmMemoria(inicial = salvos)
            val repository = HigieneSonoRepositoryComCache(HigieneSonoRepositoryImpl(createHttpClient(mockEngine)), cache)

            val listagem = repository.buscarHigieneSono()

            assertEquals(true, listagem.doCache)
            assertEquals(salvos, listagem.itens)
        }

    @Test
    fun testeSemConexaoESemCacheLancaErro() =
        runTest {
            val mockEngine = MockEngine { throw Exception("Sem internet") }
            val cache = cacheEmMemoria(inicial = null)
            val repository = HigieneSonoRepositoryComCache(HigieneSonoRepositoryImpl(createHttpClient(mockEngine)), cache)

            assertFailsWith<Falha.SemConexao> {
                repository.buscarHigieneSono()
            }
        }
}
