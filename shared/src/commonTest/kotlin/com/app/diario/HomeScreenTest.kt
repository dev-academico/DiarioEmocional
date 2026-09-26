package com.app.diario

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import com.app.diario.ui.screens.HomeScreen
import kotlin.test.Test

class HomeScreenTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun home_exibe_botoes_principais() = runComposeUiTest {

        setContent {
            HomeScreen(
                onRelatosClick = {},
                onPensamentosClick = {},
                onPontuacaoClick = {}
            )
        }

        onNodeWithText("Relatos")
            .assertIsDisplayed()

        onNodeWithText("Pensamentos disfuncionais")
            .assertIsDisplayed()

        onNodeWithText("Pontuação diária")
            .assertIsDisplayed()
    }
}