package com.app.diario.`interface`

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.app.diario.ui.HomeScreen
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

@OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
class HomeUiTest {

    @BeforeTest
    fun trocarMain() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun restaurarMain() = Dispatchers.resetMain()

    @Test
    fun exibe_botoes_principais_e_cabecalho() = runComposeUiTest {
        setContent {
            HomeScreen(
                onRelatosClick = {},
                onPensamentosClick = {},
                onAvaliacaoClick = {},
                onHigieneClick = {},
            )
        }

        onNodeWithText("Diário Emocional").assertIsDisplayed()
        onNodeWithText("Relatos").assertIsDisplayed()
        onNodeWithText("Pensamentos disfuncionais").assertIsDisplayed()
        onNodeWithText("Avaliação Sentimento").assertIsDisplayed()
        onNodeWithText("Higiene do Sono").assertIsDisplayed()

        assertEquals(1, onAllNodes(isHeading()).fetchSemanticsNodes().size)
    }

    @Test
    fun navega_para_relatos_ao_clicar_no_botao() = runComposeUiTest {
        var navegou = false
        setContent {
            HomeScreen(
                onRelatosClick = { navegou = true },
                onPensamentosClick = {},
                onAvaliacaoClick = {},
                onHigieneClick = {},
            )
        }

        onNodeWithText("Relatos").performClick()
        assertTrue(navegou)
    }
}
