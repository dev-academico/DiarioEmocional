package com.app.diario.viewmodel

import app.cash.turbine.test
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.Pensamento
import com.app.diario.domain.model.repository.PensamentoRepository
import com.app.diario.ui.pensamentos.EstadoTela
import com.app.diario.ui.pensamentos.PensamentoViewModel
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

// 1. Dublê específico para PensamentoRepository
class RepositorioPensamentoFalso(
    var listar: suspend () -> Listagem<Pensamento> = { Listagem(emptyList()) },
    var criar: suspend (String, String, String, String, String) -> Pensamento = { data, sit, pen, emo, comp ->
        Pensamento("1", data, sit, pen, emo, comp)
    },
) : PensamentoRepository {
    override suspend fun buscarPensamento(): Listagem<Pensamento> = listar()

    override suspend fun criarPensamento(
        dataRegistro: String,
        situacao: String,
        pensamentosImagens: String,
        emocoesSentimentos: String,
        comportamentosReacoes: String,
    ): Pensamento = criar(dataRegistro, situacao, pensamentosImagens, emocoesSentimentos, comportamentosReacoes)
}

@OptIn(ExperimentalCoroutinesApi::class)
class PensamentosViewModelTest {
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
    fun carregarPassaPorCarregandoEChegaASucesso() =
        runTest(testDispatcher) {
            val pensamentosEsperados =
                listOf(
                    Pensamento(
                        id = "1",
                        dataRegistro = "2026-11-21",
                        situacao = "Reunião de trabalho",
                        pensamentosImagens = "Vão achar ruim",
                        emocoesSentimentos = "Ansiedade",
                        comportamentosReacoes = "Fiquei em silêncio",
                    ),
                )
            val repositorio = RepositorioPensamentoFalso(listar = { Listagem(pensamentosEsperados) })

            PensamentoViewModel(repositorio).estado.test {
                // Primeiro estado emitido ao iniciar
                assertEquals(EstadoTela.Carregando, awaitItem().tela)

                // Segundo estado com a lista retornada com sucesso
                val sucesso = awaitItem().tela
                assertIs<EstadoTela.Sucesso>(sucesso)
                assertEquals(pensamentosEsperados, sucesso.pensamentos)
                assertEquals(false, sucesso.doCache)
            }
        }

    @Test
    fun carregarSemDadosPassaPorCarregandoEChegaAVazio() =
        runTest(testDispatcher) {
            val repositorio = RepositorioPensamentoFalso(listar = { Listagem(emptyList()) })

            PensamentoViewModel(repositorio).estado.test {
                assertEquals(EstadoTela.Carregando, awaitItem().tela)
                assertEquals(EstadoTela.Vazio, awaitItem().tela)
            }
        }

    @Test
    fun carregarSemConexaoChegaAErro() =
        runTest(testDispatcher) {
            val repositorio = RepositorioPensamentoFalso(listar = { throw Falha.SemConexao() })

            PensamentoViewModel(repositorio).estado.test {
                assertEquals(EstadoTela.Carregando, awaitItem().tela)
                assertIs<EstadoTela.Erro>(awaitItem().tela)
            }
        }

    @Test
    fun carregarComRespostaInvalidaChegaAErro() =
        runTest(testDispatcher) {
            val repositorio = RepositorioPensamentoFalso(listar = { throw Falha.RespostaInvalida("500") })

            PensamentoViewModel(repositorio).estado.test {
                assertEquals(EstadoTela.Carregando, awaitItem().tela)
                assertIs<EstadoTela.Erro>(awaitItem().tela)
            }
        }

    @Test
    fun carregarDoCacheMarcaSucessoComoDoCache() =
        runTest(testDispatcher) {
            val pensamentosEsperados =
                listOf(
                    Pensamento(
                        id = "1",
                        dataRegistro = "2026-11-21",
                        situacao = "Reunião de trabalho",
                        pensamentosImagens = "Vão achar ruim",
                        emocoesSentimentos = "Ansiedade",
                        comportamentosReacoes = "Fiquei em silêncio",
                    ),
                )
            val repositorio = RepositorioPensamentoFalso(listar = { Listagem(pensamentosEsperados, doCache = true) })

            PensamentoViewModel(repositorio).estado.test {
                assertEquals(EstadoTela.Carregando, awaitItem().tela)

                val sucesso = awaitItem().tela
                assertIs<EstadoTela.Sucesso>(sucesso)
                assertEquals(pensamentosEsperados, sucesso.pensamentos)
                assertEquals(true, sucesso.doCache)
            }
        }
}
