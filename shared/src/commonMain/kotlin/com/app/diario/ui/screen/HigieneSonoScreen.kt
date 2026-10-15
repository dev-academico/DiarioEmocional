package com.app.diario.ui.screen

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowSizeClass
import com.app.diario.ui.layout.SonoLayoutCelular
import com.app.diario.ui.layout.SonoLayoutLargo
import com.app.diario.ui.mock.RegistroSonoMock
import com.app.diario.ui.mock.registrosSonoMock

// Tela principal

@Composable
fun HigieneSonoScreen(
    registros: List<RegistroSonoMock>,
    onRegistroIndividual: (RegistroSonoMock) -> Unit,
    onNovoRegistroClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    var registroSelecionado by remember {
        mutableStateOf<RegistroSonoMock?>(null)
    }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact =
        !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    if (isCompact) {
        SonoLayoutCelular(
            registros = registros,
            onRegistroClick = onRegistroIndividual,
            onNovoRegistroClick = onNovoRegistroClick,
            onVoltar = onVoltar,
        )
    } else {
        SonoLayoutLargo(
            registros = registros,
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
        registros = registrosSonoMock,
        onRegistroIndividual = {},
        onNovoRegistroClick = {},
        onVoltar = {},
    )
}

@Preview(showBackground = true)
@Composable
fun RegistroSonoScreenEmptyPreview() {
    HigieneSonoScreen(
        registros = emptyList(),
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
