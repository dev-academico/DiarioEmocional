package com.app.diario.ui.avaliacao

import AvaliacaoMock
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
import avaliacaoMock
import com.app.diario.ui.layout.AvaliacaoLayoutCelular
import com.app.diario.ui.layout.AvaliacaoLayoutLargo

// Tela principal

@Composable
fun AvaliacaoSentimentoScreen(
    viewModel: AvaliacaoViewModel = viewModel { AvaliacaoViewModel() },
    onAvaliacaoIndividual: (AvaliacaoMock) -> Unit,
    onNovaAvaliacaoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var avaliacaoSelecionada by remember {
        mutableStateOf<AvaliacaoMock?>(null)
    }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact =
        !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    if (isCompact) {
        AvaliacaoLayoutCelular(
            avaliacoes = estado.avaliacoes,
            onAvaliacaoClick = onAvaliacaoIndividual,
            onNovaAvaliacaoClick = onNovaAvaliacaoClick,
            onVoltar = onVoltar,
        )
    } else {
        AvaliacaoLayoutLargo(
            avaliacoes = estado.avaliacoes,
            avaliacaoSelecionada = avaliacaoSelecionada,
            onAvaliacaoClick = { avaliacaoSelecionada = it },
            onNovaAvaliacaoClick = onNovaAvaliacaoClick,
            onVoltar = onVoltar,
        )
    }
}

// Preview

@Preview(showBackground = true)
@Composable
fun AvaliacaoSentimentoScreenPreview() {
    AvaliacaoSentimentoScreen(
        onAvaliacaoIndividual = {},
        onNovaAvaliacaoClick = {},
        onVoltar = {},
    )
}

@Preview(showBackground = true)
@Composable
fun AvaliacaoSentimentoScreenEmptyPreview() {
    AvaliacaoSentimentoScreen(
        onAvaliacaoIndividual = {},
        onNovaAvaliacaoClick = {},
        onVoltar = {},
    )
}

@Preview(showBackground = true, widthDp = 900, heightDp = 600)
@Composable
fun AvaliacaoSentimentoScreenLargePreview() {
    AvaliacaoLayoutLargo(
        avaliacoes = avaliacaoMock,
        avaliacaoSelecionada = avaliacaoMock.first(),
        onAvaliacaoClick = {},
        onNovaAvaliacaoClick = {},
        onVoltar = {},
    )
}
