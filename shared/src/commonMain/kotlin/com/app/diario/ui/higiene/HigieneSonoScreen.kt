package com.app.diario.ui.higiene

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import com.app.diario.ui.layout.SonoLayoutCelular
import com.app.diario.ui.layout.SonoLayoutLargo
import com.app.diario.ui.mock.RegistroSonoMock
import com.app.diario.ui.mock.registrosSonoMock

// Tela principal

@Composable
fun HigieneSonoScreen(
    viewModel: HigieneSonoViewModel = viewModel { HigieneSonoViewModel() },
    onRegistroIndividual: (RegistroSonoMock) -> Unit,
    onNovoRegistroClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var registroSelecionado by remember {
        mutableStateOf<RegistroSonoMock?>(null)
    }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact =
        !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    if (isCompact) {
        SonoLayoutCelular(
            registros = estado.registros,
            onRegistroClick = onRegistroIndividual,
            onNovoRegistroClick = onNovoRegistroClick,
            onVoltar = onVoltar,
        )
    } else {
        SonoLayoutLargo(
            registros = estado.registros,
            registroSelecionado = registroSelecionado,
            onRegistroClick = { registroSelecionado = it },
            onNovoRegistroClick = onNovoRegistroClick,
            onVoltar = onVoltar,
        )
    }
}

// Preview

@Preview(showBackground = true)
@Composable
fun RegistroSonoScreenPreview() {
    HigieneSonoScreen(
        onRegistroIndividual = {},
        onNovoRegistroClick = {},
        onVoltar = {},
    )
}

@Preview(showBackground = true)
@Composable
fun RegistroSonoScreenEmptyPreview() {
    HigieneSonoScreen(
        onRegistroIndividual = {},
        onNovoRegistroClick = {},
        onVoltar = {},
    )
}

@Preview(showBackground = true, widthDp = 900, heightDp = 600)
@Composable
fun RegistroSonoScreenLargePreview() {
    SonoLayoutLargo(
        registros = registrosSonoMock,
        registroSelecionado = registrosSonoMock.first(),
        onRegistroClick = {},
        onNovoRegistroClick = {},
        onVoltar = {},
    )
}
