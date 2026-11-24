package com.app.diario.viewmodel

import app.cash.turbine.test
import com.app.diario.domain.model.DisposicaoAoAcordar
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.HigieneSono
import com.app.diario.domain.model.QualidadeSono
import com.app.diario.domain.model.repository.HigieneSonoRepository
import com.app.diario.ui.higiene.EstadoSonoTela
import com.app.diario.ui.higiene.HigieneSonoViewModel
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

class RepositorioHigieneSonoFalso(
    var listar: suspend () -> List<HigieneSono> = { emptyList() },
    var criar: suspend (
        String,
        String,
        String,
        QualidadeSono,
        DisposicaoAoAcordar,
        Boolean,
        Boolean,
        String,
    ) -> HigieneSono = { data, dormiu, acordou, qual, disp, cel, pes, com ->
        HigieneSono("1", data, dormiu, acordou, qual, disp, cel, pes, com)
    },
) : HigieneSonoRepository {
    override suspend fun buscarHigieneSono(): List<HigieneSono> = listar()

    override suspend fun criarHigieneSono(
        dataRegistro: String,
        horarioDormiu: String,
        horarioAcordou: String,
        qualidade: QualidadeSono,
        disposicao: DisposicaoAoAcordar,
        usouCelularAntesDeDormir: Boolean,
        tevePesadelos: Boolean,
        comentarioSonhos: String,
    ): HigieneSono =
        criar(
            dataRegistro,
            horarioDormiu,
            horarioAcordou,
            qualidade,
            disposicao,
            usouCelularAntesDeDormir,
            tevePesadelos,
            comentarioSonhos,
        )
}

@OptIn(ExperimentalCoroutinesApi::class)
class HigieneSonoViewModelTest {
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
            val registrosEsperados =
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
                        comentarioSonhos = "Dormi muito bem.",
                    ),
                )
            val repositorio = RepositorioHigieneSonoFalso(listar = { registrosEsperados })

            HigieneSonoViewModel(repositorio).estado.test {
                assertEquals(EstadoSonoTela.Carregando, awaitItem().tela)

                val sucesso = awaitItem().tela
                assertIs<EstadoSonoTela.Sucesso>(sucesso)
                assertEquals(registrosEsperados, sucesso.registros)
            }
        }

    @Test
    fun carregarSemDadosPassaPorCarregandoEChegaAVazio() =
        runTest(testDispatcher) {
            val repositorio = RepositorioHigieneSonoFalso(listar = { emptyList() })

            HigieneSonoViewModel(repositorio).estado.test {
                assertEquals(EstadoSonoTela.Carregando, awaitItem().tela)
                assertEquals(EstadoSonoTela.Vazio, awaitItem().tela)
            }
        }

    @Test
    fun carregarSemConexaoChegaAErro() =
        runTest(testDispatcher) {
            val repositorio = RepositorioHigieneSonoFalso(listar = { throw Falha.SemConexao() })

            HigieneSonoViewModel(repositorio).estado.test {
                assertEquals(EstadoSonoTela.Carregando, awaitItem().tela)
                assertIs<EstadoSonoTela.Erro>(awaitItem().tela)
            }
        }

    @Test
    fun carregarComRespostaInvalidaChegaAErro() =
        runTest(testDispatcher) {
            val repositorio = RepositorioHigieneSonoFalso(listar = { throw Falha.RespostaInvalida("500") })

            HigieneSonoViewModel(repositorio).estado.test {
                assertEquals(EstadoSonoTela.Carregando, awaitItem().tela)
                assertIs<EstadoSonoTela.Erro>(awaitItem().tela)
            }
        }
}
