package com.app.diario.`interface`

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.app.diario.domain.model.DisposicaoAoAcordar
import com.app.diario.domain.model.HigieneSono
import com.app.diario.domain.model.Listagem
import com.app.diario.domain.model.QualidadeSono
import com.app.diario.domain.model.repository.HigieneSonoRepository
import com.app.diario.ui.components.LayoutError
import com.app.diario.ui.higiene.HigieneSonoScreen
import com.app.diario.ui.higiene.HigieneSonoViewModel
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
    var registros: List<HigieneSono> = listOf(
        HigieneSono("1", "2026-11-25", "23:00", "07:00", QualidadeSono.BOA, DisposicaoAoAcordar.ALTA, false, false, "")
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
    ): HigieneSono = HigieneSono("1", dataRegistro, horarioDormiu, horarioAcordou, qualidade, disposicao, usouCelularAntesDeDormir, tevePesadelos, comentarioSonhos)
}

@OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
class HigieneSonoUiTest {

    @BeforeTest
    fun trocarMain() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun restaurarMain() = Dispatchers.resetMain()

    @Test
    fun exibe_registros_de_sono_e_cabecalho() = runComposeUiTest {
        val vm = HigieneSonoViewModel(FakeHigieneSonoRepo())
        setContent {
            HigieneSonoScreen(
                viewModel = vm,
                onRegistroIndividual = {},
                onNovoRegistroClick = {},
                onVoltar = {},
            )
        }

        onNodeWithText("Registro de sono").assertIsDisplayed()
        assertEquals(1, onAllNodes(isHeading()).fetchSemanticsNodes().size)
    }

    @Test
    fun erro_mostra_mensagem_e_botao_de_tentar_de_novo() = runComposeUiTest {
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
