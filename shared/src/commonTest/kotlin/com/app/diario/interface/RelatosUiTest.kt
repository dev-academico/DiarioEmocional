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
import com.app.diario.domain.model.Relato
import com.app.diario.domain.model.repository.RelatoRepository
import com.app.diario.ui.components.EstadoVazio
import com.app.diario.ui.components.LayoutError
import com.app.diario.ui.relatos.CriarRelatoScreen
import com.app.diario.ui.relatos.RelatosLayoutCelular
import com.app.diario.ui.relatos.RelatosScreen
import com.app.diario.ui.relatos.RelatosViewModel
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

class FakeRelatoRepo(
    var relatos: List<Relato> = listOf(
        Relato("1", "Dia bom", "2026-11-25", "Conteúdo do dia")
    ),
    var deveFalhar: Boolean = false,
) : RelatoRepository {
    override suspend fun buscarRelatos(): Listagem<Relato> {
        if (deveFalhar) throw RuntimeException("Sem conexão com o servidor")
        return Listagem(relatos)
    }

    override suspend fun criarRelato(titulo: String, dataRegistro: String, conteudo: String): Relato {
        val novo = Relato("1", titulo, dataRegistro, conteudo)
        relatos = relatos + novo
        return novo
    }
}

@OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
class RelatosUiTest {

    @BeforeTest
    fun trocarMain() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun restaurarMain() = Dispatchers.resetMain()

    @Test
    fun relatos_exibe_tela_principal_e_cabecalho() = runComposeUiTest {
        val vm = RelatosViewModel(FakeRelatoRepo())
        setContent {
            RelatosScreen(
                viewModel = vm,
                onRelatoIndividual = {},
                onNovoRelatoClick = {},
                onVoltar = {},
            )
        }
        mainClock.advanceTimeBy(1000)
        onNodeWithText("Relatos").assertIsDisplayed()
        assertEquals(1, onAllNodes(isHeading()).fetchSemanticsNodes().size)
    }

    @Test
    fun relatos_formulario_invalido_mantem_botao_desabilitado() = runComposeUiTest {
        setContent {
            CriarRelatoScreen(
                dataRegistro = "2026-11-25",
                onVoltar = {},
                onSalvar = { _, _ -> },
            )
        }

        onNodeWithText("Salvar").assertIsNotEnabled()
    }

    @Test
    fun relatos_formulario_valido_habilita_botao_salvar() = runComposeUiTest {
        var salvou = false
        setContent {
            CriarRelatoScreen(
                dataRegistro = "2026-11-25",
                onVoltar = {},
                onSalvar = { _, _ -> salvou = true },
            )
        }

        onNodeWithText("Título").performTextInput("Escrever testes")
        onNodeWithText("Conteúdo").performTextInput("Conteúdo do relato")
        onNodeWithText("Salvar").assertIsEnabled()
        onNodeWithText("Salvar").performClick()
        assertTrue(salvou)
    }

    @Test
    fun relatos_navega_ao_clicar_no_item() = runComposeUiTest {
        var selecionado = false
        val relato = Relato("1", "Dia bom", "2026-11-25", "Conteúdo do dia")
        setContent {
            RelatosLayoutCelular(
                relatos = listOf(relato),
                onRelatoClick = { selecionado = true },
                onVoltar = {},
                onNovoRelatoClick = {},
            )
        }

        onNodeWithText("Dia bom").performClick()
        assertTrue(selecionado)
    }

    @Test
    fun relatos_exibe_estado_vazio() = runComposeUiTest {
        setContent {
            EstadoVazio(
                titulo = "Nenhum relato ainda",
                inserirNovoComponente = "Escreva o primeiro relato",
            )
        }

        onNodeWithText("Nenhum relato ainda").assertIsDisplayed()
    }

    @Test
    fun relatos_exibe_estado_de_erro_e_recarrega() = runComposeUiTest {
        var recarregou = false
        setContent {
            LayoutError(
                onClick = { recarregou = true },
                mensagem = "Sem conexão com o servidor. Confira a rede e tente de novo.",
            )
        }

        onNodeWithText("Sem conexão com o servidor. Confira a rede e tente de novo.").assertIsDisplayed()
        onNodeWithText("Tentar de novo").performClick()
        assertTrue(recarregou)
    }
}
