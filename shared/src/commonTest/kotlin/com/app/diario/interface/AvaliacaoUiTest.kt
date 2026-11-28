package com.app.diario.uitest

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runComposeUiTest
import com.app.diario.domain.model.Avaliacao
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.repository.AvaliacaoRepository
import com.app.diario.ui.avaliacao.AvaliacaoLayoutCelular
import com.app.diario.ui.avaliacao.AvaliacaoSentimentoScreen
import com.app.diario.ui.avaliacao.AvaliacaoViewModel
import com.app.diario.ui.avaliacao.CriarAvaliacaoScreen
import com.app.diario.ui.components.EstadoVazio
import com.app.diario.ui.components.LayoutError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FakeAvaliacaoRepo(
    var avaliacoes: List<Avaliacao> =
        listOf(
            Avaliacao("1", "2026-11-25", 5, "Tranquilo", listOf("Sono")),
        ),
    var deveFalhar: Boolean = false,
) : AvaliacaoRepository {
    override suspend fun buscarAvaliacoes(): Listagem<Avaliacao> {
        if (deveFalhar) throw RuntimeException("Erro ao carregar")
        return Listagem(avaliacoes)
    }

    override suspend fun criarAvaliacao(
        dataRegistro: String,
        intensidade: Int,
        descricao: String,
        gatilhos: List<String>,
    ): Avaliacao = Avaliacao("1", dataRegistro, intensidade, descricao, gatilhos)
}

@OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
class AvaliacaoUiTest {
    @BeforeTest
    fun trocarMain() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun restaurarMain() = Dispatchers.resetMain()

    @Test
    fun avaliacao_exibe_tela_principal_e_cabecalho() =
        runComposeUiTest {
            val vm = AvaliacaoViewModel(FakeAvaliacaoRepo())
            setContent {
                AvaliacaoSentimentoScreen(
                    viewModel = vm,
                    onAvaliacaoIndividual = {},
                    onNovaAvaliacaoClick = {},
                    onVoltar = {},
                )
            }

            onNodeWithText("Avaliação sentimento").assertIsDisplayed()
            assertEquals(1, onAllNodes(isHeading()).fetchSemanticsNodes().size)
        }

    @Test
    fun avaliacao_formulario_invalido_mantem_botao_desabilitado() =
        runComposeUiTest {
            setContent {
                CriarAvaliacaoScreen(
                    dataRegistro = "2026-11-25",
                    onVoltar = {},
                    onSalvar = { _, _, _ -> },
                )
            }

            onNodeWithText("Salvar").assertIsNotEnabled()
        }

    @Test
    fun avaliacao_formulario_valido_habilita_botao_salvar() =
        runComposeUiTest {
            var salvou = false
            setContent {
                CriarAvaliacaoScreen(
                    dataRegistro = "2026-11-25",
                    onVoltar = {},
                    onSalvar = { _, _, _ -> salvou = true },
                )
            }

            onNodeWithText("Descrição").performTextInput("Dia calmo e produtivo")
            onNodeWithText("Trabalho").performClick()
            onNodeWithText("Salvar").assertIsEnabled()
            onNodeWithText("Salvar").performClick()
            assertTrue(salvou)
        }

    @Test
    fun avaliacao_navega_ao_clicar_no_item() =
        runComposeUiTest {
            var selecionado = false
            val avaliacao = Avaliacao("1", "2026-11-25", 5, "Tranquilo", listOf("Sono"))
            setContent {
                AvaliacaoLayoutCelular(
                    avaliacoes = listOf(avaliacao),
                    onAvaliacaoClick = { selecionado = true },
                    onVoltar = {},
                    onNovaAvaliacaoClick = {},
                )
            }

            onNodeWithText("Tranquilo").performClick()
            assertTrue(selecionado)
        }

    @Test
    fun avaliacao_exibe_estado_vazio() =
        runComposeUiTest {
            setContent {
                EstadoVazio(
                    titulo = "Nenhuma avaliação registrada",
                    inserirNovoComponente = "Adicione a primeira avaliação",
                )
            }

            onNodeWithText("Nenhuma avaliação registrada").assertIsDisplayed()
        }

    @Test
    fun avaliacao_exibe_estado_de_erro_e_recarrega() =
        runComposeUiTest {
            var recarregou = false
            setContent {
                LayoutError(
                    onClick = { recarregou = true },
                    mensagem = "Erro de conexão no servidor.",
                )
            }

            onNodeWithText("Erro de conexão no servidor.").assertIsDisplayed()
            onNodeWithText("Tentar de novo").performClick()
            assertTrue(recarregou)
        }
}
