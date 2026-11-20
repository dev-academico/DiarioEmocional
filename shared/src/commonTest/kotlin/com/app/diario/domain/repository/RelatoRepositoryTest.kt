package com.app.diario.domain.repository

import com.app.diario.domain.data.remote.createHttpClient
import com.app.diario.domain.data.repository.RelatoRepositoryImpl
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

class RelatoRepositoryTest {

    @Test
    fun testeBuscarRelatos() = runTest {
        // campo_desconhecido para testar o ignoreUnknownKeys = true
        val jsonMock = """
            [
                {
                    "id": "1",
                    "titulo": "Dia produtivo",
                    "data_registro": "2026-11-19",
                    "conteudo": "Hoje consegui focar nos estudos da faculdade.",
                    "campo_desconhecido": "deve_ser_ignorado"
                }
            ]
        """.trimIndent()

        val mockEngine = MockEngine { request ->
            assertEquals("/rest/v1/relatos", request.url.encodedPath)

            respond(
                content = jsonMock,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = createHttpClient(engine = mockEngine)
        val repository = RelatoRepositoryImpl(client = client)

        val relatos = repository.buscarRelatos()

        assertEquals(1, relatos.size)
        val primeiro = relatos.first()
        assertEquals("1", primeiro.id)
        assertEquals("Dia produtivo", primeiro.titulo)
        assertEquals("2026-11-19", primeiro.dataRegistro)
        assertEquals("Hoje consegui focar nos estudos da faculdade.", primeiro.conteudo)
    }

    @Test
    fun testeCriarRelato() = runTest {
        val jsonRespostaCriacao = """
            [
                {
                    "id": "1",
                    "titulo": "Noite calma",
                    "data_registro": "2026-11-19",
                    "conteudo": "Dormi cedo hoje."
                }
            ]
        """.trimIndent()

        val mockEngine = MockEngine { request ->
            assertEquals("/rest/v1/relatos", request.url.encodedPath)
            assertEquals("POST", request.method.value)

            respond(
                content = jsonRespostaCriacao,
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = createHttpClient(engine = mockEngine)
        val repository = RelatoRepositoryImpl(client = client)

        val relatoCriado = repository.criarRelato(
            titulo = "Noite calma",
            dataRegistro = "2026-11-19",
            conteudo = "Dormi cedo hoje."
        )

        assertNotNull(relatoCriado)
        assertEquals("1", relatoCriado.id)
        assertEquals("Noite calma", relatoCriado.titulo)
        assertEquals("Dormi cedo hoje.", relatoCriado.conteudo)
    }

    @Test
    fun testeBuscarRelato404ExpectedSucess() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"code":"PGRST205","message":"Tabela não encontrada"}""",
                status = HttpStatusCode.NotFound // 404
            )
        }

        val client = createHttpClient(engine = mockEngine)
        val repository = RelatoRepositoryImpl(client = client)

        // testar expectSuccess = true do ktor
        val falha = assertFailsWith<Falha.RespostaInvalida> {
            repository.buscarRelatos()
        }

        assertEquals(404, falha.codigoHttp)
    }

    @Test
    fun testeBuscarRelato500ExpectedSucess() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = "Erro interno do servidor",
                status = HttpStatusCode.InternalServerError // 500
            )
        }

        val client = createHttpClient(engine = mockEngine)
        val repository = RelatoRepositoryImpl(client = client)

        // testar expectSuccess = true do ktor
        val falha = assertFailsWith<Falha.RespostaInvalida> {
            repository.buscarRelatos()
        }

        assertEquals(500, falha.codigoHttp)
    }

    @Test
    fun testeSemInternet() = runTest {
        val mockEngine = MockEngine {
            _ -> throw Exception("Sem conexão com a internet (UnknownHostException / ConnectException)")
        }

        val client = createHttpClient(engine = mockEngine)
        val repository = RelatoRepositoryImpl(client = client)

        assertFailsWith<Falha.SemConexao> {
            repository.buscarRelatos()
        }
    }
}
