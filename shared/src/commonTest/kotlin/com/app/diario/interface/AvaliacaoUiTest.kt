package com.app.diario.`interface`

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.app.diario.domain.model.Avaliacao
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.repository.AvaliacaoRepository
import com.app.diario.ui.avaliacao.AvaliacaoSentimentoScreen
import com.app.diario.ui.avaliacao.AvaliacaoViewModel
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
    var avaliacoes: List<Avaliacao> = listOf(
        Avaliacao("1", "2026-11-25", 5, "Tranquilo", listOf("Sono"))
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
    fun exibe_lista_e_cabecalho() = runComposeUiTest {
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
    fun erro_mostra_mensagem_e_botao_de_tentar_de_novo() = runComposeUiTest {
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
