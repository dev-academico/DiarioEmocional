package com.app.diario.ui.screen

import AvaliacaoMock
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowSizeClass
import avaliacaoMock
import com.app.diario.ui.layout.AvaliacaoLayoutCelular
import com.app.diario.ui.layout.AvaliacaoLayoutLargo

// Tela principal

@Composable
fun AvaliacaoSentimentoScreen(
    avaliacoes: List<AvaliacaoMock>,
    onAvaliacaoIndividual: (AvaliacaoMock) -> Unit,
    onNovaAvaliacaoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    var avaliacaoSelecionada by remember {
        mutableStateOf<AvaliacaoMock?>(null)
    }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact =
        !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    if (isCompact) {
        AvaliacaoLayoutCelular(
            avaliacoes = avaliacoes,
            onAvaliacaoClick = onAvaliacaoIndividual,
            onNovaAvaliacaoClick = onNovaAvaliacaoClick,
            onVoltar = onVoltar,
        )
    } else {
        AvaliacaoLayoutLargo(
            avaliacoes = avaliacoes,
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
        avaliacoes = avaliacaoMock,
        onAvaliacaoIndividual = {},
        onNovaAvaliacaoClick = {},
        onVoltar = {},
    )
}

@Preview(showBackground = true)
@Composable
fun AvaliacaoSentimentoScreenEmptyPreview() {
    AvaliacaoSentimentoScreen(
        avaliacoes = emptyList(),
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
