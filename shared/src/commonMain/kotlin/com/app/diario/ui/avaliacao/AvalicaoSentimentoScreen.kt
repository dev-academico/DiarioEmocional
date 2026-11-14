package com.app.diario.ui.avaliacao

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import com.app.diario.dados.repositorio.AvaliacaoRepositoryImpl
import com.app.diario.ui.mock.AvaliacaoMock
import com.app.diario.ui.mock.avaliacaoMock

@Composable
fun AvaliacaoSentimentoScreen(
    viewModel: AvaliacaoViewModel = viewModel { AvaliacaoViewModel(AvaliacaoRepositoryImpl()) },
    onAvaliacaoIndividual: (AvaliacaoMock) -> Unit,
    onNovaAvaliacaoClick: () -> Unit,
    onVoltar: () -> Unit,
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var avaliacaoSelecionada by remember { mutableStateOf<AvaliacaoMock?>(null) }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact =
        !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    when (val tela = estado.tela) {
        EstadoAvaliacaoTela.Carregando -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Carregando avaliações...")
                }
            }
        }

        EstadoAvaliacaoTela.Vazio -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhuma avaliação registrada ainda.")
            }
        }

        is EstadoAvaliacaoTela.Sucesso -> {
            val avaliacoes = tela.avaliacoes
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

        is EstadoAvaliacaoTela.Erro -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(tela.mensagem, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.carregarAvaliacoes() }) {
                    Text("Tentar de novo")
                }
            }
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
