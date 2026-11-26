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
import com.app.diario.ui.components.EstadoVazio
import com.app.diario.ui.components.LayoutError
import com.app.diario.ui.pensamentos.CriarPensamentoDisfuncionalScreen
import com.app.diario.ui.pensamentos.PensamentoLayoutCelular
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
        Pensamento("1", "2026-11-25", "Situação de teste", "Pensamento", "Emoção", "Reação")
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
    fun pensamentos_exibe_tela_principal_e_cabecalho() = runComposeUiTest {
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
    fun pensamentos_formulario_invalido_mantem_botao_desabilitado() = runComposeUiTest {
        setContent {
            CriarPensamentoDisfuncionalScreen(
                dataRegistro = "2026-11-25",
                onVoltar = {},
                onSalvar = { _, _, _, _ -> },
            )
        }

        onNodeWithText("Salvar").assertIsNotEnabled()
    }

    @Test
    fun pensamentos_formulario_valido_habilita_botao_salvar() = runComposeUiTest {
        var salvou = false
        setContent {
            CriarPensamentoDisfuncionalScreen(
                dataRegistro = "2026-11-25",
                onVoltar = {},
                onSalvar = { _, _, _, _ -> salvou = true },
            )
        }

        onNodeWithText("Situação").performTextInput("Provas")
        onNodeWithText("Pensamentos / imagens").performTextInput("Vou ir mal")
        onNodeWithText("Emoções / sentimentos").performTextInput("Ansiedade")
        onNodeWithText("Comportamentos / reações").performTextInput("Estudar mais")
        onNodeWithText("Salvar").assertIsEnabled()
        onNodeWithText("Salvar").performClick()
        assertTrue(salvou)
    }

    @Test
    fun pensamentos_navega_ao_clicar_no_item() = runComposeUiTest {
        var selecionado = false
        val pensamento = Pensamento("1", "2026-11-25", "Situação de teste", "Pensamento", "Emoção", "Reação")
        setContent {
            PensamentoLayoutCelular(
                pensamentos = listOf(pensamento),
                onPensamentoClick = { selecionado = true },
                onVoltar = {},
                onNovoPensamentoClick = {},
            )
        }

        onNodeWithText("Situação de teste").performClick()
        assertTrue(selecionado)
    }

    @Test
    fun pensamentos_exibe_estado_vazio() = runComposeUiTest {
        setContent {
            EstadoVazio(
                titulo = "Nenhum pensamento registrado",
                inserirNovoComponente = "Adicione um novo pensamento",
            )
        }

        onNodeWithText("Nenhum pensamento registrado").assertIsDisplayed()
    }

    @Test
    fun pensamentos_exibe_estado_de_erro_e_recarrega() = runComposeUiTest {
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
