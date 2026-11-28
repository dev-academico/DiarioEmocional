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
import com.app.diario.domain.model.DisposicaoAoAcordar
import com.app.diario.domain.model.HigieneSono
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.QualidadeSono
import com.app.diario.domain.model.repository.HigieneSonoRepository
import com.app.diario.ui.components.EstadoVazio
import com.app.diario.ui.components.LayoutError
import com.app.diario.ui.higiene.CriarSonoScreen
import com.app.diario.ui.higiene.HigieneSonoScreen
import com.app.diario.ui.higiene.HigieneSonoViewModel
import com.app.diario.ui.higiene.SonoLayoutCelular
import com.app.diario.ui.util.formatarData
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

class FakeHigieneSonoRepo(
    var registros: List<HigieneSono> =
        listOf(
            HigieneSono("1", "2026-11-25", "23:00", "07:00", QualidadeSono.BOA, DisposicaoAoAcordar.ALTA, false, false, "Sonhei com praia"),
        ),
    var deveFalhar: Boolean = false,
) : HigieneSonoRepository {
    override suspend fun buscarHigieneSono(): Listagem<HigieneSono> {
        if (deveFalhar) throw RuntimeException("Erro ao buscar sono")
        return Listagem(registros)
    }

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
        HigieneSono(
            "1",
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

@OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
class HigieneSonoUiTest {
    @BeforeTest
    fun trocarMain() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun restaurarMain() = Dispatchers.resetMain()

    @Test
    fun higiene_sono_exibe_tela_principal_e_cabecalho() =
        runComposeUiTest {
            val vm = HigieneSonoViewModel(FakeHigieneSonoRepo())
            setContent {
                HigieneSonoScreen(
                    viewModel = vm,
                    onRegistroIndividual = {},
                    onNovoRegistroClick = {},
                    onVoltar = {},
                )
            }
            mainClock.advanceTimeBy(1000)
            onNodeWithText("Registro de sono").assertIsDisplayed()
            assertEquals(1, onAllNodes(isHeading()).fetchSemanticsNodes().size)
        }

    @Test
    fun higiene_sono_formulario_invalido_mantem_botao_desabilitado() =
        runComposeUiTest {
            setContent {
                CriarSonoScreen(
                    dataRegistro = "2026-11-25",
                    onVoltar = {},
                    onSalvar = { _, _, _, _, _, _, _ -> },
                )
            }

            onNodeWithText("Salvar").assertIsNotEnabled()
        }

    @Test
    fun higiene_sono_formulario_valido_habilita_botao_salvar() =
        runComposeUiTest {
            var salvou = false
            setContent {
                CriarSonoScreen(
                    dataRegistro = "2026-11-25",
                    onVoltar = {},
                    onSalvar = { _, _, _, _, _, _, _ -> salvou = true },
                )
            }

            onNodeWithText("Dormiu às").performTextInput("23:00")
            onNodeWithText("Acordou às").performTextInput("07:00")
            onNodeWithText("Salvar").assertIsEnabled()
            onNodeWithText("Salvar").performClick()
            assertTrue(salvou)
        }

    @Test
    fun higiene_sono_navega_ao_clicar_no_item() =
        runComposeUiTest {
            var selecionado = false
            val sono =
                HigieneSono(
                    "1",
                    "2026-11-25",
                    "23:00",
                    "07:00",
                    QualidadeSono.BOA,
                    DisposicaoAoAcordar.ALTA,
                    false,
                    false,
                    "Sonhei com praia",
                )
            setContent {
                SonoLayoutCelular(
                    registros = listOf(sono),
                    onRegistroClick = { selecionado = true },
                    onVoltar = {},
                    onNovoRegistroClick = {},
                )
            }

            onNodeWithText(formatarData("2026-11-25")).performClick()
            assertTrue(selecionado)
        }

    @Test
    fun higiene_sono_exibe_estado_vazio() =
        runComposeUiTest {
            setContent {
                EstadoVazio(
                    titulo = "Nenhum registro de sono",
                    inserirNovoComponente = "Adicione um registro",
                )
            }

            onNodeWithText("Nenhum registro de sono").assertIsDisplayed()
        }

    @Test
    fun higiene_sono_exibe_estado_de_erro_e_recarrega() =
        runComposeUiTest {
            var recarregou = false
            setContent {
                LayoutError(
                    onClick = { recarregou = true },
                    mensagem = "Falha ao carregar registros de sono.",
                )
            }

            onNodeWithText("Falha ao carregar registros de sono.").assertIsDisplayed()
            onNodeWithText("Tentar de novo").performClick()
            assertTrue(recarregou)
        }
}
