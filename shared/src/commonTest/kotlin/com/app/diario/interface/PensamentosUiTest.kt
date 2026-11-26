package com.app.diario.`interface`

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runComposeUiTest
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.Pensamento
import com.app.diario.domain.model.repository.PensamentoRepository
import com.app.diario.ui.components.LayoutError
import com.app.diario.ui.pensamentos.CriarPensamentoDisfuncionalScreen
import com.app.diario.ui.pensamentos.PensamentoViewModel
import com.app.diario.ui.pensamentos.RegistroPensamentoScreen
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

class FakePensamentoRepo(
    var pensamentos: List<Pensamento> = listOf(
        Pensamento("1", "2026-11-25", "Situação", "Pensamento", "Emoção", "Reação")
    ),
    var deveFalhar: Boolean = false,
) : PensamentoRepository {
    override suspend fun buscarPensamento(): Listagem<Pensamento> {
        if (deveFalhar) throw RuntimeException("Sem conexão")
        return Listagem(pensamentos)
    }

    override suspend fun criarPensamento(
        dataRegistro: String,
        situacao: String,
        pensamentosImagens: String,
        emocoesSentimentos: String,
        comportamentosReacoes: String,
    ): Pensamento = Pensamento("1", dataRegistro, situacao, pensamentosImagens, emocoesSentimentos, comportamentosReacoes)
}

@OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
class PensamentosUiTest {

    @BeforeTest
    fun trocarMain() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun restaurarMain() = Dispatchers.resetMain()

    @Test
    fun adiciona_pensamento_pelo_formulario() = runComposeUiTest {
        var salvou = false
        setContent {
            CriarPensamentoDisfuncionalScreen(
                dataRegistro = "2026-11-25",
                onVoltar = {},
                onSalvar = { _, _, _, _ -> salvou = true },
            )
        }

        onNodeWithText("Salvar").assertIsNotEnabled()
        onNodeWithText("Situação").performTextInput("Provas")
        onNodeWithText("Pensamentos / imagens").performTextInput("Vou ir mal")
        onNodeWithText("Emoções / sentimentos").performTextInput("Ansiedade")
        onNodeWithText("Comportamentos / reações").performTextInput("Estudar mais")
        onNodeWithText("Salvar").assertIsEnabled()
        onNodeWithText("Salvar").performClick()
        assertTrue(salvou)
    }

    @Test
    fun exibe_lista_e_cabecalho() = runComposeUiTest {
        val vm = PensamentoViewModel(FakePensamentoRepo())
        setContent {
            RegistroPensamentoScreen(
                viewModel = vm,
                onPensamentoIndividual = {},
                onNovoPensamentoClick = {},
                onVoltar = {},
            )
        }

        onNodeWithText("Registro de pensamentos disfuncionais").assertIsDisplayed()
        assertEquals(1, onAllNodes(isHeading()).fetchSemanticsNodes().size)
    }

    @Test
    fun erro_mostra_mensagem_e_botao_de_tentar_de_novo() = runComposeUiTest {
        var recarregou = false
        setContent {
            LayoutError(
                onClick = { recarregou = true },
                mensagem = "Erro de rede ao carregar pensamentos.",
            )
        }

        onNodeWithText("Erro de rede ao carregar pensamentos.").assertIsDisplayed()
        onNodeWithText("Tentar de novo").performClick()
        assertTrue(recarregou)
    }
}
