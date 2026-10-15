package com.app.diario.ui.layout

import AvaliacaoMock
import EstadoVazio
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.diario.ui.components.AvaliacaoCard
import com.app.diario.ui.components.AvaliacaoPrevia
import com.app.diario.ui.components.Cabecalho
import com.app.diario.ui.components.FloatingAdd

@Composable
fun AvaliacaoLayoutCelular(
    avaliacoes: List<AvaliacaoMock>,
    onAvaliacaoClick: (AvaliacaoMock) -> Unit,
    onNovaAvaliacaoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    Scaffold(
        topBar = { Cabecalho(title = "Avaliação sentimento", subtitle = "Como está se sentindo hoje?", onVoltar = onVoltar) },
        floatingActionButton = { FloatingAdd(onClick = onNovaAvaliacaoClick, "Nova avaliação") },
    ) { padding ->
        if (avaliacoes.isEmpty()) {
            EstadoVazio(
                modifier = Modifier.padding(padding),
                "Nenhuma avaliação ainda",
                "Toque em “Nova avaliação” para registrar como você está se sentindo.",
            )
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                contentPadding =
                    PaddingValues(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 96.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
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

@Composable
fun AvaliacaoLayoutLargo(
    avaliacoes: List<AvaliacaoMock>,
    avaliacaoSelecionada: AvaliacaoMock?,
    onAvaliacaoClick: (AvaliacaoMock) -> Unit,
    onNovaAvaliacaoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    Scaffold(
        topBar = { Cabecalho(title = "Avaliação sentimento", subtitle = "Como está se sentindo hoje?", onVoltar = onVoltar) },
        floatingActionButton = { FloatingAdd(onClick = onNovaAvaliacaoClick, "Nova avalição") },
    ) { padding ->
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            if (avaliacoes.isEmpty()) {
                EstadoVazio(
                    modifier = Modifier.weight(1f),
                    "Nenhuma avaliação ainda",
                    "Toque em “Nova avaliação” para registrar como você está se sentindo.",
                )
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
