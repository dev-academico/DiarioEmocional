package com.app.diario.viewmodel

import app.cash.turbine.test
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.Relato
import com.app.diario.domain.model.repository.RelatoRepository
import com.app.diario.ui.relatos.EstadoTela
import com.app.diario.ui.relatos.RelatosViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RepositorioFalso(
    var listar: suspend () -> List<Relato> = { emptyList() },
    var criar: suspend (String, String, String) -> Relato = { titulo, data, conteudo ->
        Relato("1", titulo, data, conteudo)
    }
) : RelatoRepository {
    override suspend fun buscarRelatos(): List<Relato> = listar()
    override suspend fun criarRelato(titulo: String, dataRegistro: String, conteudo: String): Relato =
        criar(titulo, dataRegistro, conteudo)
}

@OptIn(ExperimentalCoroutinesApi::class)
class RelatosViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun carregarPassaPorCarregandoEChegaASucesso() = runTest(testDispatcher) {
        val relatosEsperados = listOf(
            Relato(id = "1", titulo = "Dia produtivo", dataRegistro = "2026-11-21", conteudo = "Estudei bastante")
        )
        val repositorio = RepositorioFalso(listar = { relatosEsperados })

        RelatosViewModel(repositorio).estado.test {
            assertEquals(EstadoTela.Carregando, awaitItem().tela)

            val sucesso = awaitItem().tela
            assertIs<EstadoTela.Sucesso>(sucesso)
            assertEquals(relatosEsperados, sucesso.relatos)
        }
    }

    @Test
    fun carregarSemDadosPassaPorCarregandoEChegaAVazio() = runTest(testDispatcher) {
        val repositorio = RepositorioFalso(listar = { emptyList() })

        RelatosViewModel(repositorio).estado.test {
            assertEquals(EstadoTela.Carregando, awaitItem().tela)
            assertEquals(EstadoTela.Vazio, awaitItem().tela)
        }
    }

    @Test
    fun carregarSemConexaoChegaAErro() = runTest(testDispatcher) {
        val repositorio = RepositorioFalso(listar = { throw Falha.SemConexao() })

        RelatosViewModel(repositorio).estado.test {
            assertEquals(EstadoTela.Carregando, awaitItem().tela)
            assertIs<EstadoTela.Erro>(awaitItem().tela)
        }
    }

    @Test
    fun carregarComRespostaInvalidaChegaAErro() = runTest(testDispatcher) {
        val repositorio = RepositorioFalso(listar = { throw Falha.RespostaInvalida("500") })

        RelatosViewModel(repositorio).estado.test {
            assertEquals(EstadoTela.Carregando, awaitItem().tela)
            assertIs<EstadoTela.Erro>(awaitItem().tela)
        }
    }
}
