package com.app.diario.ui.pensamentos

import PensamentoMock
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowWidthSizeClass
import com.app.diario.ui.layout.PensamentoLayoutCelular
import com.app.diario.ui.layout.PensamentoLayoutLargo

@Composable
fun RegistroPensamentoScreen(
    viewModel: PensamentoViewModel = viewModel { PensamentoViewModel() },
    onPensamentoIndividual: (PensamentoMock) -> Unit,
    onNovoPensamentoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var pensamentoSelecionado by remember {
        mutableStateOf<PensamentoMock?>(null)
    }

    val width =
        currentWindowAdaptiveInfo()
            .windowSizeClass
            .windowWidthSizeClass

    when (width) {
        WindowWidthSizeClass.COMPACT -> {
            PensamentoLayoutCelular(
                pensamentos = estado.pensamentos,
                onPensamentoClick = onPensamentoIndividual,
                onVoltar = onVoltar,
                onNovoPensamentoClick = onNovoPensamentoClick,
            )
        }

        WindowWidthSizeClass.MEDIUM,
        WindowWidthSizeClass.EXPANDED,
        -> {
            PensamentoLayoutLargo(
                pensamentos = estado.pensamentos,
                pensamentoSelecionado = pensamentoSelecionado,
                onPensamentoClick = {
                    pensamentoSelecionado = it
                },
                onVoltar = onVoltar,
                onNovoPensamentoClick = onNovoPensamentoClick,
            )
        }
    }
}

@Preview
@Composable
fun RegistroPensamentoScreenPreview() {
    RegistroPensamentoScreen(
        onPensamentoIndividual = {},
        onNovoPensamentoClick = {},
        onVoltar = {},
    )
}

@Preview
@Composable
fun RegistroPensamentoScreenEmptyPreview() {
    RegistroPensamentoScreen(
        onPensamentoIndividual = {},
        onNovoPensamentoClick = {},
        onVoltar = {},
    )
}
