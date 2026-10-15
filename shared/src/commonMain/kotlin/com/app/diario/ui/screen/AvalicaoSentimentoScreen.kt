package com.app.diario.ui.screen

import AvaliacaoMock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import avaliacaoMock
import com.app.diario.ui.components.AvaliacaoCard
import com.app.diario.ui.components.AvaliacaoPrevia
import com.app.diario.ui.components.Cabecalho
import com.app.diario.ui.components.NovaAvaliacaoFab
import com.app.diario.ui.components.ResumoCard

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
            onVerDetalhes = onAvaliacaoIndividual,
            onNovaAvaliacaoClick = onNovaAvaliacaoClick,
            onVoltar = onVoltar,
        )
    }
}

@Composable
fun AvaliacaoLayoutCelular(
    avaliacoes: List<AvaliacaoMock>,
    onAvaliacaoClick: (AvaliacaoMock) -> Unit,
    onNovaAvaliacaoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    Scaffold(
        topBar = { Cabecalho(title = "Avaliação sentimento", subtitle = "Como está se sentindo hoje?", onVoltar = onVoltar) },
        floatingActionButton = { NovaAvaliacaoFab(onClick = onNovaAvaliacaoClick) },
    ) { padding ->
        if (avaliacoes.isEmpty()) {
            EstadoVazio(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                contentPadding =
                    PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 96.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item { ResumoCard(avaliacoes) }
                items(avaliacoes) { avaliacao ->
                    AvaliacaoCard(
                        avaliacao = avaliacao,
                        selecionada = false,
                        onClick = { onAvaliacaoClick(avaliacao) },
                    )
                }
            }
        }
    }
}

// Layout largo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvaliacaoLayoutLargo(
    avaliacoes: List<AvaliacaoMock>,
    avaliacaoSelecionada: AvaliacaoMock?,
    onAvaliacaoClick: (AvaliacaoMock) -> Unit,
    onVerDetalhes: (AvaliacaoMock) -> Unit,
    onNovaAvaliacaoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    Scaffold(
        topBar = { Cabecalho(title = "Avaliação sentimento", subtitle = "Como está se sentindo hoje?", onVoltar = onVoltar) },
        floatingActionButton = { NovaAvaliacaoFab(onClick = onNovaAvaliacaoClick) },
    ) { padding ->
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            if (avaliacoes.isEmpty()) {
                EstadoVazio(modifier = Modifier.weight(1f))
            } else {
                LazyColumn(
                    modifier =
                        Modifier
                            .weight(0.45f)
                            .fillMaxHeight(),
                    contentPadding =
                        PaddingValues(
                            start = 16.dp,
                            end = 8.dp,
                            top = 8.dp,
                            bottom = 96.dp,
                        ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item { ResumoCard(avaliacoes) }
                    items(avaliacoes) { avaliacao ->
                        AvaliacaoCard(
                            avaliacao = avaliacao,
                            selecionada = avaliacao == avaliacaoSelecionada,
                            onClick = { onAvaliacaoClick(avaliacao) },
                        )
                    }
                }

                AvaliacaoPrevia(
                    avaliacao = avaliacaoSelecionada,
                    onVerDetalhes = onVerDetalhes,
                    modifier =
                        Modifier
                            .weight(0.55f)
                            .fillMaxHeight()
                            .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                )
            }
        }
    }
}

// Vazio

@Composable
private fun EstadoVazio(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Nenhuma avaliação ainda",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = "Toque em “Nova avaliação” para registrar como você está se sentindo.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }
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
        onVerDetalhes = {},
        onNovaAvaliacaoClick = {},
        onVoltar = {},
    )
}
