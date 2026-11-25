package com.app.diario.ui.avaliacao

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
import com.app.diario.domain.model.Avaliacao
import com.app.diario.ui.components.LayoutCarregando
import com.app.diario.ui.components.LayoutError
import com.app.diario.ui.components.LayoutVazio

@Composable
fun AvaliacaoSentimentoScreen(
    viewModel: AvaliacaoViewModel = viewModel { AvaliacaoViewModel() },
    onAvaliacaoIndividual: (Avaliacao) -> Unit,
    onNovaAvaliacaoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var avaliacaoSelecionada by remember { mutableStateOf<Avaliacao?>(null) }

    when (val tela = estado.tela) {
        EstadoAvaliacaoTela.Carregando -> {
            LayoutCarregando("avaliações")
        }

        EstadoAvaliacaoTela.Vazio -> {
            LayoutVazio(
                onVoltar = onVoltar,
                onNovoClick = onNovaAvaliacaoClick,
                entidade = "registro de avaliação",
                title = "Avaliação sentimento",
                subtitle = "Como está se sentindo hoje?",
            )
        }

        is EstadoAvaliacaoTela.Sucesso -> {
            val avaliacoes = tela.avaliacoes
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val isCompact =
                !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

            if (isCompact) {
                AvaliacaoLayoutCelular(
                    avaliacoes = avaliacoes,
                    doCache = tela.doCache,
                    onTentarNovamente = { viewModel.carregar() },
                    onAvaliacaoClick = onAvaliacaoIndividual,
                    onNovaAvaliacaoClick = onNovaAvaliacaoClick,
                    onVoltar = onVoltar,
                )
            } else {
                AvaliacaoLayoutLargo(
                    avaliacoes = avaliacoes,
                    doCache = tela.doCache,
                    onTentarNovamente = { viewModel.carregar() },
                    avaliacaoSelecionada = avaliacaoSelecionada,
                    onAvaliacaoClick = { avaliacaoSelecionada = it },
                    onNovaAvaliacaoClick = onNovaAvaliacaoClick,
                    onVoltar = onVoltar,
                )
            }
        }

        is EstadoAvaliacaoTela.Erro -> {
            LayoutError(onClick = { viewModel.carregar() }, mensagem = tela.mensagem)
        }
    }
}

@Preview
@Composable
fun AvaliacaoSentimentoScreenPreview() {
    AvaliacaoSentimentoScreen(
        onAvaliacaoIndividual = {},
        onNovaAvaliacaoClick = {},
        onVoltar = {},
    )
}
