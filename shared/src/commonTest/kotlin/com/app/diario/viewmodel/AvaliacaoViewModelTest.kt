package com.app.diario.viewmodel

import app.cash.turbine.test
import com.app.diario.domain.model.Avaliacao
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.repository.AvaliacaoRepository
import com.app.diario.ui.avaliacao.AvaliacaoViewModel
import com.app.diario.ui.avaliacao.EstadoAvaliacaoTela
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

class RepositorioAvaliacaoFalso(
    var listar: suspend () -> Listagem<Avaliacao> = { Listagem(emptyList()) },
    var criar: suspend (String, Int, String, List<String>) -> Avaliacao = { data, intensidade, descricao, gatilhos ->
        Avaliacao("1", data, intensidade, descricao, gatilhos)
    },
) : AvaliacaoRepository {
    override suspend fun buscarAvaliacoes(): Listagem<Avaliacao> = listar()

    override suspend fun criarAvaliacao(
        dataRegistro: String,
        intensidade: Int,
        descricao: String,
        gatilhos: List<String>,
    ): Avaliacao = criar(dataRegistro, intensidade, descricao, gatilhos)
}

@OptIn(ExperimentalCoroutinesApi::class)
class AvaliacaoViewModelTest {
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
            val avaliacoesEsperadas =
                listOf(
                    Avaliacao(
                        id = "1",
                        dataRegistro = "2026-11-24",
                        intensity = 7,
                        descricao = "Dia calmo",
                        gatilhos = listOf("Trabalho"),
                    ),
                )
            val repositorio = RepositorioAvaliacaoFalso(listar = { Listagem(avaliacoesEsperadas) })

            AvaliacaoViewModel(repositorio).estado.test {
                assertEquals(EstadoAvaliacaoTela.Carregando, awaitItem().tela)

                val sucesso = awaitItem().tela
                assertIs<EstadoAvaliacaoTela.Sucesso>(sucesso)
                assertEquals(avaliacoesEsperadas, sucesso.avaliacoes)
                assertEquals(false, sucesso.doCache)
            }
        }

    @Test
    fun carregarSemDadosPassaPorCarregandoEChegaAVazio() =
        runTest(testDispatcher) {
            val repositorio = RepositorioAvaliacaoFalso(listar = { Listagem(emptyList()) })

            AvaliacaoViewModel(repositorio).estado.test {
                assertEquals(EstadoAvaliacaoTela.Carregando, awaitItem().tela)
                assertEquals(EstadoAvaliacaoTela.Vazio, awaitItem().tela)
            }
        }

    @Test
    fun carregarSemConexaoChegaAErro() =
        runTest(testDispatcher) {
            val repositorio = RepositorioAvaliacaoFalso(listar = { throw Falha.SemConexao() })

            AvaliacaoViewModel(repositorio).estado.test {
                assertEquals(EstadoAvaliacaoTela.Carregando, awaitItem().tela)
                assertIs<EstadoAvaliacaoTela.Erro>(awaitItem().tela)
            }
        }

    @Test
    fun carregarComRespostaInvalidaChegaAErro() =
        runTest(testDispatcher) {
            val repositorio = RepositorioAvaliacaoFalso(listar = { throw Falha.RespostaInvalida("500") })

            AvaliacaoViewModel(repositorio).estado.test {
                assertEquals(EstadoAvaliacaoTela.Carregando, awaitItem().tela)
                assertIs<EstadoAvaliacaoTela.Erro>(awaitItem().tela)
            }
        }

    @Test
    fun carregarDoCacheMarcaSucessoComoDoCache() =
        runTest(testDispatcher) {
            val avaliacoesEsperadas =
                listOf(
                    Avaliacao(
                        id = "1",
                        dataRegistro = "2026-11-24",
                        intensity = 7,
                        descricao = "Dia calmo",
                        gatilhos = listOf("Trabalho"),
                    ),
                )
            val repositorio = RepositorioAvaliacaoFalso(listar = { Listagem(avaliacoesEsperadas, doCache = true) })

            AvaliacaoViewModel(repositorio).estado.test {
                assertEquals(EstadoAvaliacaoTela.Carregando, awaitItem().tela)

                val sucesso = awaitItem().tela
                assertIs<EstadoAvaliacaoTela.Sucesso>(sucesso)
                assertEquals(avaliacoesEsperadas, sucesso.avaliacoes)
                assertEquals(true, sucesso.doCache)
            }
        }

    @Test
    fun salvarComSucessoChamaCallbackSucesso() =
        runTest(testDispatcher) {
            var criou = false
            val repositorio =
                RepositorioAvaliacaoFalso(
                    criar = { data, intensidade, descricao, gatilhos ->
                        criou = true
                        Avaliacao("1", data, intensidade, descricao, gatilhos)
                    },
                )
            val viewModel = AvaliacaoViewModel(repositorio)

            var sucessoChamado = false
            var erroChamado: String? = null

            viewModel.salvar(
                dataRegistro = "2026-11-24",
                intensidade = 6,
                descricao = "Tudo bem",
                gatilhos = listOf("Descanso"),
                onSucesso = { sucessoChamado = true },
                onErro = { erroChamado = it },
            )
            testScheduler.advanceUntilIdle()

            assertEquals(true, criou)
            assertEquals(true, sucessoChamado)
            assertEquals(null, erroChamado)
        }

    @Test
    fun salvarComFalhaChamaCallbackErro() =
        runTest(testDispatcher) {
            val repositorio =
                RepositorioAvaliacaoFalso(
                    criar = { _, _, _, _ -> throw Falha.SemConexao() },
                )
            val viewModel = AvaliacaoViewModel(repositorio)

            var sucessoChamado = false
            var erroChamado: String? = null

            viewModel.salvar(
                dataRegistro = "2026-11-24",
                intensidade = 6,
                descricao = "Tudo bem",
                gatilhos = listOf("Descanso"),
                onSucesso = { sucessoChamado = true },
                onErro = { erroChamado = it },
            )
            testScheduler.advanceUntilIdle()

            assertEquals(false, sucessoChamado)
            assertEquals(true, erroChamado != null)
        }
}
